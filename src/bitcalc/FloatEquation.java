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
    final byte NUMBER_SIZE = 32;
    final byte MANT_LENGTH = 25;
    final byte SIGN_BIT = MANT_LENGTH;
    final byte EXP_START = MANT_LENGTH + 1;
    final byte EXP_LENGTH = NUMBER_SIZE - EXP_START;
    
    @Override
    public boolean addition()   {
        byte lesserExponent = checkLesser();
        while (lesserExponent < 2)   {
            addOne(lesserExponent, EXP_START);
            bitShiftHalf(lesserExponent, 0, MANT_LENGTH);
            lesserExponent = checkLesser();
        }

        System.arraycopy(binaryNumber[OPERAND1], EXP_START, binaryNumber[RESULT], EXP_START, EXP_LENGTH);
//        for (byte i = EXP_START; i < EXP_START + EXP_LENGTH; i++)
//            binaryNumber[RESULT][i] = binaryNumber[OPERAND1][i];

        return addRange(0, MANT_LENGTH + 1);
    }
    
    @Override
    public boolean subtraction()    {
        multByNegOne(OPERAND2, 0, MANT_LENGTH + 1);
        return addition();
    }
    
    private byte checkLesser()  {
        byte signBit = EXP_START + EXP_LENGTH - 1;
        if (binaryNumber[OPERAND1][signBit] != binaryNumber[OPERAND2][signBit])
                return (binaryNumber[OPERAND1][signBit] ? OPERAND1 : OPERAND2);
        for (byte i = signBit - 1; i >= EXP_START; i--)
            if (binaryNumber[OPERAND1][i] != binaryNumber[OPERAND2][i])
                return (binaryNumber[OPERAND1][i] ? OPERAND2 : OPERAND1);
        return 2;
    }
    
    private void bitShiftHalf(byte whichNumber, byte start, byte length)    {
        for (byte i = start; i < start + length - 1; i++)
            binaryNumber[whichNumber][i] = binaryNumber[whichNumber][i+1];
        binaryNumber[whichNumber][start + length - 1] = false;
    }
    
    private void subtractRange (byte minuend, int start, int length)    {
        boolean borrowFlag = false;
        int end = start + length;
        byte subtrahend = (minuend + 1) % 2;
        
        for (int i = start; i < end; i++)   {
            binaryNumber[RESULT][i] = binaryNumber[minuend][i] ^ (binaryNumber[subtrahend][i] ^ borrowFlag);
            borrowFlag = binaryNumber[minuend][i] ? (binaryNumber[subtrahend][i] && borrowFlag) : (binaryNumber[subtrahend][i] || borrowFlag);
        }
    }
}
