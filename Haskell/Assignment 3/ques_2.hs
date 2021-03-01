fibonacci :: Integer -> Integer
fibonacci 0 = 0
fibonacci 1 = 1
fibonacci n | even n         = f1 * (f1 + 2 * f2)
            | n `mod` 4 == 1 = (2 * f1 + f2) * (2 * f1 - f2) + 2
            | otherwise      = (2 * f1 + f2) * (2 * f1 - f2) - 2
         where k = n `div` 2
               f1 = fibonacci k
               f2 = fibonacci (k-1)