# Name: Samay Varshney
# Roll Number: 180101097
# CS331 Assignment 6

- Instructions to run each test case:
	1) g++ generateMaze.cpp -o generateMaze
	2) ./generateMaze <Height> <Width> <FaultProbability> <SrcX> <SrcY> <DstX> <DstY>
	3) swipl
	4) [code].
	5) shortest_path(S,D).

- Example:
	1) g++ generateMaze.cpp -o generateMaze
	2) ./generateMaze 10 10 0.3 1 1 9 9
	3) swipl
	4) [code].
	5) shortest_path(11,99).  

- Test Cases:

	1) 	g++ generateMaze.cpp -o generateMaze
		./generateMaze 10 10 0.3 1 1 9 9
		swipl
		[code].
		shortest_path(11,99). 

	2)	g++ generateMaze.cpp -o generateMaze
		./generateMaze 10 10 0.4 2 2 8 8
		swipl
		[code].
		shortest_path(22,88).

	3)  g++ generateMaze.cpp -o generateMaze
		./generateMaze 8 8 0.3 1 3 5 6
		swipl
		[code].
		shortest_path(11,46).

	4)  g++ generateMaze.cpp -o generateMaze
		./generateMaze 9 9 0.3 3 3 7 8
		swipl
		[code].
		shortest_path(30,71).

	5)	g++ generateMaze.cpp -o generateMaze
		./generateMaze 7 7 0.2 5 2 6 5
		swipl
		[code].
		shortest_path(37,47).