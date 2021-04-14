:-include('Mazedata.pl').

% predicate for increasing by one

incr(X, X1) :- X1 is X + 1.


% predicate for finding the shortest list 

find_min_length([],X,X).
find_min_length([[A|_]|T],M,X) :- M >= A, find_min_length(T,A,X).
find_min_length([[A|_]|T],M,X) :- M < A, find_min_length(T,M,X).
find_min_length([[A|_]|T],X) :- find_min_length(T,A,X).


% finding all the paths which are feasible from source to destination

find_all_paths([End|Rest],End,Count,X) :- X = [Count,End|Rest].
find_all_paths([Current|Rest],End,Count,X) :-
	mazelink(Current,Next),
	\+ faultynode(Next),
	\+ member(Next,Rest),
	incr(Count,Count1),
	find_all_paths([Next,Current|Rest],End,Count1,X).


% finding the solution whose length is minimum

find_solution([[L|H]|_],L,P) :- P = H.
find_solution([[_|_]|T],L,P) :- find_solution([T],L,P). 


% printing the solution 

print_solution([]).
print_solution([H]) :- write(H).
print_solution([H|T]) :- write(H), write(' -> '), print_solution(T).


% reversing the list 

reverse_list([],Z,Z).
reverse_list([H|T],Z,Acc) :- reverse_list(T,Z,[H|Acc]).


% main predicate for finding the shortest path

shortest_path(Start,End):- 
	setof(X, find_all_paths([Start],End,1,X), O), 
	find_min_length(O,L), 
	find_solution(O,L,P), 
	reverse_list(P,X,[]), 
	print_solution(X).