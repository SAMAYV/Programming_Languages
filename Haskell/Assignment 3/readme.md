# Name: Samay Varshney
# Roll Number: 180101097
# CS 331 Assignment 3

# Question 1 - square root of number

- Instructions to run the code:
	1) ghci
	2) :l ques_1.hs

- Instructions to run Test Cases:
	1) squareroot <input_number> 
	
	Example: Input: squareroot 10, Output: 3.162277665175675

- Test Cases
	1) squareroot 23.56
	2) squareroot 123.57
	3) squareroot 10987.20943
	4) squareroot 1000294893.10923
	5) squareroot 0.0112343
	6) squareroot 0.000001234589
	7) squareroot 3
	8) squareroot 0
	9) squareroot 10000


# Question 2 - nth fibonacci number

- Instructions to run the code:
	1) ghci
	2) :l ques_2.hs

- Instructions to run Test Cases:
	1) fibonacci <input_number> 
	
	Example: Input: fibonacci 10, Output: 55

- Test Cases
	1) fibonacci 6
	2) fibonacci 90
	3) fibonacci 198
	4) fibonacci 1000
	5) fibonacci 100000
	6) fibonacci 1234
	7) fibonacci 0
	8) fibonacci 5
	9) fibonacci 98765


# Question 3 - quick sort

- Instructions to run the code:
	1) ghci
	2) :l ques_3.hs

- Instructions to run Test Cases:
	1) quicksort <list_of_numbers> 
	
	Example: Input: quicksort [12,2,5,4,8], Output: [2,4,5,8,12]

- Test Cases
	1) quicksort [0,1,0,1,0]
	2) quicksort [1,9,8,9,1,2,3,2,5,4,5,8,9,0,1,3,2,1]
	3) quicksort []
	4) quicksort [1]
	5) quicksort [1,2,1.1,4.9,3,9.4,5.6]
	6) quicksort [1..1000]
	7) quicksort ([1..100] ++ [1000..2000])
	8) quicksort (reverse [1..1000])
	9) quicksort (reverse ([1..10] ++ [100..120]))