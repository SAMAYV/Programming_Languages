getTwo :: IO(Char,Char)
getTwo = do 
          x <- getChar
          y <- getChar
          return (x,y)

getL :: IO String
getL = do 
         x <- getChar
         if x == '\n' then
            return []
         else
            do xs <- getLine
               return (x:xs)

putS :: String -> IO ()
putS [] = return ()
putS (x:xs) = do
                   putChar x 
                   putS xs

strlen :: IO ()
strlen = do 
          putStr "Enter a string: "
          xs <- getLine
          putStr "The string has "
          putStr (show (length xs))
          putStrLn " characters"

data Shape = Circle Float | Rect Float Float

square :: Float -> Shape
square n = Rect n n

area :: Shape -> Float
area (Circle r) = pi * r^2
area (Rect x y) = x * y

data S = C Float Float Float | R Float Float Float Float deriving (Show)

data Person = Person String String Int Float String String deriving (Show)

firstName :: Person -> String 
firstName (Person firstname _ _ _ _ _ ) = firstname

data P = P { first :: String, lastName :: String, age :: Int , height :: Float , phoneNumber :: String , flavor :: String } deriving (Show)

data Car a b c = Car { company :: a, model :: b, year :: c } deriving (Show)

tellCar :: Car String String Integer -> String
tellCar (Car {company = c, model = m, year = y}) = "This " ++ c ++ " " ++ m ++ " was made in " ++ show y

data Nat = Zero | Succ Nat

nat2int :: Nat -> Int
nat2int Zero = 0
nat2int (Succ n) = 1 + nat2int n