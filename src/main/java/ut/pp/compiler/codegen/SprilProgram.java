package ut.pp.compiler.codegen;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the list of SPRIL instructions for a single Sprockell processor.
 * Instructions are appended in order and can be patched (overwritten) by index.
 */
public class SprilProgram {
    public List<String> instructions = new ArrayList<>();

    /**
     * Creates an empty SPRIL program.
     */
    public SprilProgram() {
        this.instructions = new ArrayList<>();
    }

    /**
     * Appends an instruction to the program and returns its index.
     *
     * @param instruction the SPRIL instruction string to add
     * @return the index of the newly added instruction
     */
    public int emit(String instruction) {
        instructions.add(instruction);
        return instructions.size() - 1;
    }

    /**
     * Returns the number of instructions in this program.
     *
     * @return the instruction count
     */
    public int size() {
        return instructions.size();
    }

    /**
     * Replaces the instruction at the given index with a new one.
     * Used to back-patch jump and branch targets after the destination is known.
     *
     * @param instruction the replacement instruction string
     * @param index       the index of the instruction to replace
     */
    public void patch(String instruction, int index) {
        instructions.set(index, instruction);
    }

    /**
     * Returns the full list of instructions in this program.
     *
     * @return the instruction list
     */
    public List<String> getInstruction() {
        return instructions;
    }


}
