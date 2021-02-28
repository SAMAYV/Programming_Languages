quick :: [Int] -> [Int]
quick [] = []
quick [a] = [a]
quick q = quick q1 ++ [e] ++ quick q2 
           where 
            e = last q
            q1 = [x | x <- init q, x <= e]
            q2 = [x | x <- init q, x > e]