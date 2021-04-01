% predicate to initialize the value of result

findroot(X, Accuracy):- M is X/2,
						squareroot(X, M, Accuracy).

% predicate to find result 			
					
squareroot(X, Result, Accuracy):-
	abs(Result*Result - X, Z),
	Z >= Accuracy,
	NewRes is (Result + X/Result)/2,
	squareroot(X, NewRes, Accuracy).

% predicate to output the result

squareroot(X, Result, Accuracy):-
	abs(Result*Result - X, Z),
	Z < Accuracy,
	write(Result).
