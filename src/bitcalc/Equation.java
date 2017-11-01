package bitcalc;

import java.util.Arrays;

public class Equation   {
    boolean[][] binaryNumber = new boolean[4][32];
    final byte OPERAND1 = 0;
    final byte OPERAND2 = 1;
    final byte RESULT = 2;
    final byte TEMP = 3;
    
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
        }   while (bitShiftDouble(OPERAND1));
        
        for (int i = multiplierIndex; i < 32; i++)  {
            if (binaryNumber[TEMP][i])  {
                return true;
            }
        }
        
        if (negativeResult)
            multByNegOne(RESULT);
        return false;
    }
    
    public void division()  {
        boolean negativeResult = removeNegatives();
        multByNegOne(OPERAND2);
        
        do  {
            addition();
            if (!binaryNumber[RESULT][31])
                addOne(TEMP);
            System.arraycopy(binaryNumber[RESULT], 0, binaryNumber[OPERAND1], 0, 32);
        }   while (!binaryNumber[OPERAND1][31]);
        
        System.arraycopy(binaryNumber[TEMP], 0, binaryNumber[RESULT], 0, 32);
        if (negativeResult)
            multByNegOne(RESULT);
    }
    
    protected boolean addRange(int startBit, int rangeLength)    {
        boolean carryFlag = false;
        int endBit = startBit + rangeLength - 1;
        
        for (int i = startBit; i < endBit; i++)    {
            binaryNumber[RESULT][i] = (binaryNumber[OPERAND1][i] ^ binaryNumber[OPERAND2][i]);
            binaryNumber[RESULT][i] = (binaryNumber[RESULT][i] ^ carryFlag);
            if (carryFlag)
                carryFlag = (binaryNumber[OPERAND1][i] || binaryNumber[OPERAND2][i]);
            else
                carryFlag = (binaryNumber[OPERAND1][i] && binaryNumber[OPERAND2][i]);
        }

        if (binaryNumber[OPERAND1][endBit] == binaryNumber[OPERAND2][endBit])   {
            if (binaryNumber[OPERAND1][endBit]) {
                return !binaryNumber[RESULT][endBit];
            }   else    {
                return binaryNumber[RESULT][endBit];
            }
        }   else    {
            return false;
        }
    }
    
    protected void multByNegOne(byte whichNumber, byte start, byte length)  {
        if (binaryNumber[whichNumber][length - 1]) {
            subtractOne(whichNumber, start, length);
            complement(whichNumber, start, length);
        }
        else    {
            complement(whichNumber, start, length);
            addOne(whichNumber, start);
        }
    }

    protected boolean subtractOne(byte whichNumber, byte start, byte length)   {
//        int i = start;
        while (!binaryNumber[whichNumber][start] && start < length - 1)  {
            binaryNumber[whichNumber][start] = true;
            start++;
        }
        if (start == length - 1)
            return false;
        
        binaryNumber[whichNumber][start] = false;
        return true;
    }

    protected void addOne(byte whichNumber, byte start)    {
        byte i = start;
        while (binaryNumber[whichNumber][i])   {
            binaryNumber[whichNumber][i] = false;
            i++;
        }
        binaryNumber[whichNumber][i] = true;
    }

    protected void complement(byte whichNumber, byte start, byte length)    {
        for (int i = start; i < length; i++)    {
            binaryNumber[whichNumber][i] = !binaryNumber[whichNumber][i];
        }
    }

    protected boolean removeNegatives()    {
        boolean result = binaryNumber[OPERAND1][31] ^ binaryNumber[OPERAND2][31];
        for (byte i = 0; i < 2; i++) {
            if (binaryNumber[i][31])
                multByNegOne(i);
        }
        return result;
    }
    
    protected boolean bitShiftDouble(byte whichNumber)    {
        if (binaryNumber[whichNumber][31])
            return false;
        for (int i = 31; i > 0; i--)    {
            binaryNumber[whichNumber][i] = binaryNumber[whichNumber][i-1];
        }
        binaryNumber[whichNumber][0] = false;
        return true;
    }
}