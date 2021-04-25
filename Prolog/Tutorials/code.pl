listlength([],0).
listlength([_|Tail],Count):-
	listlength(Tail,PartialCount),
	write(PartialCount),
	Count is PartialCount + 1.
