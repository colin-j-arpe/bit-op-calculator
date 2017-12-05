package bitcalc;

import java.util.Arrays;

public class FloatEquation extends Equation {
    final static int NUMBER_SIZE = 32;
    final static int SIGN_BIT = NUMBER_SIZE - 1;
    final static int MANT_LENGTH = 25;
    final static int EXP_START = MANT_LENGTH;
    final static int EXP_LENGTH = NUMBER_SIZE - EXP_START - 1;
    
    @Override
    public boolean addition()   {
System.out.println("here: 1");
        byte lesserExponent;
        byte greaterExponent = checkGreater(EXP_START, EXP_LENGTH, true);
        byte greaterMantissa = checkGreater(0, MANT_LENGTH, false);
        byte greaterOperand = (byte)((greaterExponent < 2) ? greaterExponent : greaterMantissa);
        if (greaterOperand == 2) greaterOperand = OPERAND1;
        lesserExponent = (byte)((greaterExponent + 1) % 2);

System.out.println("here: 2");
        while (greaterExponent < 2)   {
            if (addOne(lesserExponent, EXP_START, EXP_LENGTH)) return true;
            bitShiftHalf(lesserExponent, 0, MANT_LENGTH);
            greaterExponent = checkGreater(EXP_START, EXP_LENGTH, true);
        }

System.out.println("here: 3");
        binaryNumber[RESULT][SIGN_BIT] = binaryNumber[greaterOperand][SIGN_BIT];

        if (binaryNumber[OPERAND1][SIGN_BIT] == binaryNumber[OPERAND2][SIGN_BIT])    {
            for (byte i = 0; i < 2; i++) {
                if (addOne(i, EXP_START, EXP_LENGTH)) return true;
                bitShiftHalf(i, 0, MANT_LENGTH);
            }
            addRange(0, MANT_LENGTH);
System.out.println("here: 4");
        }   else    {
            subtractRange(greaterOperand, 0, MANT_LENGTH);
System.out.println("here: 5");
        }

        System.arraycopy(binaryNumber[OPERAND1], EXP_START, binaryNumber[RESULT], EXP_START, EXP_LENGTH);
System.out.println("here: 6");
        oneToTheFront(RESULT);
System.out.println("here: 7");
        return false;
    }
    
    @Override
    public boolean subtraction()    {
        binaryNumber[OPERAND2][SIGN_BIT] = !binaryNumber[OPERAND2][SIGN_BIT];
        return addition();
    }
    
    @Override
    public boolean multiplication() {
        binaryNumber[RESULT][31] = binaryNumber[OPERAND1][31] ^ binaryNumber[OPERAND2][31];
        
        if (addOne(OPERAND1, EXP_START, EXP_LENGTH)) return true;
        bitShiftHalf(OPERAND1, 0, MANT_LENGTH);
        System.arraycopy(binaryNumber[OPERAND2], 0, binaryNumber[TEMP], 0, MANT_LENGTH);
        Arrays.fill(binaryNumber[OPERAND2], 0, MANT_LENGTH, false);
        
        for (int i = MANT_LENGTH - 1; i >= 0; i--)  {
            if (binaryNumber[TEMP][i])  {
                addRange(0, MANT_LENGTH);
                System.arraycopy(binaryNumber[RESULT], 0, binaryNumber[OPERAND2], 0, MANT_LENGTH);
            }
            bitShiftHalf(OPERAND1, 0, MANT_LENGTH);
        }
        
        if (addRange(EXP_START, EXP_LENGTH)) return true;
        oneToTheFront(RESULT);
        return false;
    }
    
    @Override
    public boolean division()   {
    //  Catch division by zero
        if (!binaryNumber[OPERAND2][24])
            return true;
        
        binaryNumber[RESULT][SIGN_BIT] = binaryNumber[OPERAND1][SIGN_BIT] ^ binaryNumber[OPERAND2][SIGN_BIT];
        if (subtractRange(OPERAND1, EXP_START, EXP_LENGTH)) return true;
        
        for (int i = MANT_LENGTH - 1; i >= 0; i--)  {
            if (checkGreater(0, MANT_LENGTH, false) != OPERAND2)   {
                subtractRange(OPERAND1, 0, MANT_LENGTH);
                System.arraycopy(binaryNumber[RESULT], 0, binaryNumber[OPERAND1], 0, MANT_LENGTH);
                binaryNumber[TEMP][i] = true;
            }   else binaryNumber[TEMP][i] = false;
            bitShiftHalf(OPERAND2, 0, MANT_LENGTH);
        }
        
        System.arraycopy(binaryNumber[TEMP], 0, binaryNumber[RESULT], 0, MANT_LENGTH);
        oneToTheFront(RESULT);
        return false;
    }
    
    private byte checkGreater(int start, int length, boolean isSigned)  {
        if (isSigned)   {
            int signBit = start + length - 1;
            if (binaryNumber[OPERAND1][signBit] != binaryNumber[OPERAND2][signBit])
                    return (binaryNumber[OPERAND1][signBit] ? OPERAND2 : OPERAND1);
        }
        for (int i = start + length - 1; i >= start; i--)
            if (binaryNumber[OPERAND1][i] != binaryNumber[OPERAND2][i])
                return (binaryNumber[OPERAND1][i] ? OPERAND1 : OPERAND2);
        return 2;
    }
    
    private void bitShiftHalf(byte whichNumber, int start, int length)    {
        for (int i = start; i < start + length - 1; i++)
            binaryNumber[whichNumber][i] = binaryNumber[whichNumber][i+1];
        binaryNumber[whichNumber][start + length - 1] = false;
    }
    
    private boolean subtractRange (byte minuend, int start, int length)    {
        boolean borrowFlag = false;
        int endBit = start + length - 1;
        byte subtrahend = (byte)((minuend + 1) % 2);
        
        for (int i = start; i <= endBit; i++)   {
            binaryNumber[RESULT][i] = binaryNumber[minuend][i] ^ (binaryNumber[subtrahend][i] ^ borrowFlag);
            borrowFlag = binaryNumber[minuend][i] ? (binaryNumber[subtrahend][i] && borrowFlag) : (binaryNumber[subtrahend][i] || borrowFlag);
        }
    
    //  Return true if result is out of range
        if (binaryNumber[minuend][endBit] ^ binaryNumber[subtrahend][endBit])
            return !(binaryNumber[RESULT][endBit] ^ borrowFlag);
        else return false;
    }
    
    //  This method ensures that the mantissa is stored as a value between 1 and 2, i.e., 1.xxx...
    private void oneToTheFront (byte whichNumber)   {
        if (checkZero(whichNumber, 0, MANT_LENGTH)) return;
        while (!binaryNumber[whichNumber][MANT_LENGTH - 1])  {
            subtractOne(whichNumber, EXP_START, EXP_LENGTH);
            bitShiftDouble(whichNumber, 0, MANT_LENGTH);
        }
    }
}
