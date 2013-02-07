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
        boolean wide = opcode == WIDE;
        if(wide) {
            opcode = memory.getUByte(getPC());
            increasePC(1);
        }
        
        switch(opcode) {
            case NOP:
            break;
            case ICONST_M1:
            case ICONST_0:
            case ICONST_1:
            case ICONST_2:
            case ICONST_3:
            case ICONST_4:
            case ICONST_5: {
                int iconst = opcode - ICONST_0;
                stack.setStackItem(0, iconst);
                stack.increaseSP(4);
            }
            break;
            case ILOAD: {
                int index = wide? memory.getUShort(getPC()):memory.getUByte(getPC());
                increasePC(wide? 2:1);
                stack.setStackItem(0, f.getLocal(index * 4));
                stack.increaseSP(4);
            }
            break;
            case ILOAD_0:
            case ILOAD_1:
            case ILOAD_2:
            case ILOAD_3: {
                int index = opcode - ILOAD_0;
                stack.setStackItem(0, f.getLocal(index * 4));
                stack.increaseSP(4);
            }
            break;
            case ISTORE: {
                int index = wide? memory.getUShort(getPC()):memory.getUByte(getPC());
                increasePC(wide? 2:1);
                f.setLocal(index * 4, stack.getStackItem(-4));
                stack.increaseSP(-4);
            }
            break;
            case ISTORE_0:
            case ISTORE_1:
            case ISTORE_2:
            case ISTORE_3: {
                int index = opcode - ISTORE_0;
                f.setLocal(index * 4, stack.getStackItem(-4));
                stack.increaseSP(-4);
            }
            break;
            case IADD:
                stack.setStackItem(-8, stack.getStackItem(-8) + stack.getStackItem(-4));
                stack.increaseSP(-4);
            break;
            case ISUB:
                stack.setStackItem(-8, stack.getStackItem(-8) - stack.getStackItem(-4));
                stack.increaseSP(-4);
            break;
            case IMUL:
                stack.setStackItem(-8, stack.getStackItem(-8) * stack.getStackItem(-4));
                stack.increaseSP(-4);
            break;
            case IDIV:
                stack.setStackItem(-8, stack.getStackItem(-8) / stack.getStackItem(-4));
                stack.increaseSP(-4);
            break;
            case IREM:
                stack.setStackItem(-8, stack.getStackItem(-8) % stack.getStackItem(-4));
                stack.increaseSP(-4);
            break;
            case IRETURN:
            case RETURN:
                setPC(f.getReturnAddress());
                stack.deallocateFrame(f);
                f = f.getFrame();
            break;
            case CALL: {
                int method = memory.getUShort(getPC());
                increasePC(2);
                f = stack.allocateFrame(f, getPC(), method);
                setPC(f.getMethod().getCodeAddress());
            }
            break;
            case IPTR: {
                int index = wide? memory.getUShort(getPC()):memory.getUByte(getPC());
                increasePC(wide? 2:1);
                stack.setStackItem(0, f.getAddress() + index * 4);
                stack.increaseSP(4);
            }
            break;
            case IPTR_0:
            case IPTR_1:
            case IPTR_2:
            case IPTR_3: {
                int index = opcode - ISTORE_0;
                stack.setStackItem(0, f.getAddress() + index * 4);
                stack.increaseSP(4);
            }
            break;
            case IPLOAD: {
                stack.setStackItem(-4, memory.getInt(stack.getStackItem(-4)));
            }
            break;
            case IPSTORE: {
                memory.setInt(stack.getStackItem(-8), stack.getStackItem(-4));
                stack.increaseSP(-8);
            }
            break;
            default:
                throw new AssertionError();
        }
        
        return f;
    }
}
