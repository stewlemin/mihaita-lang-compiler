module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 1)
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 12)
  , Jump (Abs 6)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

prog1:: [Instruction]
prog1=
  [ ReadInstr (DirAddr 1)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 2)
  , ReadInstr (DirAddr 2)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 14)
  , Jump (Abs 8)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 1)
  , EndProg
  ]

prog2:: [Instruction]
prog2=
  [ ReadInstr (DirAddr 2)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , Load (ImmValue (21)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 2)
  , EndProg
  ]

main = run[prog0,prog1,prog2]
