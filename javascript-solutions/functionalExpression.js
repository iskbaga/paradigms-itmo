const cnst = (value) => () => value

const variable = (name) => {
    const index = {x: 0, y: 1, z: 2}[name]
    return (...args) => args[index]
}

const operation = op => (...expressions) => (x, y, z) => op(...expressions.map(expr => expr(x, y, z)));

const madd = operation((a, b, c) => a * b + c);
const floor = operation((a) => Math.floor(a))
const ceil = operation((a) => Math.ceil(a))
const add = operation((a, b) => a + b)
const subtract = operation((a, b) => a - b)
const multiply = operation((a, b) => a * b)
const divide = operation((a, b) => a / b)
const negate = operation(a => -a)

const one = cnst(1)
const two = cnst(2)

const arity = {
    "negate": 1,
    "_": 1,
    "^": 1,
    "+": 2,
    "-": 2,
    "*": 2,
    "/": 2,
    "*+": 3
}

const vars = {
    "x": variable("x"),
    "y": variable("y"),
    "z": variable("z"),
    "one": one,
    "two": two
}

const operations = {
    "negate": negate,
    "_": floor,
    "^": ceil,
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide,
    "*+": madd
}

const parse = (expression) => {
    const stack = []
    const tokens = expression.trim().split(/\s+/)
    for (let token of tokens) {
        if (!isNaN(parseFloat(token))) {
            stack.push(cnst(parseFloat(token)))
        } else if (token in vars) {
            stack.push(vars[token]);
        } else {
            // :NOTE: стандартная функция splice
            let args = []
            for (let j = 0; j < arity[token]; j++) {
                args.push(stack.pop())
            }
            stack.push(operations[token](...args.reverse()))
        }
    }
    return stack.pop()
}

for(let i = 0; i<=10; i++) {
    console.log(parse("x x two - * x * 1 +")(i, 0, 0))
}
