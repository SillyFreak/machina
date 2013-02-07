/**
 * Memory.java
 * 
 * Created on 29.01.2013
 */

package machine;


import java.nio.ByteBuffer;


/**
 * <p>
 * The class Memory.
 * </p>
 * 
 * @version V0.0 29.01.2013
 * @author SillyFreak
 */
public class Memory {
    private ByteBuffer memory;
    
    public Memory(int size) {
        memory = ByteBuffer.allocate(size);
    }
    
    public int getSize() {
        return memory.capacity();
    }
    
    
    public byte getByte(int address) {
        return memory.get(address);
    }
    
    public void setByte(int address, byte value) {
        memory.put(address, value);
    }
    
    public short getShort(int address) {
        return memory.getShort(address);
    }
    
    public void setShort(int address, short value) {
        memory.putShort(address, value);
    }
    
    public int getInt(int address) {
        return memory.getInt(address);
    }
    
    public void setInt(int address, int value) {
        memory.putInt(address, value);
    }
    
    public long getLong(int address) {
        return memory.getLong(address);
    }
    
    public void setLong(int address, long value) {
        memory.putLong(address, value);
    }
    
    
    public float getFloat(int address) {
        return memory.getFloat(address);
    }
    
    public void setFloat(int address, float value) {
        memory.putFloat(address, value);
    }
    
    public double getDouble(int address) {
        return memory.getDouble(address);
    }
    
    public void setDouble(int address, double value) {
        memory.putDouble(address, value);
    }
    
    
    public boolean getBoolean(int address) {
        return memory.get(address) != 0;
    }
    
    public void setBoolean(int address, boolean value) {
        memory.put(address, (byte) (value? 1:0));
    }
    
    public char getChar(int address) {
        return memory.getChar(address);
    }
    
    public void setChar(int address, char value) {
        memory.putChar(address, value);
    }
    
    
    public int getUByte(int address) {
        return memory.get(address) & 0xFF;
    }
    
    public void setUByte(int address, int value) {
        if(value < 0x00 || value > 0xFF) throw new IllegalArgumentException("Not in unsigned byte range");
        memory.put(address, (byte) value);
    }
    
    public int getUShort(int address) {
        return memory.getShort(address) & 0xFFFF;
    }
    
    public void setUShort(int address, int value) {
        if(value < 0x0000 || value > 0xFFFF) throw new IllegalArgumentException("Not in unsigned short range");
        memory.putShort(address, (short) value);
    }
    
    public void print(int lower, int upper) {
        for(int i = lower; i < upper; i++) {
            System.out.printf("%02X ", getUByte(i));
            if((i - lower) % 4 == 3) System.out.print(" ");
        }
        System.out.println();
    }
}
