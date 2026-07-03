module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 2)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 3)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 5)
  , ReadInstr (DirAddr 3)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 20)
  , Jump (Abs 14)
  , ReadInstr (DirAddr 5)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 26)
  , Jump (Abs 20)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

prog1:: [Instruction]
prog1=
  [ ReadInstr (DirAddr 3)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , TestAndSet (DirAddr 2)
  , Receive 2
  , Branch 2 (Abs 10)
  , Jump (Abs 6)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Load (ImmValue (50)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Push 2
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 2)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 4)
  , ReadInstr (DirAddr 4)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 42)
  , Jump (Abs 36)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 3)
  , EndProg
  ]

prog2:: [Instruction]
prog2=
  [ ReadInstr (DirAddr 4)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , TestAndSet (DirAddr 2)
  , Receive 2
  , Branch 2 (Abs 10)
  , Jump (Abs 6)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Load (ImmValue (300)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Push 2
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 2)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 4)
  , EndProg
  ]

prog3:: [Instruction]
prog3=
  [ ReadInstr (DirAddr 5)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , TestAndSet (DirAddr 2)
  , Receive 2
  , Branch 2 (Abs 10)
  , Jump (Abs 6)
  , ReadInstr (DirAddr 0)
  , Receive 2
  , Push 2
  , Load (ImmValue (100)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 0)
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Push 2
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (0)) 2
  , WriteInstr 2 (DirAddr 2)
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Push 2
  , Load (ImmValue (2)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Gt 2 3 2
  , Push 2
  , Pop 2
  , Branch 2 (Abs 46)
  , Jump (Abs 54)
  , Load (ImmValue (1)) 2
  , WriteInstr 2 (DirAddr 6)
  , ReadInstr (DirAddr 6)
  , Receive 2
  , Load (ImmValue (2)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 54)
  , Jump (Abs 48)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 5)
  , EndProg
  ]

prog4:: [Instruction]
prog4=
  [ ReadInstr (DirAddr 6)
  , Receive 2
  , Load (ImmValue (1)) 3
  , Compute Equal 2 3 3
  , Branch 3 (Abs 6)
  , Jump (Abs 0)
  , ReadInstr (DirAddr 1)
  , Receive 2
  , Push 2
  , Load (ImmValue (999)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Add 2 3 2
  , Push 2
  , Pop 2
  , WriteInstr 2 (DirAddr 1)
  , Load (ImmValue (2)) 2
  , WriteInstr 2 (DirAddr 6)
  , EndProg
  ]

main = run[prog0,prog1,prog2,prog3,prog4]