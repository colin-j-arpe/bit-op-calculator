package bitcalc;

import java.util.Arrays;

public class Equation   {
    boolean[][] binaryNumber = new boolean[4][32];
    final static byte OPERAND1 = 0;
    final static byte OPERAND2 = 1;
    final static byte RESULT = 2;
    final static byte TEMP = 3;
    
    public boolean addition()   {
        return addRange(0, 32);
    }

    public boolean subtraction()   {
        multByNegOne(OPERAND2, 0, 32);
        return addition();
    }
    
    public boolean multiplication()    {
        boolean negativeResult = removeNegatives();
        int multiplierIndex = 0;
        
        System.arraycopy(binaryNumber[OPERAND2], 0, binaryNumber[TEMP], 0, 32);
        Arrays.fill(binaryNumber[OPERAND2], false);
        
        do  {
            if (binaryNumber[TEMP][multiplierIndex])    {
                addition();
                System.arraycopy(binaryNumber[RESULT], 0, binaryNumber[OPERAND2], 0, 32);
            }
            multiplierIndex++;
        }   while (!bitShiftDouble(OPERAND1, 0, 32));
        
        for (int i = multiplierIndex; i < 32; i++)  {
            if (binaryNumber[TEMP][i])  {
                return true;
            }
        }
        
        if (negativeResult)
            multByNegOne(RESULT, 0, 32);
        return false;
    }
    
    public boolean division()  {
        if (checkZero(OPERAND2, 0, 32))
            return true;
        boolean negativeResult = removeNegatives();
        multByNegOne(OPERAND2, 0, 32);
        
        do  {
            addition();
            if (!binaryNumber[RESULT][31])
                addOne(TEMP, 0, 32);
            System.arraycopy(binaryNumber[RESULT], 0, binaryNumber[OPERAND1], 0, 32);
        }   while (!binaryNumber[OPERAND1][31]);
        
        System.arraycopy(binaryNumber[TEMP], 0, binaryNumber[RESULT], 0, 32);
        if (negativeResult)
            multByNegOne(RESULT, 0, 32);
        return false;
    }
    
    protected boolean addRange(int startBit, int rangeLength)    {
        boolean carryFlag = false;
        int endBit = startBit + rangeLength - 1;
        
        for (int i = startBit; i <= endBit; i++)    {
            binaryNumber[RESULT][i] = (binaryNumber[OPERAND1][i] ^ binaryNumber[OPERAND2][i]) ^ carryFlag;
            carryFlag = carryFlag   ? (binaryNumber[OPERAND1][i] || binaryNumber[OPERAND2][i]) 
                                    : (binaryNumber[OPERAND1][i] && binaryNumber[OPERAND2][i]);
        }
        
    //  Return true if result is out of range
        if (binaryNumber[OPERAND1][endBit] == binaryNumber[OPERAND2][endBit])
            return (binaryNumber[OPERAND1][endBit] ^ binaryNumber[RESULT][endBit]);
        else return false;
    }
    
    protected void multByNegOne(byte whichNumber, int start, int length)  {
        if (binaryNumber[whichNumber][length - 1]) {
            subtractOne(whichNumber, start, length);
            complement(whichNumber, start, length);
        }
        else    {
            complement(whichNumber, start, length);
            addOne(whichNumber, start, length);
        }
    }

    protected boolean subtractOne(byte whichNumber, int start, int length)   {
        int endBit = start + length - 1;
        boolean negative = binaryNumber[whichNumber][endBit];
        while (!binaryNumber[whichNumber][start] && start <= endBit)  {
            binaryNumber[whichNumber][start] = true;
            start++;
        }
        
        if (start <= endBit)
            binaryNumber[whichNumber][start] = false;
        return (negative ? !binaryNumber[whichNumber][endBit] : false);
    }

    protected boolean addOne(byte whichNumber, int start, int length)    {
        int endBit = start + length - 1;
        boolean positive = !binaryNumber[whichNumber][endBit];
        while (binaryNumber[whichNumber][start] && start <= endBit)   {
            binaryNumber[whichNumber][start] = false;
            start++;
        }
        if (start <= endBit)
            binaryNumber[whichNumber][start] = true;
        return (positive ? binaryNumber[whichNumber][endBit] : false);
    }

    protected void complement(byte whichNumber, int start, int length)    {
        for (int i = start; i < length; i++)    {
            binaryNumber[whichNumber][i] = !binaryNumber[whichNumber][i];
        }
    }

    protected boolean removeNegatives()    {
        boolean result = binaryNumber[OPERAND1][31] ^ binaryNumber[OPERAND2][31];
        for (byte i = 0; i < 2; i++) {
            if (binaryNumber[i][31])
                multByNegOne(i, 0, 32);
        }
        return result;
    }
    
    protected boolean bitShiftDouble(byte whichNumber, int start, int length)    {
//        if (binaryNumber[whichNumber][start + length - 1])
//            return false;
        boolean signChange = (binaryNumber[whichNumber][start + length - 1] != binaryNumber[whichNumber][start + length - 2]) ?
                true : false;
        for (int i = start + length - 1; i > start; i--)    {
            binaryNumber[whichNumber][i] = binaryNumber[whichNumber][i-1];
        }
        binaryNumber[whichNumber][start] = false;
        return signChange;
    }
    
    //  Return true if the value of the range is zero
    protected boolean checkZero(byte whichNumber, int start, int length)  {
        for (int i = start + length - 1; i >= start; i--)
            if (binaryNumber[whichNumber][i])
                return false;
        return true;
    }
}

