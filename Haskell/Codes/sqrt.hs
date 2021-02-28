square :: Double -> Double -> Double -> Double
square x y n = if x - y < 0.000001
                   then x
                   else square l1 l2 n 
                   where 
                    l1 = (x+y) / 2.0  
                    l2 = n / l1

squareroot :: Double -> Double
squareroot n = square n 1 n 