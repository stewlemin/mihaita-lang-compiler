package ut.pp.compiler.codegen;

/**
 * Factory class that builds SPRIL instruction strings.
 * Each method returns a properly formatted instruction ready to be emitted.
 */
public class Spril {
    /**
     * Register 0 — always holds the value zero.
     */
    public static final String ZERO = "0";

    /**
     * General-purpose register A.
     */
    public static final String REG_A = "2";
    /**
     * General-purpose register B.
     */
    public static final String REG_B = "3";
    /**
     * General-purpose register C.
     */
    public static final String REG_C = "4";
    /**
     * General-purpose register D.
     */
    public static final String REG_D = "5";
    /**
     * General-purpose register E.
     */
    public static final String REG_E = "6";

    /**
     * The numberIO address used by WriteInstr to print a value.
     */
    public static final String NUMBER_IO = "numberIO";

    /**
     * Builds a Compute instruction that applies an operator to two registers.
     *
     * @param operator the SPRIL operator name (e.g. "Add", "Equal")
     * @param reg1     the first source register
     * @param reg2     the second source register
     * @param resReg   the destination register
     * @return the instruction string
     */
    public static String compute(String operator, String reg1, String reg2, String resReg) {
        return "Compute " + operator + " " + reg1 + " " + reg2 + " " + resReg;
    }

    /**
     * Builds an unconditional Jump instruction.
     *
     * @param target the jump target address
     * @return the instruction string
     */
    public static String jump(String target) {
        return "Jump " + target;
    }

    /**
     * Builds a conditional Branch instruction that jumps if the register is non-zero.
     *
     * @param reg    the register to test
     * @param target the jump target address
     * @return the instruction string
     */
    public static String branch(String reg, String target) {
        return "Branch " + reg + " " + target;
    }

    /**
     * Builds a Load instruction that reads a value into a register.
     *
     * @param addrImmDI the address or immediate value to load from
     * @param reg       the destination register
     * @return the instruction string
     */
    public static String load(String addrImmDI, String reg) {
        return "Load " + addrImmDI + " " + reg;
    }

    /**
     * Builds a Store instruction that writes a register value to memory.
     *
     * @param reg       the source register
     * @param addrImmDI the destination memory address
     * @return the instruction string
     */
    public static String store(String reg, String addrImmDI) {
        return "Store " + reg + " " + addrImmDI;
    }

    /**
     * Builds a Push instruction that pushes a register onto the stack.
     *
     * @param reg the register to push
     * @return the instruction string
     */
    public static String push(String reg) {
        return "Push " + reg;
    }

    /**
     * Builds a Pop instruction that pops the top of the stack into a register.
     *
     * @param reg the register to pop into
     * @return the instruction string
     */
    public static String pop(String reg) {
        return "Pop " + reg;
    }

    /**
     * Builds a ReadInstr instruction that reads from shared memory asynchronously.
     *
     * @param addrImmDI the shared memory address to read from
     * @return the instruction string
     */
    public static String readInstr(String addrImmDI) {
        return "ReadInstr " + addrImmDI;
    }

    /**
     * Builds a Receive instruction that stores the result of a ReadInstr into a register.
     *
     * @param reg the register to receive into
     * @return the instruction string
     */
    public static String receive(String reg) {
        return "Receive " + reg;
    }

    /**
     * Builds a WriteInstr instruction that writes a register value to shared memory.
     *
     * @param reg       the source register
     * @param addrImmDI the shared memory address to write to
     * @return the instruction string
     */
    public static String writeInstr(String reg, String addrImmDI) {
        return "WriteInstr " + reg + " " + addrImmDI;
    }

    /**
     * Builds a TestAndSet instruction for atomic lock acquisition.
     *
     * @param addrImmDI the shared memory address to test-and-set
     * @return the instruction string
     */
    public static String testAndSet(String addrImmDI) {
        return "TestAndSet " + addrImmDI;
    }

    /**
     * Builds an EndProg instruction that halts this Sprockell processor.
     *
     * @return the instruction string
     */
    public static String endProg() {
        return "EndProg";
    }

    /**
     * Builds an immediate value operand, e.g. {@code (ImmValue (42))}.
     *
     * @param n the integer constant
     * @return the operand string
     */
    public static String immValue(int n) {
        return "(ImmValue " + "(" + n + ")" + ")";
    }

    /**
     * Builds a direct address operand, e.g. {@code (DirAddr 5)}.
     *
     * @param addr the memory address
     * @return the operand string
     */
    public static String dirAddr(int addr) {
        return "(DirAddr " + addr + ")";
    }

    /**
     * Builds an indirect address operand using a register, e.g. {@code (IndAddr 2)}.
     *
     * @param reg the register holding the address
     * @return the operand string
     */
    public static String indAddr(String reg) {
        return "(IndAddr " + reg + ")";
    }

    /**
     * Builds an absolute code address operand, e.g. {@code (Abs 10)}.
     *
     * @param codeAddr the instruction index
     * @return the operand string
     */
    public static String abs(int codeAddr) {
        return "(Abs " + codeAddr + ")";
    }

    /**
     * Builds a relative code address operand, e.g. {@code (Rel 3)}.
     *
     * @param offset the offset from the current instruction
     * @return the operand string
     */
    public static String rel(int offset) {
        return "(Rel " + offset + ")";
    }
}
