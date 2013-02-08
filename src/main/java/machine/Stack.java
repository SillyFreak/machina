/**
 * Stack.java
 * 
 * Created on 30.01.2013
 */

package machine;


/**
 * <p>
 * The class Stack.
 * </p>
 * 
 * @version V0.0 30.01.2013
 * @author SillyFreak
 */
public class Stack {
    //memory where the stack is allocated
    private final Memory memory;
    //beginning address of the stack
    private final int    address;
    //current stack pointer - the address where a push pushes its value to
    private int          sp;
    
    public Stack(Memory memory, int address) {
        this.memory = memory;
        this.address = address;
        sp = address;
    }
    
    public void setSP(int sp) {
        this.sp = sp;
    }
    
    public int getSP() {
        return sp;
    }
    
    public void increaseSP(int offset) {
        sp += offset;
    }
    
    public void setInt(int offset, int item) {
        memory.setInt(sp + offset, item);
    }
    
    public int getInt(int offset) {
        return memory.getInt(sp + offset);
    }
    
    public void setLong(int offset, long item) {
        memory.setLong(sp + offset, item);
    }
    
    public long getLong(int offset) {
        return memory.getLong(sp + offset);
    }
    
    public void setFloat(int offset, float item) {
        memory.setFloat(sp + offset, item);
    }
    
    public float getFloat(int offset) {
        return memory.getFloat(sp + offset);
    }
    
    public void setDouble(int offset, double item) {
        memory.setDouble(sp + offset, item);
    }
    
    public double getDouble(int offset) {
        return memory.getDouble(sp + offset);
    }
    
    /**
     * Allocates a new stack frame. The new frame will overlap with the top of the stack to contain
     * {@code parameters} entries from the calling frame. The frame will contain {@code maxLocals} local variable
     * slots and {@code maxStack} stack slots.
     * 
     * Allocating the frame will put the stack pointer on top of the new frame, and will store the
     * {@code returnAddress} in the frame data segment.
     */
    public StackFrame allocateFrame(StackFrame current, int returnAddress, int method) {
        StackFrame frame = new StackFrame(memory, current, sp, method);
        frame.setReturnAddress(returnAddress);
        sp = frame.getBottomOfStack();
        
        return frame;
    }
    
    /**
     * Deallocates the given frame.
     * 
     * A return from the frame will move appropriate elements from the current frame's stack onto the calling
     * frame's stack, and set the stack pointer on top of the calling frame's stack.
     * 
     * The frame's {@code returnAddress} is returned.
     */
    public StackFrame deallocateFrame(StackFrame frame) {
        int returns = frame.method.getReturns();
        int address = frame.getAddress(), bottom = frame.getBottomOfStack();
        for(int i = 0; i < returns; i++) {
            memory.setInt(address + 4 * i, memory.getInt(bottom + 4 * i));
        }
        sp = address + returns * 4;
        
        return frame.frame;
    }
    
    public void print() {
        memory.print(address, sp);
    }
    
    public static class StackFrame {
        private final Memory memory;
        private StackFrame   frame;
        private final Method method;
        private final int    address;
        
        public StackFrame(Memory memory, StackFrame frame, int sp, int method) {
            this.memory = memory;
            this.frame = frame;
            this.method = new Method(memory, method);
            this.address = sp - this.method.getParameters() * 4;
        }
        
        public int getAddress() {
            return address;
        }
        
        public Method getMethod() {
            return method;
        }
        
        public int getBottomOfStack() {
            return address + method.getMaxLocals() * 4 + 2;
        }
        
        private void setReturnAddress(int returnAddress) {
            memory.setUShort(getBottomOfStack() - 2, returnAddress);
        }
        
        public int getReturnAddress() {
            return memory.getUShort(getBottomOfStack() - 2);
        }
        
        public void setInt(int index, int item) {
            memory.setInt(address + index, item);
        }
        
        public int getInt(int index) {
            return memory.getInt(address + index);
        }
        
        public void setLong(int index, long item) {
            memory.setLong(address + index, item);
        }
        
        public long getLong(int index) {
            return memory.getLong(address + index);
        }
        
        public void print() {
            memory.print(address, address + 2 + (method.getMaxLocals() + method.getMaxStack()) * 4);
        }
    }
}
