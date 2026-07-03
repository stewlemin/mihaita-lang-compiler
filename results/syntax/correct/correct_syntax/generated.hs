module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (5)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 0)
  , Load (ImmValue (0)) 2
  , Store 2 (DirAddr 1)
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 2)
  , Load (ImmValue (0)) 2
  , Store 2 (DirAddr 3)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 4)
  , Load (DirAddr 4) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (ImmValue (0)) 2
  , Store 2 (DirAddr 5)
  , Store 2 (DirAddr 6)
  , Store 2 (DirAddr 7)
  , Store 2 (DirAddr 8)
  , Store 2 (DirAddr 9)
  , Load (ImmValue (0)) 2
  , Store 2 (DirAddr 10)
  , Store 2 (DirAddr 11)
  , Store 2 (DirAddr 12)
  , Load (ImmValue (10)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 1)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 43)
  , Load (ImmValue (5)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 43)
  , Jump (Abs 46)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (5)) 3
  , Compute Add 3 2 3
  , Push 3
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , Pop 3
  , Store 2 (IndAddr 3)
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 63)
  , Load (ImmValue (5)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 63)
  , Jump (Abs 66)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (5)) 3
  , Compute Add 3 2 3
  , Push 3
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 78)
  , Load (ImmValue (5)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 78)
  , Jump (Abs 81)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (5)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Load (ImmValue (2)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , Pop 3
  , Store 2 (IndAddr 3)
  , Load (ImmValue (2)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 103)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 103)
  , Jump (Abs 106)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (10)) 3
  , Compute Add 3 2 3
  , Push 3
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 118)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 118)
  , Jump (Abs 121)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (10)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Or 2 3 2
  , Push 2
  , Pop 2
  , Pop 3
  , Store 2 (IndAddr 3)
  , Load (ImmValue (2)) 2
  , Push 2
  , Load (ImmValue (3)) 2
  , Push 2
  , Load (ImmValue (6)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Mul 2 3 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 13)
  , Load (ImmValue (2)) 2
  , Push 2
  , Load (ImmValue (3)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Load (ImmValue (6)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Mul 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 14)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Lt 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 15)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute LtE 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 16)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute GtE 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 17)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Gt 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 18)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Equal 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 19)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute NEq 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 20)
  , Load (DirAddr 15) 2
  , Push 2
  , Load (DirAddr 16) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute And 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 21)
  , Load (DirAddr 15) 2
  , Push 2
  , Load (DirAddr 16) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Or 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 22)
  , Load (DirAddr 15) 2
  , Push 2
  , Pop 2
  , Compute Equal 2 0 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 23)
  , Load (DirAddr 13) 2
  , Push 2
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Lt 2 3 2
  , Push 2
  , Pop 2
  , Branch 2 (Abs 268)
  , Load (DirAddr 14) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Jump (Abs 272)
  , Load (DirAddr 13) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 2) 2
  , Push 2
  , Pop 2
  , Branch 2 (Abs 277)
  , Jump (Abs 281)
  , Load (ImmValue (67)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 24)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 25)
  , Load (DirAddr 24) 2
  , Push 2
  , Load (ImmValue (5)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Lt 2 3 2
  , Push 2
  , Pop 2
  , Branch 2 (Abs 300)
  , Jump (Abs 321)
  , Load (DirAddr 25) 2
  , Push 2
  , Load (DirAddr 24) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 25)
  , Load (DirAddr 24) 2
  , Push 2
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 24)
  , Jump (Abs 289)
  , Load (DirAddr 25) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (ImmValue (42)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 26)
  , Load (DirAddr 26) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 0) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 1) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 25) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

main = run[prog0]
