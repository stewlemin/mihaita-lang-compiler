# MihaitaLang Compiler 

> Instead of using the run button for the bash commands please copy and paste them to terminal.

## Prerequisites

The following software is required:

- Java Development Kit 17 or later
- Maven 3.8 or later
- Haskell Stack

Check the installations with:

```bash
javac --version
```

```bash
mvn --version
```

```bash
stack --version
```
## First-time setup
After unzipping the project, run these once to build both parts of the compiler.
From the SprockellCompiler directory, build the Java Compiler:
```bash
mvn compile
```

Then build the Sprockell simulator so the generated code can run on it:
```bash
cd sprockell
stack build
cd ..
```

## Running the compiler

To run the compiler on a MihaitaLang ('.msm') file, 'cd' to the SprockellCompiler directory and use the following command in the terminal:


```bash
mvn exec:java -Dexec.mainClass="ut.pp.Main" -Dexec.args="path/to/source/file"
```
For example to compile the banking test program:
```bash
mvn exec:java -Dexec.mainClass="ut.pp.Main" -Dexec.args="results/semantic/correct/banking_system/source.msm"
```
If your '.msm' file is inside the SprockellCompiler directory, use a relative path. If it's elsewhere on your system, give its absolute path.

This writes the generated SPRIL code to 'SprockellCompiler/output/Generated.hs'.

Then change directory to sprockell:
```bash
cd sprockell
```
And run the following command to run the source code on the processor:
```bash
stack runghc ../output/Generated.hs
```
To compile another program don't forget to go back to SprockellCompiler directory.
```bash
cd ..
```
## Testing
To run JUnit Tests run:
```bash
mvn test
```
The `code_examples` dir has files that have both correct and incorrect programs for each of the testing categories:
- syntax
- contextual
- semantic

These files are all ran by the Junit tests, but the automated tests don't show error messages so the results directory holds the results for each test.
 
## Project Structure

- `src/` — Java source (lexer, parser, checker, code generation,JUnit Tests)
- `results/` — test programs (.msm) and their expected outputs
- `output/` — generated SPRIL code (Generated.hs) is written here
- `sprockell/` — the Sprockell simulator (Haskell Stack project)











