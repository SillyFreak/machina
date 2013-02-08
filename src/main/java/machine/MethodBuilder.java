/**
 * MethodBuilder.java
 * 
 * Created on 07.02.2013
 */

package machine;


import static machine.Opcodes.*;


/**
 * <p>
 * The class MethodBuilder.
 * </p>
 * 
 * @version V0.0 07.02.2013
 * @author SillyFreak
 */
public class MethodBuilder {
    private final Method m;
    private int          codeSize = 0;
    
    public MethodBuilder(Method method) {
        this.m = method;
    }
    
    public void setMeta(int params, int returns, int maxLocals, int maxStack) {
        m.setParameters(params);
        m.setReturns(returns);
        m.setMaxLocals(maxLocals);
        m.setMaxStack(maxStack);
    }
    
    public MethodBuilder addInsn(int opcode) {
        m.setCodeUByte(codeSize, opcode);
        codeSize++;
        return this;
    }
    
    public MethodBuilder addCallInsn(Method method) {
        return addCallInsn(method.getAddress());
    }
    
    public MethodBuilder addCallInsn(int method) {
        m.setCodeUByte(codeSize, CALL);
        m.setCodeUShort(codeSize + 1, method);
        codeSize += 3;
        return this;
    }
    
    public MethodBuilder addVarInsn(int opcode, int var, boolean wide) {
        if(wide) {
            m.setCodeUByte(codeSize, WIDE);
            m.setCodeUByte(codeSize + 1, opcode);
            m.setCodeUShort(codeSize + 2, var);
            codeSize += 4;
        } else {
            m.setCodeUByte(codeSize, opcode);
            m.setCodeUByte(codeSize + 1, var);
            codeSize += 2;
        }
        return this;
    }
    
    public MethodBuilder addPtrInsn(int var, boolean wide) {
        if(wide) {
            m.setCodeUByte(codeSize, WIDE);
            m.setCodeUByte(codeSize + 1, PTR);
            m.setCodeUShort(codeSize + 2, var);
            codeSize += 4;
        } else {
            m.setCodeUByte(codeSize, PTR);
            m.setCodeUByte(codeSize + 1, var);
            codeSize += 2;
        }
        return this;
    }
    
    public Method finish() {
        m.setCodeSize(codeSize);
        return m;
    }
}
