square :: Double -> Double -> Double
square x n = if abs(root - x) < prec
             then x
             else square root n 
               where 
                root = 0.5 * (x + n / x)  
                prec = 0.000001

squareroot :: Double -> Double
squareroot n = square n n 