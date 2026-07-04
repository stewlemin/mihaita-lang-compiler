module Main where

import Sprockell

prog0:: [Instruction]
prog0=
  [ Load (ImmValue (2)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 0)
  , Load (DirAddr 0) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (ImmValue (1)) 2
  , Push 2
  , Pop 2
  , Branch 2 (Abs 13)
  , Jump (Abs 21)
  , Load (ImmValue (3)) 2
  , Push 2
  , Pop 2
  , Store 2 (DirAddr 1)
  , Load (DirAddr 1) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , Load (DirAddr 0) 2
  , Push 2
  , Pop 2
  , WriteInstr 2 numberIO
  , EndProg
  ]

main = run[prog0]