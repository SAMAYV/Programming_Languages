import Data.List.Split
import Data.Char

f :: [String] -> [Int]
f = map read

lcmm :: [Int] -> Int
lcmm [] = 1
lcmm (x:xs) = lcm x (lcmm xs)

data BST a = Nil | Node (BST a) a (BST a)

insert :: Ord a => BST a -> a -> BST a
insert Nil v = Node Nil v Nil
insert (Node tl x tr) v
   | x == v = Node tl x tr
   | v < x = Node (insert tl v) x tr
   | otherwise = Node tl x (insert tr v)

makeTree :: Ord a => [a] -> BST a
makeTree = foldl insert Nil

inorder :: BST a -> [a]
inorder Nil = []
inorder (Node tl x tr) = inorder tl ++ [x] ++ inorder tr

preorder :: BST a -> [a]
preorder Nil = []
preorder (Node tl x tr) = [x] ++ preorder tl ++ preorder tr

postorder :: BST a -> [a]
postorder Nil = []
postorder (Node tl x tr) = preorder tl ++ preorder tr ++ [x]

getL :: IO ()
getL = do 
         putStr "Enter String: "
         xs <- getLine
         let lst = splitOn "," xs 
         let l = f lst 
         let tree = makeTree l
         print (inorder tree)
         print (preorder tree)
         print (postorder tree)
         print l
         print (lcmm l)


