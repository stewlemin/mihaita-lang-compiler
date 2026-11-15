module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (5)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 0)
  , Load (ImmValue (10)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 1)
  , Load (DirAddr 0) 2
  , Push 2
  , Load (DirAddr 1) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Lt 2 3 2
  , Push 2
  , Load (DirAddr 0) 2
  , Push 2
  , Load (DirAddr 1) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Equal 2 3 2
  , Push 2
  , Pop 2
  , Compute Equal 2 0 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute And 2 3 2
  , Push 2
  , Load (DirAddr 1) 2
  , Push 2
  , Load (ImmValue (0)) 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Gt 2 3 2
  , Push 2
  , Pop 3
  , Pop 2
  , Compute Or 2 3 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 2)
  , Load (DirAddr 2) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

main = run[prog0]