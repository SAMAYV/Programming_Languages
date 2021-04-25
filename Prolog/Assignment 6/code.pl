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


% loop for adding faulty nodes

loop(0).
loop(N) :- N > 0, read(No), assert(faultynode(No)), P is N - 1, loop(P). 


% loop for removing faulty nodes

loop1(0).
loop1(N) :- N > 0, read(No), retract(faultynode(No)), P is N - 1, loop1(P).


% loop for performing queries of adding and deleting

queries() :- nl, writeln("Enter the number of faulty nodes you want to add: "), read(Num),
			writeln("Enter all the nodes you want to make faulty"), loop(Num), nl,
			writeln("Enter the number of faulty nodes you want to remove: "), read(Num1),
			writeln("Enter all the faulty nodes you want to remove"), loop1(Num1), nl.


% main predicate for finding the shortest path

shortest_path(Start,End) :- 
	queries(),
	writeln("Printing the shortest path in sometime:- "),
	setof(X, find_all_paths([Start],End,1,X), O), 
	find_min_length(O,L), 
	find_solution(O,L,P), 
	reverse_list(P,X,[]), 
	print_solution(X).