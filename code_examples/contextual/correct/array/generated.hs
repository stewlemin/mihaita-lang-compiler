module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (10)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 0)
  , Load (ImmValue (20)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 1)
  , Load (ImmValue (30)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 2)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 21)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 21)
  , Jump (Abs 24)
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
  , Branch 4 (Abs 37)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 37)
  , Jump (Abs 40)
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
  , Branch 4 (Abs 57)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 57)
  , Jump (Abs 60)
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
  , Store 2 (DirAddr 3)
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , Compute Lt 2 0 4
  , Branch 4 (Abs 79)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 79)
  , Jump (Abs 82)
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
  , Branch 4 (Abs 95)
  , Load (ImmValue (3)) 5
  , Compute GtE 2 5 4
  , Branch 4 (Abs 95)
  , Jump (Abs 98)
  , Load (ImmValue (-88888888)) 2
  , WriteInstr 2 numberIO
  , EndProg
  , Load (ImmValue (0)) 3
  , Compute Add 3 2 3
  , Load (IndAddr 3) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Equal 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 4)
  , Load (DirAddr 3) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 4) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

main = run[prog0]
