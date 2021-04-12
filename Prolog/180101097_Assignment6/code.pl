:-include('Mazedata.pl').

incr(X, X1) :- X1 is X + 1.

mini([],X,X).
mini([[A|_]|T],M,X) :- M >= A, mini(T,A,X).
mini([[A|_]|T],M,X) :- M < A, mini(T,M,X).
mini([[A|_]|T],X) :- mini(T,A,X).

path([End|Rest],End,Count,X) :- X = [Count,End|Rest].

path([Current|Rest],End,Count,X) :-
	mazelink(Current,Next),
	\+ faultynode(Next),
	\+ member(Next,Rest),
	incr(Count,Count1),
	path([Next,Current|Rest],End,Count1,X).

findsol([[L|H]|_],L,P) :- P = H.
findsol([[_|_]|T],L,P) :- findsol([T],L,P). 

printsol([]).
printsol([H]) :- write(H).
printsol([H|T]) :- write(H), write(' -> '), printsol(T).

reverse([],Z,Z).
reverse([H|T],Z,Acc) :- reverse(T,Z,[H|Acc]).

solve_maze(Start,End):- setof(X, path([Start],End,1,X), O),  
                        mini(O,L),
						findsol(O,L,P),
						reverse(P,X,[]),
						printsol(X).