/**
 * Method.java
 * 
 * Created on 02.02.2013
 */

package machine;


/**
 * <p>
 * The class Method.
 * </p>
 * 
 * @version V0.0 02.02.2013
 * @author SillyFreak
 */
public class Method {
    //memory where the method is allocated
    private final Memory memory;
    //beginning address of the method
    private final int    address;
    
    public Method(Memory memory, int address) {
        this.memory = memory;
        this.address = address;
    }
    
    public int getAddress() {
        return address;
    }
    
    public int getCodeAddress() {
        return address + 5;
    }
    
    public void setParameters(int parameters) {
        memory.setUByte(address, parameters);
    }
    
    public int getParameters() {
        return memory.getUByte(address);
    }
    
    public void setReturns(int returns) {
        memory.setUByte(address + 1, returns);
    }
    
    public int getReturns() {
        return memory.getUByte(address + 1);
    }
    
    public void setMaxLocals(int maxLocals) {
        memory.setUByte(address + 2, maxLocals);
    }
    
    public int getMaxLocals() {
        return memory.getUByte(address + 2);
    }
    
    public void setMaxStack(int maxStack) {
        memory.setUByte(address + 3, maxStack);
    }
    
    public int getMaxStack() {
        return memory.getUByte(address + 3);
    }
    
    public void setCodeSize(int codeSize) {
        memory.setUByte(address + 4, codeSize);
    }
    
    public int getCodeSize() {
        return memory.getUByte(address + 4);
    }
    
    public void setCodeUByte(int index, int code) {
        memory.setUByte(getCodeAddress() + index, code);
    }
    
    public int getCodeUByte(int index) {
        return memory.getUByte(getCodeAddress() + index);
    }
    
    public void setCodeUShort(int index, int code) {
        memory.setUShort(getCodeAddress() + index, code);
    }
    
    public int getCodeUShort(int index) {
        return memory.getUShort(getCodeAddress() + index);
    }
    
    public void print() {
        memory.print(address, address + 5 + getCodeSize());
    }
}
