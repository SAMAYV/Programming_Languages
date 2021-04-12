:-include('Mazedata.pl').

incr(X, X1) :- X1 is X + 1.

mini([],X,X).
mini([[A|H]|T],M,X) :- M >= A, mini(T,A,X).
mini([[A|H]|T],M,X) :- M < A, mini(T,M,X).
mini([[A|H]|T],X) :- mini(T,A,X).

path([End|Rest],End,Count,X) :- X = [Count,End|Rest].

path([Current|Rest],End,Count,X) :-
	mazelink(Current,Next),
	\+ faultynode(Next),
	\+ member(Next,Rest),
	incr(Count,Count1),
	path([Next,Current|Rest],End,Count1,X).

findsol([[L|H]|T],L,P) :- P = H.
findsol([[M|H]|T],L,P) :- findsol([T],L,P). 

solve_maze(Start,End,Z):- setof(X, path([Start],End,1,X), O),  
                          mini(O,L),
						  findsol(O,L,P),
						  Z = P.