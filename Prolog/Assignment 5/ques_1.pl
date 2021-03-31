findroot(X, _Result, Acc):- squareroot(X, div(X, 2), Acc).

squareroot(X, Result, Acc):-
	abs(Result*Result - X) >= Acc,
	squareroot(X, Res, Acc),
	Result is div((Res + div(X, Res)), 2).

squareroot(X, Result, Acc):-
	abs(Result*Result - X) < Acc,
	write(Result).