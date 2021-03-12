import Data.List.Split
import Data.Char

-- function used to convert list values from string to integer
string_to_int :: [String] -> [Int]
string_to_int = map read

-- function to generate the lcm of the list of values
generate_lcm :: [Int] -> Int
generate_lcm [] = 1
generate_lcm (x:xs) = lcm x (generate_lcm xs)

-- Data structure: Binary Search Tree
data BST a = Nil | Node (BST a) a (BST a)

-- insertion in a BST
insert :: Ord a => BST a -> a -> BST a
insert Nil v = Node Nil v Nil
insert (Node tl x tr) v
   | x == v = Node tl x tr
   | v < x = Node (insert tl v) x tr
   | otherwise = Node tl x (insert tr v)

-- Creation of BST from list of values
maketree :: Ord a => [a] -> BST a
maketree = foldl insert Nil

-- Returns list of inorder traversal of BST
inorder :: BST a -> [a]
inorder Nil = []
inorder (Node tl x tr) = inorder tl ++ [x] ++ inorder tr

-- Returns list of preorder traversal of BST
preorder :: BST a -> [a]
preorder Nil = []
preorder (Node tl x tr) = [x] ++ preorder tl ++ preorder tr

-- Returns list of postorder traversal of BST
postorder :: BST a -> [a]
postorder Nil = []
postorder (Node tl x tr) = postorder tl ++ postorder tr ++ [x]

-- Main function used to take input from the user and is responsible for calling all other functions
main :: IO ()
main = do 

      -- Prompting the user to enter numbers seperated by commas
      putStrLn "Enter list of numbers separated by commas: "
      xs <- getLine
      putStrLn ""

      -- Numbers entered by the user, converted into list form are present in my_list
      let my_list = string_to_int (splitOn "," xs) 

      -- lcm_value is the lcm of all the numbers present in the list
      let lcm_value = generate_lcm my_list

      -- tree is the BST generated from the list 
      let tree = maketree my_list

      -- Printing the list
      putStrLn "List of numbers entered by the user: "
      print my_list
      putStrLn ""

      -- Printing the LCM
      putStrLn "LCM of the numbers: "
      print lcm_value
      putStrLn ""

      -- Printing Inorder tree traversal
      putStrLn "Inorder traversal of the BST: "
      print (inorder tree)
      putStrLn ""

      -- Printing Preorder tree traversal
      putStrLn "Preorder traversal of the BST: "
      print (preorder tree)
      putStrLn ""

      -- Printing Postorder tree traversal
      putStrLn "Postorder traversal of the BST: "
      print (postorder tree)
      putStrLn ""