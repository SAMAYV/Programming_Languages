import Data.List

fib :: Int -> Int
fib n = if n <= 1
        then n
        else fib(n-1) + fib(n-2)

fact n = case n of
           0 -> 1
           a -> a * fact (a-1)

factorial n
   | n == 0    = 1
   | otherwise = n * factorial (n - 1)

fact1 n
   | n == 0    = 1
   | otherwise = let m = n - 1 in n * fact1 m

fact2 n
   | n == 0    = 1
   | otherwise = n * fact2 m
    where m = n - 1

absolute x | x >= 0 = x
           | x < 0  = -x

avg = \x y -> (x + y) / 2

add2(x,y) = x+y
inc = \x -> add2(x,1)

negative = (< 0)

myData = [1..7]

twiceData = [2 * x | x <- myData]

twiceEvenData = [2 * x| x <- myData, x `mod` 2 == 0]

p = [1,2,3,4]
y = [x * x | x <- p]
z = [(x,y) | x <- [1,2], y <- "abc"]
a = [x * y | x <- [1,2,5], y <- [4,3]]
b = [x*x | x <- [1,2,3], even x]

c = [(x,y) | x <- [1..3], y <- [x..3]]

factors :: Int -> [Int]
factors n =
   [x | x <- [1..n], n `mod` x == 0]

prime :: Int -> Bool
prime n = factors n == [1,n]

primes :: Int -> [Int]
primes n = [x | x <- [2..n], prime x]