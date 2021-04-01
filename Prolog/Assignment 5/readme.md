# Name: Samay Varshney
# Roll Number: 180101097
# CS 331 Assignment 5

# Question 1

- Instructions to run the code:
	1) swipl
	2) [ques_1]

- Instructions to run Test Cases:
	1) squareroot(X, Accuracy). 
	
	Example: squareroot(10, 0.001).

- Test Cases
	1) squareroot(1000,0.001).
	1) squareroot(23.56,0.0000001).
	2) squareroot(123.57,0.1).
	3) squareroot(10987.20943,0.000001).
	4) squareroot(1000294893.10923,0.0001).
	5) squareroot(0.0112343,0.01).
	6) squareroot(0.000001234589,0.00001).
	7) squareroot(3,0.0001).
	8) squareroot(0,0.1).
	9) squareroot(10000,0.01).
	
# Question 2

- Instructions to run the code:
	1) swipl
	2) [ques_2]

- Instructions to run Test Cases:
	1) sublist(X, Y). 
	
	Example: sublist([a,b],[a,e,b,d,s,e]).

- Test Cases
	1) sublist([],[]).
	2) sublist([],[a,b,c]).
	3) sublist([a,b,c],[]).
	4) sublist([a,b],[a,e,e,f]).
	5) sublist([a,b],[b,a]).
	6) sublist([a,b,c,d,e],[a,e,b,c,p,a,d,c,v,e]).
	7) sublist([a,b,c,d,e,v],[a,e,b,c,p,a,d,c,v,e]).
	8) sublist([a,a],[a,b,a]).
	9) sublist([a,b,c],[c,b,a]).