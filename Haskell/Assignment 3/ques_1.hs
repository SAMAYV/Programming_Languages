-- takes 2 input parameters and generates 1 output
square :: Double -> Double -> Double

-- base case when n is 0
square 0 0 = 0

-- calculating square root of n using recursion where x is our current square root of n
square x n = if abs(root - x) < prec
             then x
             else square root n 
               where 
                root = 0.5 * (x + n / x)  
                prec = 0.000001

-- our main function takes 1 input parameter (n) and generates 1 output value (square root of n)
squareroot :: Double -> Double

-- using square function to calculate square root of n
squareroot n = square n n 

-- Time complexity: sqrt(n)