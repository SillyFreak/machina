/**
 * Opcodes.java
 * 
 * Created on 29.01.2013
 */

package machine;


/**
 * <p>
 * The class Opcodes.
 * </p>
 * 
 * @version V0.0 29.01.2013
 * @author SillyFreak
 */
public interface Opcodes {
    int NOP       = 0;
    int ICONST_M1 = 2;
    int ICONST_0  = 3;
    int ICONST_1  = 4;
    int ICONST_2  = 5;
    int ICONST_3  = 6;
    int ICONST_4  = 7;
    int ICONST_5  = 8;
    int ILOAD     = 21;
    int ILOAD_0   = 26;
    int ILOAD_1   = 27;
    int ILOAD_2   = 28;
    int ILOAD_3   = 29;
    int ISTORE    = 54;
    int ISTORE_0  = 59;
    int ISTORE_1  = 60;
    int ISTORE_2  = 61;
    int ISTORE_3  = 62;
    int IADD      = 96;
    int ISUB      = 100;
    int IMUL      = 104;
    int IDIV      = 108;
    int IREM      = 112;
    int IRETURN   = 172;
    int RETURN    = 177;
    int CALL      = 182; //OWN - invokevirtual
    int WIDE      = 196;
    
    //below: OWN - reserved
    
    int IPTR      = 203;
    int IPTR_0    = 204;
    int IPTR_1    = 205;
    int IPTR_2    = 206;
    int IPTR_3    = 207;
    int IPLOAD    = 208;
    int IPSTORE   = 209;
}
