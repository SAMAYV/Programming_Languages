% rules

sublist([],[]).
sublist([],[_]).

% predicate to match the current character in both lists

sublist([X|L],[X|T]):-sublist(L,T).	

% predicate to proceed further, head element of both doesnt matches

sublist(L,[_|T]):-sublist(L,T).