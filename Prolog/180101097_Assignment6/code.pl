:-include('Mazedata.pl').

incr(X, X1) :- X1 is X + 1.

find_min_length([],X,X).
find_min_length([[A|_]|T],M,X) :- M >= A, find_min_length(T,A,X).
find_min_length([[A|_]|T],M,X) :- M < A, find_min_length(T,M,X).
find_min_length([[A|_]|T],X) :- find_min_length(T,A,X).

find_all_paths([End|Rest],End,Count,X) :- X = [Count,End|Rest].

find_all_paths([Current|Rest],End,Count,X) :-
	mazelink(Current,Next),
	\+ faultynode(Next),
	\+ member(Next,Rest),
	incr(Count,Count1),
	find_all_paths([Next,Current|Rest],End,Count1,X).

find_solution([[L|H]|_],L,P) :- P = H.
find_solution([[_|_]|T],L,P) :- find_solution([T],L,P). 

print_solution([]).
print_solution([H]) :- write(H).
print_solution([H|T]) :- write(H), write(' -> '), print_solution(T).

reverse_list([],Z,Z).
reverse_list([H|T],Z,Acc) :- reverse_list(T,Z,[H|Acc]).

shortest_path(Start,End):- 
	setof(X, find_all_paths([Start],End,1,X), O), 
	find_min_length(O,L), 
	find_solution(O,L,P), 
	reverse_list(P,X,[]), 
	print_solution(X).