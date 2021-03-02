-- takes 3 input parameters and generates 1 output integer
fibo :: Integer -> Integer -> Integer -> Integer

-- base case when n is 0
fibo a b 0 = a

-- calculating nth fibonacci number using recursion for n-1
fibo a b n = fibo x y z
              where
                x = b
                y = a+b
                z = n-1

-- our main function takes 1 input parameter (n) and generates 1 output integer (nth fibonacci number)
fibonacci :: Integer -> Integer

-- taking input from user as n for calculating nth fibonacci number and invoking fibo function with parameters as 0th, 1st fibonacci numbers and n
fibonacci n = fibo 0 1 n

-- Time complexity: O(n)