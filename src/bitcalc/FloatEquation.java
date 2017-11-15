/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcalc;

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

        while (greaterExponent < 2)   {
            lesserExponent = (byte)((greaterExponent + 1) % 2);
            addOne(lesserExponent, EXP_START, EXP_LENGTH);
            bitShiftHalf(lesserExponent, 0, MANT_LENGTH);
            greaterExponent = checkGreater(EXP_START, EXP_LENGTH, true);
        }

        binaryNumber[RESULT][SIGN_BIT] = binaryNumber[greaterOperand][SIGN_BIT];

        if (binaryNumber[OPERAND1][SIGN_BIT] ^ binaryNumber[OPERAND2][SIGN_BIT])    {
            subtractRange(greaterOperand, 0, MANT_LENGTH);
//            return false;
        }   else    {
            for (byte i = 0; i < 2; i++) {
                addOne(i, EXP_START, EXP_LENGTH);
                bitShiftHalf(i, 0, MANT_LENGTH);
            }
            addRange(0, MANT_LENGTH);
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
        return true;
    }
    
    @Override
    public void division()   {
        return;
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
    
    private void subtractRange (byte minuend, int start, int length)    {
        boolean borrowFlag = false;
        int end = start + length;
        byte subtrahend = (byte)((minuend + 1) % 2);
        
        for (int i = start; i < end; i++)   {
            binaryNumber[RESULT][i] = binaryNumber[minuend][i] ^ (binaryNumber[subtrahend][i] ^ borrowFlag);
            borrowFlag = binaryNumber[minuend][i] ? (binaryNumber[subtrahend][i] && borrowFlag) : (binaryNumber[subtrahend][i] || borrowFlag);
        }
    }
}
