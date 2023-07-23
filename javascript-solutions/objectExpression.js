"use strict"

function Operation(...args) {
    this.args = args
}

Operation.prototype.toString = function () {
    return this.args.map(arg => arg.toString()).join(' ') + ' ' + this.operator
}
Operation.prototype.prefix = function () {
    return '(' + this.operator + ' ' + this.args.map(arg => arg.prefix()).join(' ') + ')';
};
Operation.prototype.postfix = function () {
    return '(' + this.args.map(arg => arg.postfix()).join(' ') + ' ' + this.operator + ')';
};
Operation.prototype.evaluate = function (x, y, z) {
    return this.calculate(...this.args.map(arg => arg.evaluate(x, y, z)))
}
Operation.prototype.diff = function (variable) {
}

function Const(value) {
    this.value = value
}

Const.prototype.toString = function () {
    return this.value.toString()
}
Const.prototype.evaluate = function (x, y, z) {
    return this.value
}
Const.prototype.diff = function (variable) {
    return new Const(0)
}
Const.prototype.prefix = Const.prototype.toString;
Const.prototype.postfix = Const.prototype.toString;

function Variable(name) {
    this.name = name
}

Variable.prototype.toString = function () {
    return this.name
}
Variable.prototype.prefix = Variable.prototype.toString;
Variable.prototype.postfix = Variable.prototype.toString;
Variable.prototype.evaluate = function (x, y, z) {
    switch (this.name) {
        case 'x':
            return x
        case 'y':
            return y
        case 'z':
            return z
    }
}
Variable.prototype.diff = function (variable) {
    return this.name === variable ? new Const(1) : new Const(0)
}

function MakeOperation(operator, calculate, diff) {
    function NewOperation(...args) {
        Operation.call(this, ...args)
    }

    NewOperation.prototype = Object.create(Operation.prototype)
    NewOperation.prototype.calculate = calculate
    NewOperation.prototype.operator = operator
    NewOperation.prototype.diff = diff
    return NewOperation
}

function MakeDistanceOperation(tag, n) {
    return MakeOperation(tag + n, function () {
        let result = 0
        for (let i = 0; i < n; i++) {
            result += arguments[i] * arguments[i]
        }
        return tag === 'sumsq' ? result : Math.sqrt(result)
    }, function (variable) {
        let sum = new Const(0);
        for (let i = 0; i < n; i++) {
            sum = new Add(sum, new Multiply(new Const(2), new Multiply(this.args[i], this.args[i].diff(variable))))
        }
        return tag === 'sumsq' ? sum : new Divide(sum, new Multiply(new Const(2), this))
    })
}

const Subtract = MakeOperation('-', (a, b) => a - b,
    function (variable) {
        return new Subtract(this.args[0].diff(variable), this.args[1].diff(variable))
    })
const Multiply = MakeOperation('*', (a, b) => a * b,
    function (variable) {
        return new Add(
            new Multiply(this.args[0].diff(variable), this.args[1]),
            new Multiply(this.args[0], this.args[1].diff(variable)))
    })
const Add = MakeOperation('+', (a, b) => a + b,
    function (variable) {
        return new Add(this.args[0].diff(variable), this.args[1].diff(variable))
    })
const Divide = MakeOperation('/', (a, b) => a / b,
    function (variable) {
        return new Divide(
            new Subtract(
                new Multiply(this.args[0].diff(variable), this.args[1]),
                new Multiply(this.args[0], this.args[1].diff(variable))),
            new Multiply(this.args[1], this.args[1]))
    })
const Negate = MakeOperation('negate', (a) => -a,
    function (variable) {
        return new Negate(this.args[0].diff(variable))
    })
const Sumexp = MakeOperation('sumexp', (...args) => args.reduce((res, arg) => res + Math.exp(arg), 0),
    function (variable) {
        return this.args.reduce((res, arg) =>
            new Add(res, new Multiply(new Sumexp(arg), arg.diff(variable))), new Const(0))
    })
const LSE = MakeOperation('lse',
    (...args) => Math.log(args.reduce((res, arg) => res + Math.exp(arg - Math.max(...args)), 0)) + Math.max(...args),
    function (variable) {
        return new Multiply(new Divide(new Const(1), new Sumexp(...this.args)), new Sumexp(...this.args).diff(variable))
    })

const Sumsq2 = MakeDistanceOperation('sumsq', 2)
const Sumsq4 = MakeDistanceOperation('sumsq', 4)
const Sumsq3 = MakeDistanceOperation('sumsq', 3)
const Sumsq5 = MakeDistanceOperation('sumsq', 5)
const Distance2 = MakeDistanceOperation('distance', 2)
const Distance3 = MakeDistanceOperation('distance', 3)
const Distance4 = MakeDistanceOperation('distance', 4)
const Distance5 = MakeDistanceOperation('distance', 5)

const arity = {
    'negate': 1, '+': 2, '-': 2, '*': 2, '/': 2,
    "sumsq2": 2, "sumsq3": 3, "sumsq4": 4, "sumsq5": 5,
    "distance2": 2, "distance3": 3, "distance4": 4, "distance5": 5,
    "sumexp": -1, "lse": -1
}
const operations = {
    'negate': Negate, '+': Add, '-': Subtract, '*': Multiply, '/': Divide,
    "sumsq2": Sumsq2, "sumsq3": Sumsq3, "sumsq4": Sumsq4, "sumsq5": Sumsq5,
    "distance2": Distance2, "distance3": Distance3, "distance4": Distance4, "distance5": Distance5,
    "sumexp": Sumexp, "lse": LSE
}
const vars = {'x': new Variable('x'), 'y': new Variable('y'), 'z': new Variable('z')}

function parse(expression) {
    const stack = []
    const tokens = expression.trim().split(/\s+/)
    for (let token of tokens) {
        if (token in vars) {
            stack.push(vars[token])
        } else if (!isNaN(token)) {
            stack.push(new Const(parseFloat(token)));
        } else if (token in operations) {
            let args = []
            if (arity[token] === -1) {
                for (let j = 0; j < stack.length; j++) {
                    args.push(stack.pop())
                }
            } else {
                for (let j = 0; j < arity[token]; j++) {
                    args.push(stack.pop())
                }
            }
            stack.push(new operations[token](...args.reverse()))
        } else {
            throw new Error(`Invalid expression: ${expression}\n`);
        }
    }
    return stack.pop()
}

const getParser = (mode) => function (input) {
    const tokenParser = function (expression) {
        let pos = 0
        let token
        let tokens = expression.match(/\(|\)|[^\s()]+/g)
        if (tokens == null) {
            throw new Error("Empty input\n")
        }
        return {
            getNext: () => {
                if (pos === tokens.length) {
                    return token = ""
                }
                return token = tokens[pos++]
            },
            getToken: () => token,
            getErrorPos: () => expression.substring(0, expression.indexOf(token)),
            toString: () => expression.toString()
        }
    }
    const parseConst = function () {
        let cur = tokenizer.getToken()
        let token
        if (!(isNaN(cur) || cur.trim() === "")) {
            token = new Const(parseFloat(cur))
        } else if (cur in vars) {
            token = vars[cur]
        } else {
            if (cur.trim() === "") {
                throw new Error("Expected close bracket \")\" but found \""
                    + tokenizer.getToken() + "\"\n")
            }
            throw new Error("Expected number or variable but found: \"" + tokenizer.getToken() + "\"\n")
        }
        tokenizer.getNext()
        return token
    }
    const parseExpression = function () {
        if (tokenizer.getToken() === '(') {
            let args = []
            let operation
            if (mode) {
                operation = tokenizer.getNext()
                tokenizer.getNext()
                while (tokenizer.getToken() !== ')') {
                    args.push(parseExpression())
                }
            } else {
                tokenizer.getNext()
                while (tokenizer.getToken() !== ')' && !(tokenizer.getToken() in operations)) {
                    args.push(parseExpression())
                }
                operation = tokenizer.getToken()
                tokenizer.getNext()
            }
            if (tokenizer.getToken() !== ')') {
                if (!(mode || operation in operations)) {
                    throw new Error("No operator found in \"" + tokenizer.toString()) + "\"\n"
                }
                throw new Error("Expected close bracket \")\" but found \""
                    + tokenizer.getErrorPos() + "\"\n")
            }
            if (!(arity[operation] === -1) &&
                arity[operation] !== args.length && (operation in operations)) {
                throw new Error("Invalid arguments count, expected " + arity[operation]
                    + " but found " + args.length + "\n")
            }
            if (!(operation in operations)) {
                throw new Error("Unexpected operator: \"" + tokenizer.getErrorPos()) + "\"\n"
            }
            tokenizer.getNext()
            return new operations[operation](...args)
        }
        return parseConst()
    }
    let tokenizer = tokenParser(input);
    tokenizer.getNext()
    let token = parseExpression(mode)
    if (tokenizer.getToken().length > 0) {
        throw new Error("Extra token found: \"" + tokenizer.getErrorPos() + '\"\n')
    }
    return token
}
const parsePrefix = getParser(true)
const parsePostfix = getParser(false)
const c = new LSE(new Variable('x')).diff('x')
console.log(new Subtract(new Const(1), new Sumexp(new Variable("x"))).evaluate(2, 2, 2))
console.log(new Sumexp(new Variable("x")).diff("x").evaluate(2, 2, 2))
console.log(c.evaluate(2, 2, 2)) //1.000000000029
console.log(parsePrefix("( - ( * 2 x ) 3 )").evaluate(8, 0, 0))
console.log((parsePostfix("(x 2 -)").evaluate(8, 0, 0)))
console.log((parsePostfix("(x 2 -)").evaluate(8, 0, 0)))