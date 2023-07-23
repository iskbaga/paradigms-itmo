init(MAX_N) :- assert_composite(3, MAX_N).

assert_composite(N, MAX_N) :-
    N * N > MAX_N.
assert_composite(N, MAX_N) :-
    N2 is N * N, Next is N + 2,
    assert_composite(N2, N, MAX_N),
    assert_composite(Next, MAX_N).
assert_composite(N, _, MAX_N) :-
    N > MAX_N, !.
assert_composite(N2, N, MAX_N) :-
    N2 =< MAX_N, Next is N2 + N,
    assert(composite(N2)),
    assert_composite(Next, N, MAX_N).

composite(N) :-
    N > 2, N mod 2 =:= 0, !.

divisible(N, D) :- N mod D =:= 0.

prime(N) :-
    integer(N),
    N > 1,
    \+ composite(N).

prime_divisors(N, D, [N]) :-
    N < D * D, !.
prime_divisors(N, D, [D | Divisors]) :-
    divisible(N, D),
    Next is N / D,
    prime_divisors(Next, D, Divisors).
prime_divisors(N, D, Divisors) :-
    \+ divisible(N, D),
    (D =:= 2 -> Next is D + 1; Next is D + 2),
    prime_divisors(N, Next, Divisors).

prime_divisors(N, Divisors) :-
    integer(N),
    N > 1,
    prime_divisors(N, 2, Divisors).
prime_divisors(R, [H | T]) :-
    prime(H),
    sort_divisors(H, T),
    prime_divisors(R1, T),
    R is H * R1.
prime_divisors(1, []) :- !.

count_divisors([], _, 0) :-!.
count_divisors([X|T], X, N) :-
    count_divisors(T, X, N1),
    N is N1 + 1.
count_divisors([H|T], X, N) :-
    count_divisors(T, X, N).

compact_prime_divisors(1, []) :- !.
compact_prime_divisors(N, CDs) :-
    prime_divisors(N, Divisors),
    remove_duplicates(Divisors, NewDivisors),
    compact_prime_divisors(NewDivisors, Divisors, CDs), !.
compact_prime_divisors(X, [(Prime, Count) | CDs]) :-
    compact_prime_divisors(X1, CDs),
    pow(Prime, Count, Power),
    X is X1 * Power, sort_cds(Prime, CDs).

compact_prime_divisors([], _, []).
compact_prime_divisors([H|T], Divisors, [(H, Count)|CDs]) :-
    count_divisors(Divisors, H, Count),
    compact_prime_divisors(T, Divisors, CDs), !.

remove_duplicates([], []).
remove_duplicates([X|T], List) :-
    member(X, T),
    remove_duplicates(T, List).
remove_duplicates([X|T], [X|List]) :-
    \+ member(X, T),
    remove_duplicates(T, List).

sort_divisors(_, []).
sort_divisors(H, [H1 | T]) :-
    H =< H1.
sort_cds(_, []) :- !.
sort_cds(Prime, [(P, C) | CDs]) :-
    Prime =< P.

pow(_, 0, 1).
pow(Base, Exp, Result) :-
    Exp > 0,
    Exp1 is Exp - 1,
    pow(Base, Exp1, Result1),
    Result is Base * Result1.
