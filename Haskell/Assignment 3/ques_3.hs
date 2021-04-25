-- takes 1 input list of any datatype and generates 1 list of input datatype
quicksort :: Ord a => [a] -> [a]

-- base case when list is empty
quicksort [] = []

-- base case when only one element is present
quicksort [a] = [a]

-- using list comprehension and recursion to calculate quicksort for current list q
-- q1 is the list having elements <= last element and q2 is the list having elements > last element
quicksort q = quicksort q1 ++ [e] ++ quicksort q2 
                where 
                 e = last q
                 q1 = [x | x <- init q, x <= e]
                 q2 = [x | x <- init q, x > e]

-- Time complexity: Worst Case O(n*n), Average and Best Case O(nlogn)