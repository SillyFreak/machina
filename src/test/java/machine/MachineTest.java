/**
 * MachineTest.java
 * 
 * Created on 07.02.2013
 */

package machine;


import static machine.Opcodes.*;
import machine.Stack.StackFrame;

import org.junit.Assert;
import org.junit.Test;


/**
 * <p>
 * The class MachineTest.
 * </p>
 * 
 * @version V0.0 07.02.2013
 * @author SillyFreak
 */
public class MachineTest {
    private static Method add(Machine m, int address) {
        MethodBuilder mb = new MethodBuilder(new Method(m.getMemory(), address));
        mb.setMeta(2, 1, 2, 2);
        mb.addInsn(ILOAD_0);
        mb.addInsn(ILOAD_1);
        mb.addInsn(IADD);
        mb.addInsn(IRETURN);
        
        return mb.finish();
    }
    
    private static Method mul(Machine m, int address) {
        MethodBuilder mb = new MethodBuilder(new Method(m.getMemory(), address));
        mb.setMeta(2, 1, 2, 2);
        mb.addInsn(ILOAD_0);
        mb.addInsn(ILOAD_1);
        mb.addInsn(IMUL);
        mb.addInsn(IRETURN);
        
        return mb.finish();
    }
    
    private static Method square(Machine m, int address, Method mul) {
        MethodBuilder mb = new MethodBuilder(new Method(m.getMemory(), address));
        mb.setMeta(1, 1, 1, 2);
        mb.addInsn(ILOAD_0);
        mb.addInsn(ILOAD_0);
        mb.addCallInsn(mul);
        mb.addInsn(IRETURN);
        
        return mb.finish();
    }
    
    private static Method swap(Machine m, int address) {
        MethodBuilder mb = new MethodBuilder(new Method(m.getMemory(), address));
        mb.setMeta(2, 0, 3, 2);
        mb.addInsn(ILOAD_0);
        mb.addInsn(IPLOAD);
        mb.addInsn(ISTORE_2);
        mb.addInsn(ILOAD_0);
        mb.addInsn(ILOAD_1);
        mb.addInsn(IPLOAD);
        mb.addInsn(IPSTORE);
        mb.addInsn(ILOAD_1);
        mb.addInsn(ILOAD_2);
        mb.addInsn(IPSTORE);
        mb.addInsn(RETURN);
        
        return mb.finish();
    }
    
    private static Method swap2(Machine m, int address) {
        MethodBuilder mb = new MethodBuilder(new Method(m.getMemory(), address));
        mb.setMeta(2, 2, 2, 2);
        mb.addInsn(ILOAD_1);
        mb.addInsn(ILOAD_0);
        mb.addInsn(RETURN);
        
        return mb.finish();
    }
    
    @Test
    public void testAdd() {
        //set up machine
        Machine m = new Machine();
        Stack s = m.getStack();
        Method add = add(m, 0x0000);
        
        //push parameters
        s.setStackItem(0x0, 0x0001);
        s.setStackItem(0x4, 0x0002);
        s.increaseSP(0x8);
        
        //"call" main function
        Method main = add;
        m.setPC(main.getCodeAddress());
        StackFrame f = s.allocateFrame(null, 0xFFFF, main.getAddress());
        
        //run program
        while(f != null)
            f = m.executeInstruction(f);
        
        //compare results
        int result = s.getStackItem(-0x4);
        System.out.printf("0x%04X%n", result);
        Assert.assertEquals(3, result);
    }
    
    @Test
    public void testSquare() {
        //set up machine
        Machine m = new Machine();
        Stack s = m.getStack();
        Method mul = mul(m, 0x0000);
        Method square = square(m, 0x0100, mul);
        
        //push parameters
        s.setStackItem(0x0, 0x0003);
        s.increaseSP(0x4);
        
        //"call" main function
        Method main = square;
        m.setPC(main.getCodeAddress());
        StackFrame f = s.allocateFrame(null, 0xFFFF, main.getAddress());
        
        //run program
        while(f != null)
            f = m.executeInstruction(f);
        
        //compare results
        int result = s.getStackItem(-0x4);
        System.out.printf("0x%04X%n", result);
        Assert.assertEquals(9, result);
    }
    
    @Test
    public void testSwap() {
        //set up machine
        Machine m = new Machine();
        Stack s = m.getStack();
        Method swap = swap(m, 0x0000);
        
        //push parameters
        m.getMemory().setInt(0x0100, 0x0001);
        m.getMemory().setInt(0x0104, 0x0002);
        s.setStackItem(0x0, 0x0100);
        s.setStackItem(0x4, 0x0104);
        s.increaseSP(0x8);
        
        //"call" main function
        Method main = swap;
        m.setPC(main.getCodeAddress());
        StackFrame f = s.allocateFrame(null, 0xFFFF, main.getAddress());
        
        //run program
        while(f != null)
            f = m.executeInstruction(f);
        
        //compare results
        int result1 = m.getMemory().getInt(0x0100);
        int result2 = m.getMemory().getInt(0x0104);
        System.out.printf("0x%04X 0x%04X%n", result1, result2);
        Assert.assertEquals(2, result1);
        Assert.assertEquals(1, result2);
    }
    
    @Test
    public void testSwap2() {
        //set up machine
        Machine m = new Machine();
        Stack s = m.getStack();
        Method swap2 = swap2(m, 0x0000);
        
        //push parameters
        s.setStackItem(0x0, 0x0001);
        s.setStackItem(0x4, 0x0002);
        s.increaseSP(0x8);
        
        //"call" main function
        Method main = swap2;
        m.setPC(main.getCodeAddress());
        StackFrame f = s.allocateFrame(null, 0xFFFF, main.getAddress());
        
        //run program
        while(f != null)
            f = m.executeInstruction(f);
        
        //compare results
        int result1 = s.getStackItem(-0x8);
        int result2 = s.getStackItem(-0x4);
        System.out.printf("0x%04X 0x%04X%n", result1, result2);
        Assert.assertEquals(2, result1);
        Assert.assertEquals(1, result2);
    }
}
