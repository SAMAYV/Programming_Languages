quicksort :: [Integer] -> [Integer]
quicksort [] = []
quicksort [a] = [a]
quicksort q = quicksort q1 ++ [e] ++ quicksort q2 
                where 
                 e = last q
                 q1 = [x | x <- init q, x <= e]
                 q2 = [x | x <- init q, x > e]