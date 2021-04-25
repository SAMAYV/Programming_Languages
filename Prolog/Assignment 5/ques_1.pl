% predicate to initialize the value of result

squareroot(X, Accuracy):- M is X/2,
						findroot(X, M, Accuracy).

% predicate to find result 			
					
findroot(X, Result, Accuracy):-
	abs(Result*Result - X, Z),
	Z >= Accuracy,
	NewRes is (Result + X/Result)/2,
	findroot(X, NewRes, Accuracy).

% predicate to output the result

findroot(X, Result, Accuracy):-
	abs(Result*Result - X, Z),
	Z < Accuracy,
	write(Result).
