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
    public static void main(String[] args) {
        Machine machine = new Machine();
        Memory mem = machine.memory;
        Stack s = machine.stack;
        
        Method add = add(new Method(mem, 0x0000)); //add (II)I
        
        Method mul = mul(new Method(mem, 0x0100)); //mul (II)I
        
        Method square = square(new Method(mem, 0x0200), mul); //square (I)I
        
        Method swap = swap(new Method(mem, 0x0300)); //swap (*I*I)V
        
        Method swap2 = swap2(new Method(mem, 0x0400)); //swap (II)II
        
        s.setStackItem(0, 1);
        s.setStackItem(4, 2);
        s.increaseSP(8);
        
        StackFrame f = s.allocateFrame(null, 0xFFFF, swap2.getAddress());
        machine.setPC(f.getMethod().getCodeAddress());
        
        while(f != null) {
            System.out.printf("0x%04X%n", s.getSP());
            s.print();
            System.out.println();
            System.out.printf("0x%04X  0x%04X%n", f.getMethod().getAddress(), machine.getPC());
            f.getMethod().print();
            f = machine.executeInstruction(f);
        }
        
        System.out.printf("0x%04X%n", s.getSP());
        s.print();
        
        System.out.println(s.getStackItem(-8));
        System.out.println(s.getStackItem(-4));
    }
    
    private static Method add(Method m) {
        m.setCodeUByte(0, ILOAD_0);
        m.setCodeUByte(1, ILOAD_1);
        m.setCodeUByte(2, IADD);
        m.setCodeUByte(3, IRETURN);
        m.setParameters(2);
        m.setReturns(1);
        m.setMaxLocals(2);
        m.setMaxStack(2);
        m.setCodeSize(4);
        
        return m;
    }
    
    private static Method mul(Method m) {
        m.setCodeUByte(0, ILOAD_0);
        m.setCodeUByte(1, ILOAD_1);
        m.setCodeUByte(2, IMUL);
        m.setCodeUByte(3, IRETURN);
        m.setParameters(2);
        m.setReturns(1);
        m.setMaxLocals(2);
        m.setMaxStack(2);
        m.setCodeSize(4);
        
        return m;
    }
    
    private static Method square(Method m, Method mul) {
        m.setCodeUByte(0, ILOAD_0);
        m.setCodeUByte(1, ILOAD_0);
        m.setCodeUByte(2, CALL);
        m.setCodeUShort(3, mul.getAddress());
        m.setCodeUByte(5, IRETURN);
        m.setParameters(1);
        m.setReturns(1);
        m.setMaxLocals(1);
        m.setMaxStack(2);
        m.setCodeSize(6);
        
        return m;
    }
    
    private static Method swap(Method m) {
        m.setCodeUByte(0, ILOAD_0);
        m.setCodeUByte(1, IPLOAD);
        m.setCodeUByte(2, ISTORE_2);
        m.setCodeUByte(3, ILOAD_0);
        m.setCodeUByte(4, ILOAD_1);
        m.setCodeUByte(5, IPLOAD);
        m.setCodeUByte(6, IPSTORE);
        m.setCodeUByte(7, ILOAD_1);
        m.setCodeUByte(8, ILOAD_2);
        m.setCodeUByte(9, IPSTORE);
        m.setCodeUByte(10, RETURN);
        m.setParameters(2);
        m.setReturns(0);
        m.setMaxLocals(3);
        m.setMaxStack(2);
        m.setCodeSize(11);
        
        return m;
    }
    
    private static Method swap2(Method m) {
        m.setCodeUByte(0, ILOAD_1);
        m.setCodeUByte(1, ILOAD_0);
        m.setCodeUByte(2, RETURN);
        m.setParameters(2);
        m.setReturns(2);
        m.setMaxLocals(2);
        m.setMaxStack(2);
        m.setCodeSize(3);
        
        return m;
    }
    
    private final Memory memory;
    private final Stack  stack;
    private int          pc;
    
    public Machine() {
        memory = new Memory(0x10000);
        stack = new Stack(memory, 0x1000);
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
