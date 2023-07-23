:- load_library('alice.tuprolog.lib.DCGLibrary').

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

spaces_p --> [].
spaces_p --> [' '], spaces_p.

name_p([]) --> [].
name_p([X | T]) --> [X], { member(X, ['x', 'y', 'z', 'X', 'Y', 'Z']) }, name_p(T).

variable(Name, variable(Name)).
expr_p(variable(Name)) -->
  { nonvar(Name, atom_chars(Name, Chars)) },
  spaces_p, name_p(Chars), spaces_p,
  { Chars = [_ | _], atom_chars(Name, Chars) }.

const(Value, const(Value)).
expr_p(const(Value)) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  spaces_p, digits_p(Chars), spaces_p,
  { length(Chars, Len), Len >= 2, number_chars(Value, Chars) }.

op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).
op_negate(A, operation(op_negate, A)).
op_not(A, operation(op_not, A)).
op_and(A, B, operation(op_and, A, B)).
op_or(A, B, operation(op_or, A, B)).
op_xor(A, B, operation(op_xor, A, B)).

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].
op_p(op_not) --> ['!'].
op_p(op_and) --> ['&', '&'].
op_p(op_or) --> ['|', '|'].
op_p(op_xor) --> ['^', '^'].

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is float(A) / B.
operation(op_negate, A, R) :- R is -A.
operation(op_not, A, R) :- (A > 0 -> R is 0; R is 1).
operation(op_and, A, B, R) :- (A > 0, B > 0 -> R is 1; R is 0).
operation(op_or, A, B, R) :- (A > 0 -> R is 1; (B > 0 -> R is 1; R is 0)).
operation(op_xor, A, B, R) :- (A > 0 -> (B > 0 -> R is 0; R is 1); (B > 0 -> R is 1; R is 0)).


evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [Var | _]), lookup(Var, Vars, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'])},
  [H],
  digits_p(T).

expr_p(operation(Op, A, B)) --> spaces_p, ['('], spaces_p, expr_p(A), spaces_p, [' '], op_p(Op), [' '], spaces_p, expr_p(B), spaces_p, [')'], spaces_p.
expr_p(operation(Op, A)) --> spaces_p, op_p(Op), [' '], spaces_p, expr_p(A), spaces_p.

infix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C), !.
infix_str(E, A) :- atom(A), atom_chars(A, C), phrase(expr_p(E), C), !.