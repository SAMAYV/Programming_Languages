import Data.List.Split
import Data.Char

f :: [String] -> [Int]
f = map read

lcmm :: [Int] -> Int
lcmm [] = 1
lcmm (x:xs) = lcm x (lcmm xs)

getL :: IO ()
getL = do 
         putStr "Enter String: "
         xs <- getLine
         let lst = splitOn "," xs 
         let l = f lst 
         print l
         print (lcmm l)