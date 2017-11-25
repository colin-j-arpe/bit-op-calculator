/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcalc;

import java.util.Arrays;

/**
 *
 * @author cspanw74
 */
public class FloatEquation extends Equation {
    final int NUMBER_SIZE = 32;
    final int SIGN_BIT = NUMBER_SIZE - 1;
    final int MANT_LENGTH = 25;
    final int EXP_START = MANT_LENGTH;
    final int EXP_LENGTH = NUMBER_SIZE - EXP_START - 1;
    
    @Override
    public boolean addition()   {
        byte lesserExponent;
        byte greaterExponent = checkGreater(EXP_START, EXP_LENGTH, true);
        byte greaterMantissa = checkGreater(0, MANT_LENGTH, false);
        byte greaterOperand = (byte)((greaterExponent < 2) ? greaterExponent : greaterMantissa);
        if (greaterOperand > 1) greaterOperand = 0;
        lesserExponent = (byte)((greaterExponent + 1) % 2);

        while (greaterExponent < 2)   {
            addOne(lesserExponent, EXP_START, EXP_LENGTH);
            bitShiftHalf(lesserExponent, 0, MANT_LENGTH);
            greaterExponent = checkGreater(EXP_START, EXP_LENGTH, true);
        }

        binaryNumber[RESULT][SIGN_BIT] = binaryNumber[greaterOperand][SIGN_BIT];

        if (binaryNumber[OPERAND1][SIGN_BIT] == binaryNumber[OPERAND2][SIGN_BIT])    {
            for (byte i = 0; i < 2; i++) {
                addOne(i, EXP_START, EXP_LENGTH);
                bitShiftHalf(i, 0, MANT_LENGTH);
            }
//System.out.println("adding");
            addRange(0, MANT_LENGTH);
//            return false;
        }   else    {
//System.out.println("subtracting");
            subtractRange(greaterOperand, 0, MANT_LENGTH);
//            return false;
        }

        System.arraycopy(binaryNumber[OPERAND1], EXP_START, binaryNumber[RESULT], EXP_START, EXP_LENGTH);
        while (!binaryNumber[RESULT][MANT_LENGTH - 1])  {
            subtractOne(RESULT, EXP_START, EXP_LENGTH);
            bitShiftDouble(RESULT, 0, MANT_LENGTH);
        }
//        for (byte i = EXP_START; i < EXP_START + EXP_LENGTH; i++)
//            binaryNumber[RESULT][i] = binaryNumber[OPERAND1][i];
        return false;
//        return addRange(0, MANT_LENGTH + 1);
    }
    
    @Override
    public boolean subtraction()    {
        binaryNumber[OPERAND2][SIGN_BIT] = !binaryNumber[OPERAND2][SIGN_BIT];
        return addition();
    }
    
    @Override
    public boolean multiplication() {
        binaryNumber[RESULT][31] = binaryNumber[OPERAND1][31] ^ binaryNumber[OPERAND2][31];
        
        addOne(OPERAND1, EXP_START, EXP_LENGTH);
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
        
        return addRange(EXP_START, EXP_LENGTH);
    }
    
    @Override
    public void division()   {
        binaryNumber[RESULT][SIGN_BIT] = binaryNumber[OPERAND1][SIGN_BIT] ^ binaryNumber[OPERAND2][SIGN_BIT];
        subtractRange(OPERAND1, EXP_START, EXP_LENGTH);
        
        for (int i = MANT_LENGTH - 1; i >= 0; i--)  {
            if (checkGreater(0, MANT_LENGTH, false) != 1)   {
                subtractRange(OPERAND1, 0, MANT_LENGTH);
                System.arraycopy(binaryNumber[RESULT], 0, binaryNumber[OPERAND1], 0, MANT_LENGTH);
                binaryNumber[TEMP][i] = true;
            }   else binaryNumber[TEMP][i] = false;
            bitShiftHalf(OPERAND2, 0, MANT_LENGTH);
        }
        
        System.arraycopy(binaryNumber[TEMP], 0, binaryNumber[RESULT], 0, MANT_LENGTH);
        while (!binaryNumber[RESULT][MANT_LENGTH - 1])  {
            subtractOne(RESULT, EXP_START, EXP_LENGTH);
            bitShiftDouble(RESULT, 0, MANT_LENGTH);
        }
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
        int end = start + length;
        byte subtrahend = (byte)((minuend + 1) % 2);
        
        for (int i = start; i < end; i++)   {
            binaryNumber[RESULT][i] = binaryNumber[minuend][i] ^ (binaryNumber[subtrahend][i] ^ borrowFlag);
            borrowFlag = binaryNumber[minuend][i] ? (binaryNumber[subtrahend][i] && borrowFlag) : (binaryNumber[subtrahend][i] || borrowFlag);
        }
        
        return borrowFlag;
    }
}
