# Name: Samay Varshney
# Roll Number: 180101097
# CS331 Assignment 6

- Instructions to run each test case:
	1) g++ generateMaze.cpp -o generateMaze
	2) ./generateMaze <Height> <Width> <FaultProbability> <SrcX> <SrcY> <DstX> <DstY>
	3) swipl
	4) [code].
	5) shortest_path(S,D).

	- Now it will take input from the user for considering adding and removing queries:

	6) Enter the number of faulty nodes you want to add (ending with a full stop)
	7) Enter all the nodes you want to make faulty (enter all those line by line each ending with a full stop)
	8) Enter the number of faulty nodes you want to remove (ending with a full stop)
	9) Enter all the faulty nodes you want to remove (enter all those line by line each ending with a full stop)

- Example:
	-	g++ generateMaze.cpp -o generateMaze
	-	./generateMaze 7 7 0.2 5 2 6 5
	-	swipl
	-	[code].
	-	shortest_path(37,47).
	-	Enter the number of faulty nodes you want to add: 
	-	2.
	-	Enter all the nodes you want to make faulty
	- 	45.
	-	39.
	-	Enter the number of faulty nodes you want to remove: 
	-	2.
	-	Enter all the faulty nodes you want to remove
	-	38.
	-	31.

- You can also refer to the image attached which shows how to run sample example shown above.

- Test Cases:

	1) 	-	g++ generateMaze.cpp -o generateMaze
		-	./generateMaze 10 10 0.3 1 1 9 9
		-	swipl
		-	[code].
		-	shortest_path(11,99). 
		-	Enter the number of faulty nodes you want to add: 
		-	3.
		-	Enter all the nodes you want to make faulty
		-	4.
		-	45.
		-	88.
		-	Enter the number of faulty nodes you want to remove: 
		-	1.
		-	Enter all the faulty nodes you want to remove
		-	74.

	2)	-	g++ generateMaze.cpp -o generateMaze
		-	./generateMaze 10 10 0.4 2 2 8 8
		-	swipl
		-	[code].
		-	shortest_path(22,88).
		-	Enter the number of faulty nodes you want to add: 
		-	1.
		-	Enter all the nodes you want to make faulty
		-	78.
		-	Enter the number of faulty nodes you want to remove: 
		-	2.
		-	Enter all the faulty nodes you want to remove
		-	68.
		-	67.

	3)  -	g++ generateMaze.cpp -o generateMaze
		-	./generateMaze 8 8 0.3 1 3 5 6
		-	swipl
		-	[code].
		-	shortest_path(11,46).
		-	Enter the number of faulty nodes you want to add: 
		-	0.
		-	Enter all the nodes you want to make faulty
		-	Enter the number of faulty nodes you want to remove: 
		-	0.
		-	Enter all the faulty nodes you want to remove

	4)  -	g++ generateMaze.cpp -o generateMaze
		-	./generateMaze 9 9 0.3 3 3 7 8
		-	swipl
		-	[code].
		-	shortest_path(30,71).
		-	Enter the number of faulty nodes you want to add: 
		-	1.
		-	Enter all the nodes you want to make faulty
		-	39.
		-	Enter the number of faulty nodes you want to remove: 
		-	2.
		-	Enter all the faulty nodes you want to remove
		-	31.
		-	32.

	5)	-	g++ generateMaze.cpp -o generateMaze
		-	./generateMaze 7 7 0.2 5 2 6 5
		-	swipl
		-	[code].
		-	shortest_path(37,47).
		-	Enter the number of faulty nodes you want to add: 
		-	2.
		-	Enter all the nodes you want to make faulty
		- 	45.
		-	39.
		-	Enter the number of faulty nodes you want to remove: 
		-	0.
		-	Enter all the faulty nodes you want to remove
