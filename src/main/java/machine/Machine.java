/**
 * Machine.java
 * 
 * Created on 29.01.2013
 */

package machine;


import static machine.Opcodes.*;
import machine.Stack.StackFrame;


/**
 * <p>
 * The class Machine.
 * </p>
 * 
 * @version V0.0 29.01.2013
 * @author SillyFreak
 */
public class Machine {
    private final Memory memory;
    private final Stack  stack;
    private int          pc;
    
    public Machine() {
        memory = new Memory(0x10000);
        stack = new Stack(memory, 0x1000);
    }
    
    public Memory getMemory() {
        return memory;
    }
    
    public Stack getStack() {
        return stack;
    }
    
    public void setPC(int pc) {
        this.pc = pc;
    }
    
    public int getPC() {
        return pc;
    }
    
    public void increasePC(int offset) {
        pc += offset;
    }
    
    
    /**
     * reads the instruction at the current PC, and any optional data, and executes the instruction. The PC is
     * increased by this method accordingly.
     */
    public StackFrame executeInstruction(StackFrame f) {
        int opcode = memory.getUByte(getPC());
        increasePC(1);
        
        switch(opcode) {
            case NOP:
            break;
            case ACONST_NULL:
                throw new UnsupportedOperationException("ACONST_NULL");
            case ICONST_M1:
            case ICONST_0:
            case ICONST_1:
            case ICONST_2:
            case ICONST_3:
            case ICONST_4:
            case ICONST_5: {
                stack.setInt(0, opcode - ICONST_0);
                stack.increaseSP(4);
                break;
            }
            case LCONST_0:
            case LCONST_1: {
                stack.setLong(0, opcode - LCONST_0);
                stack.increaseSP(8);
                break;
            }
            case FCONST_0:
            case FCONST_1:
            case FCONST_2: {
                stack.setFloat(0, opcode - FCONST_0);
                stack.increaseSP(4);
                break;
            }
            case DCONST_0:
            case DCONST_1: {
                stack.setDouble(0, opcode - LCONST_0);
                stack.increaseSP(8);
                break;
            }
            case IPUSH1: {
                stack.setInt(0, memory.getUByte(getPC()));
                increasePC(1);
                stack.increaseSP(4);
                break;
            }
            case IPUSH2: {
                stack.setInt(0, memory.getUShort(getPC()));
                increasePC(2);
                stack.increaseSP(4);
                break;
            }
            case LDC:
                throw new UnsupportedOperationException("LDC");
            case LDC_W:
                throw new UnsupportedOperationException("LDC_W");
            case LDC2_W:
                throw new UnsupportedOperationException("LDC2_W");
            case PTR: {
                int index = memory.getUByte(getPC());
                increasePC(1);
                stack.setInt(0, f.getAddress() + index * 4);
                stack.increaseSP(4);
                break;
            }
            case PTR_0:
            case PTR_1:
            case PTR_2:
            case PTR_3: {
                int index = opcode - PTR_0;
                stack.setInt(0, f.getAddress() + index * 4);
                stack.increaseSP(4);
                break;
            }
            case LOAD4: {
                int index = memory.getUByte(getPC());
                increasePC(1);
                stack.setInt(0, f.getInt(index * 4));
                stack.increaseSP(4);
                break;
            }
            case LOAD8: {
                int index = memory.getUByte(getPC());
                increasePC(1);
                stack.setLong(0, f.getLong(index * 4));
                stack.increaseSP(8);
                break;
            }
            case LOAD4_0:
            case LOAD4_1:
            case LOAD4_2:
            case LOAD4_3: {
                int index = opcode - LOAD4_0;
                stack.setInt(0, f.getInt(index * 4));
                stack.increaseSP(4);
                break;
            }
            case LOAD8_0:
            case LOAD8_1:
            case LOAD8_2:
            case LOAD8_3: {
                int index = opcode - LOAD8_0;
                stack.setLong(0, f.getLong(index * 4));
                stack.increaseSP(8);
                break;
            }
            case PLOAD1: {
                stack.setInt(-4, memory.getByte(stack.getInt(-4)));
                break;
            }
            case PLOAD2: {
                //TODO short and char are signed/unsigned. casting problem here?
                //separate opcodes PLOADS and PLOADC, or convention PLOAD2/PLOAD2 I2S 
                //probably the latter, unsigned load
                stack.setInt(-4, memory.getUShort(stack.getInt(-4)));
                break;
            }
            case PLOAD4: {
                stack.setInt(-4, memory.getInt(stack.getInt(-4)));
                break;
            }
            case PLOAD8: {
                stack.setLong(-4, memory.getLong(stack.getInt(-4)));
                stack.increaseSP(4);
                break;
            }
            case STORE4: {
                int index = memory.getUByte(getPC());
                increasePC(1);
                f.setInt(index * 4, stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case STORE8: {
                int index = memory.getUByte(getPC());
                increasePC(1);
                f.setLong(index * 4, stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case STORE4_0:
            case STORE4_1:
            case STORE4_2:
            case STORE4_3: {
                int index = opcode - STORE4_0;
                f.setInt(index * 4, stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case STORE8_0:
            case STORE8_1:
            case STORE8_2:
            case STORE8_3: {
                int index = opcode - STORE4_0;
                f.setLong(index * 4, stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case PSTORE1: {
                memory.setByte(stack.getInt(-8), (byte) stack.getInt(-4));
                stack.increaseSP(-8);
                break;
            }
            case PSTORE2: {
                //TODO short and char are signed/unsigned. casting problem here?
                //separate opcodes PSTORES and PSTOREC, or convention PSTORE2/I2C PSTORE2
                //probably the latter, unsigned store
                memory.setUShort(stack.getInt(-8), stack.getInt(-4));
                stack.increaseSP(-8);
                break;
            }
            case PSTORE4: {
                memory.setInt(stack.getInt(-8), stack.getInt(-4));
                stack.increaseSP(-8);
                break;
            }
            case PSTORE8: {
                memory.setLong(stack.getInt(-12), stack.getLong(-8));
                stack.increaseSP(-12);
                break;
            }
            case IADD: {
                stack.setInt(-8, stack.getInt(-8) + stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case LADD: {
                stack.setLong(-16, stack.getLong(-16) + stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case FADD: {
                stack.setFloat(-8, stack.getFloat(-8) + stack.getFloat(-4));
                stack.increaseSP(-4);
                break;
            }
            case DADD: {
                stack.setDouble(-16, stack.getDouble(-16) + stack.getDouble(-8));
                stack.increaseSP(-8);
                break;
            }
            case ISUB: {
                stack.setInt(-8, stack.getInt(-8) - stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case LSUB: {
                stack.setLong(-16, stack.getLong(-16) - stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case FSUB: {
                stack.setFloat(-8, stack.getFloat(-8) - stack.getFloat(-4));
                stack.increaseSP(-4);
                break;
            }
            case DSUB: {
                stack.setDouble(-16, stack.getDouble(-16) - stack.getDouble(-8));
                stack.increaseSP(-8);
                break;
            }
            case IMUL: {
                stack.setInt(-8, stack.getInt(-8) * stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case LMUL: {
                stack.setLong(-16, stack.getLong(-16) * stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case FMUL: {
                stack.setFloat(-8, stack.getFloat(-8) * stack.getFloat(-4));
                stack.increaseSP(-4);
                break;
            }
            case DMUL: {
                stack.setDouble(-16, stack.getDouble(-16) * stack.getDouble(-8));
                stack.increaseSP(-8);
                break;
            }
            case IDIV: {
                stack.setInt(-8, stack.getInt(-8) / stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case LDIV: {
                stack.setLong(-16, stack.getLong(-16) / stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case FDIV: {
                stack.setFloat(-8, stack.getFloat(-8) / stack.getFloat(-4));
                stack.increaseSP(-4);
                break;
            }
            case DDIV: {
                stack.setDouble(-16, stack.getDouble(-16) / stack.getDouble(-8));
                stack.increaseSP(-8);
                break;
            }
            case IREM: {
                stack.setInt(-8, stack.getInt(-8) % stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case LREM: {
                stack.setLong(-16, stack.getLong(-16) % stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case FREM: {
                stack.setFloat(-8, stack.getFloat(-8) % stack.getFloat(-4));
                stack.increaseSP(-4);
                break;
            }
            case DREM: {
                stack.setDouble(-16, stack.getDouble(-16) % stack.getDouble(-8));
                stack.increaseSP(-8);
                break;
            }
            case INEG: {
                stack.setInt(-4, -stack.getInt(-4));
                break;
            }
            case LNEG: {
                stack.setLong(-8, -stack.getLong(-8));
                break;
            }
            case FNEG: {
                stack.setFloat(-4, -stack.getFloat(-4));
                break;
            }
            case DNEG: {
                stack.setDouble(-8, -stack.getDouble(-8));
                break;
            }
            case SHL4: {
                stack.setInt(-8, stack.getInt(-8) << stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case SHL8: {
                stack.setLong(-16, stack.getLong(-16) << stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case SHR4: {
                stack.setInt(-8, stack.getInt(-8) >> stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case SHR8: {
                stack.setLong(-16, stack.getLong(-16) >> stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case USHR4: {
                stack.setInt(-8, stack.getInt(-8) >>> stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case USHR8: {
                //TODO right operands?
                stack.setLong(-16, stack.getLong(-16) >>> stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case AND4: {
                stack.setInt(-8, stack.getInt(-8) & stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case AND8: {
                stack.setLong(-16, stack.getLong(-16) & stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case OR4: {
                stack.setInt(-8, stack.getInt(-8) | stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case OR8: {
                stack.setLong(-16, stack.getLong(-16) | stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case XOR4: {
                stack.setInt(-8, stack.getInt(-8) ^ stack.getInt(-4));
                stack.increaseSP(-4);
                break;
            }
            case XOR8: {
                stack.setLong(-16, stack.getLong(-16) ^ stack.getLong(-8));
                stack.increaseSP(-8);
                break;
            }
            case CALL: {
                int method = memory.getUShort(getPC());
                increasePC(2);
                f = stack.allocateFrame(f, getPC(), method);
                setPC(f.getMethod().getCodeAddress());
                break;
            }
            case RETURN: {
                setPC(f.getReturnAddress());
                f = stack.deallocateFrame(f);
                break;
            }
            default:
                throw new AssertionError(opcode);
        }
        
        return f;
    }
}
