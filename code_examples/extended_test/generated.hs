module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (50)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 0)
  , Load (ImmValue (75)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 1)
  , Load (ImmValue (90)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 2)
  , Load (ImmValue (99)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 3)
  , Load (DirAddr 0) 2
  , Store 2 (DirAddr 4)
  , Load (DirAddr 1) 2
  , Store 2 (DirAddr 5)
  , Load (DirAddr 2) 2
  , Store 2 (DirAddr 6)
  , Load (DirAddr 3) 2
  , Store 2 (DirAddr 7)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 33)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 33)
  , Jump (Abs 36)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Push 3
  , Load (ImmValue (60)) 2
  , Push 2
  , Pop 2
  , Pop 3
  , Store 2 (IndAddr 3)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 53)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 53)
  , Jump (Abs 56)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 69)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 69)
  , Jump (Abs 72)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Load (ImmValue (2)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 89)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 89)
  , Jump (Abs 92)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Load (ImmValue (3)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 109)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 109)
  , Jump (Abs 112)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 8)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 131)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 131)
  , Jump (Abs 134)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 147)
  , Load (ImmValue (4)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 147)
  , Jump (Abs 150)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (4)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Gt 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 9)
  , Load (DirAddr 8) 2
  , Push 2
  , Load (ImmValue (200)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Gt 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 10)
  , Load (DirAddr 10) 2
  , Push 2
  , Pop 2
  , Branch 2 (Abs 179)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Jump (Abs 191)
  , Load (ImmValue (99)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 11)
  , Load (DirAddr 11) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 8) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 9) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 2)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 3)
  , ReadInstr (DirAddr 2)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 215)
  , Jump (Abs 209)
  , ReadInstr (DirAddr 3)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 221)
  , Jump (Abs 215)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

prog1:: [Instruction]
prog1=
  [ ReadInstr (DirAddr 2)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , TestAndSet (DirAddr 1)
  , Receive 2
  , Branch 2 (Abs 10)
  , Jump (Abs 6)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Load (ImmValue (10)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 2)
  , EndProg
  ]

prog2:: [Instruction]
prog2=
  [ ReadInstr (DirAddr 3)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , TestAndSet (DirAddr 1)
  , Receive 2
  , Branch 2 (Abs 10)
  , Jump (Abs 6)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Load (ImmValue (20)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 3)
  , EndProg
  ]

main = run[prog0,prog1,prog2]