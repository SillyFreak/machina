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
    int NOP          = 0;
    
    int ACONST_NULL  = 1;  //
    int ICONST_M1    = 2;
    int ICONST_0     = 3;
    int ICONST_1     = 4;
    int ICONST_2     = 5;
    int ICONST_3     = 6;
    int ICONST_4     = 7;
    int ICONST_5     = 8;
    int LCONST_0     = 9;
    int LCONST_1     = 10;
    int FCONST_0     = 11;
    int FCONST_1     = 12;
    int FCONST_2     = 13;
    int DCONST_0     = 14;
    int DCONST_1     = 15;
    
    int IPUSH1       = 16;
    int IPUSH2       = 17;
    int LDC          = 18; //
    int LDC_W        = 19; //
    int LDC2_W       = 20; //
                            
    int PTR          = 21;
    int PTR_0        = 22;
    int PTR_1        = 23;
    int PTR_2        = 24;
    int PTR_3        = 25;
    
    int LOAD4        = 26;
    int LOAD8        = 27;
    int LOAD4_0      = 28;
    int LOAD4_1      = 29;
    int LOAD4_2      = 30;
    int LOAD4_3      = 31;
    int LOAD8_0      = 32;
    int LOAD8_1      = 33;
    int LOAD8_2      = 34;
    int LOAD8_3      = 35;
    int PLOAD1       = 36;
    int PLOAD2       = 37;
    int PLOAD4       = 38;
    int PLOAD8       = 39;
    int STORE4       = 40;
    int STORE8       = 41;
    int STORE4_0     = 42;
    int STORE4_1     = 43;
    int STORE4_2     = 44;
    int STORE4_3     = 45;
    int STORE8_0     = 46;
    int STORE8_1     = 47;
    int STORE8_2     = 48;
    int STORE8_3     = 49;
    int PSTORE1      = 50;
    int PSTORE2      = 51;
    int PSTORE4      = 52;
    int PSTORE8      = 53;
    
    int POP          = 54;
    int POP2         = 55;
    int DUP          = 56;
    int DUP_X1       = 57;
    int DUP_X2       = 58;
    int DUP2         = 59;
    int DUP2_X1      = 60;
    int DUP2_X2      = 61;
    int SWAP         = 62;
    
    int IADD         = 63;
    int LADD         = 64;
    int FADD         = 65;
    int DADD         = 66;
    int ISUB         = 67;
    int LSUB         = 68;
    int FSUB         = 69;
    int DSUB         = 70;
    int IMUL         = 71;
    int LMUL         = 72;
    int FMUL         = 73;
    int DMUL         = 74;
    int IDIV         = 75;
    int LDIV         = 76;
    int FDIV         = 77;
    int DDIV         = 78;
    int IREM         = 79;
    int LREM         = 80;
    int FREM         = 81;
    int DREM         = 82;
    int INEG         = 83;
    int LNEG         = 84;
    int FNEG         = 85;
    int DNEG         = 86;
    int SHL4         = 87;
    int SHL8         = 88;
    int SHR4         = 89;
    int SHR8         = 90;
    int USHR4        = 91;
    int USHR8        = 92;
    int AND4         = 93;
    int AND8         = 94;
    int OR4          = 95;
    int OR8          = 96;
    int XOR4         = 97;
    int XOR8         = 98;
    
    int CALL         = 99;
    int RETURN       = 100;
    
    //TODO
    
    int IINC         = 132; //
                            
    int I2L          = 133; //
    int I2F          = 134; //
    int I2D          = 135; //
    int L2I          = 136; //
    int L2F          = 137; //
    int L2D          = 138; //
    int F2I          = 139; //
    int F2L          = 140; //
    int F2D          = 141; //
    int D2I          = 142; //
    int D2L          = 143; //
    int D2F          = 144; //
    int I2B          = 145; //
    int I2C          = 146; //
    int I2S          = 147; //
                            
    int LCMP         = 148; //
    int FCMPL        = 149; //
    int FCMPG        = 150; //
    int DCMPL        = 151; //
    int DCMPG        = 152; //
    int IFEQ         = 153; //
    int IFNE         = 154; //
    int IFLT         = 155; //
    int IFGE         = 156; //
    int IFGT         = 157; //
    int IFLE         = 158; //
    int IF_ICMPEQ    = 159; //
    int IF_ICMPNE    = 160; //
    int IF_ICMPLT    = 161; //
    int IF_ICMPGE    = 162; //
    int IF_ICMPGT    = 163; //
    int IF_ICMPLE    = 164; //
    int IF_ACMPEQ    = 165; //
    int IF_ACMPNE    = 166; //
                            
    int GOTO         = 167; //
    int JSR          = 168; //
    int RET          = 169; //
    int TABLESWITCH  = 170; //
    int LOOKUPSWITCH = 171; //
                            
                            
    int GETSTATIC    = 178; //
    int PUTSTATIC    = 179; //
    int GETFIELD     = 180; //
    int PUTFIELD     = 181; //
                            
    int WIDE         = 196; //
}
