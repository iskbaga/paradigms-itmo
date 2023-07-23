map_build([],[]) :- !.
map_build([(Key, Value) | T], Result) :-
    map_build(T, TreeMap),
    map_put(TreeMap, Key, Value, Result).

map_put(TreeMap, Key, Value, Result):-
    rand_int(10000, P),
    Mode = true,
    mode_put(Mode, TreeMap, Key, Value, P, Result).

map_putIfAbsent(TreeMap, Key, Value, Result) :-
    rand_int(10000, P),
    Mode = false,
    mode_put(Mode, TreeMap, Key, Value, P, Result).

mode_put(_, [], Key, Value, P, node(Key, Value, P, [], [])) :- !.
mode_put(Overwrite, node(Key1, Value1, P1, Left1, Right1), Key2, Value2, P2, Result) :-
    Key2 = Key1, Overwrite,
    Result = node(Key1, Value2, P1, Left1, Right1), !.
mode_put(Overwrite, node(Key1, Value1, P1, Left1, Right1), Key2, Value2, P2, Result) :-
    Key2 = Key1, \+ Overwrite,
    Result = node(Key1, Value1, P1, Left1, Right1), !.
mode_put(Overwrite, node(Key1, Value1, P1, Left1, Right1), Key2, Value2, P2, Result) :-
    P2 > P1, Overwrite,
    split(node(Key1, Value1, P1, Left1, Right1), Key2, L, R),
    Result = node(Key2, Value2, P2, L, R), !.
mode_put(Overwrite, node(Key1, Value1, P1, Left1, Right1), Key2, Value2, P2, Result) :-
    Key2 < Key1,
    mode_put(Overwrite, Left1, Key2, Value2, P2, L),
    Result = node(Key1, Value1, P1, L, Right1), !.
mode_put(Overwrite, node(Key1, Value1, P1, Left1, Right1), Key2, Value2, P2, Result) :-
    mode_put(Overwrite, Right1, Key2, Value2, P2, R),
    Result = node(Key1, Value1, P1, Left1, R), !.

map_remove([], _, []) :-!.
map_remove(node(Key, _, _, Left, Right), Key, Result) :-
    merge(Left, Right, Result).
map_remove(node(Key1, Value1, P1, Left1, Right1), Key2, Result) :-
    Key2 < Key1,
    map_remove(Left1, Key2, LeftResult),
    Result = node(Key1, Value1, P1, LeftResult, Right1).
map_remove(node(Key1, Value1, P1, Left1, Right1), Key2, Result) :-
    Key2 > Key1,
    map_remove(Right1, Key2, RightResult),
    Result = node(Key1, Value1, P1, Left1, RightResult).

map_get(node(Key, Value, _, _, _), Key, Value) :- !.
map_get(node(Key, Value, _, Left, Right), K, R) :-
    K < Key, map_get(Left, K, R).
map_get(node(Key, Value, _, Left, Right), K, R) :-
    K > Key, map_get(Right, K, R).

split([], _, [], []) :- !.
split(node(Key, Value, P, Left, Right), Key, Left, Right) :-!.
split(node(Key, Value, P, Left, Right), SplitKey, LeftResult, RightResult) :-
    Key < SplitKey,
    split(Right, SplitKey, T1, RightResult),
    LeftResult = node(Key, Value, P, Left, T1).
split(node(Key, Value, P, Left, Right), SplitKey, LeftResult, RightResult) :-
    Key > SplitKey,
    split(Left, SplitKey, LeftResult, T2),
    RightResult = node(Key, Value, P, T2, Right).

merge([], T, T) :- !.
merge(T, [], T) :- !.
merge(node(Key1, Value1, P1, Left1, Right1), node(Key2, Value2, P2, Left2, Right2), Result) :-
    P1 > P2,
    merge(Right1, node(Key2, Value2, P2, Left2, Right2), T1),
    Result = node(Key1, Value1, P1, Left1, T1).
merge(node(Key1, Value1, P1, Left1, Right1), node(Key2, Value2, P2, Left2, Right2), Result) :-
    P1 =< P2,
    merge(node(Key1, Value1, P1, Left1, Right1), Left2, T2),
    Result = node(Key2, Value2, P2, T2, Right2).

