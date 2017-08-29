package bitcalc;

import java.util.Arrays;

public class Equation   {
    boolean[][] binaryNumber = new boolean[4][32];
    final byte OPERAND1 = 0;
    final byte OPERAND2 = 1;
    final byte RESULT = 2;
    final byte TEMP = 3;
    
    public void addition()    {
        boolean carryFlag = false;
        
        for (int i = 0; i < 32; i++)    {
            binaryNumber[RESULT][i] = (binaryNumber[OPERAND1][i] ^ binaryNumber[OPERAND2][i]);
            binaryNumber[RESULT][i] = (binaryNumber[RESULT][i] ^ carryFlag);
            if (carryFlag)
                carryFlag = (binaryNumber[OPERAND1][i] || binaryNumber[OPERAND2][i]);
            else
                carryFlag = (binaryNumber[OPERAND1][i] && binaryNumber[OPERAND2][i]);
        }
    }
    
    public void subtraction()   {
        multByNegOne(OPERAND2);
        addition();
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
                System.out.println("Result is too large");
                return false;
            }
        }
        
        if (negativeResult)
            multByNegOne(RESULT);
        return true;
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
    
    private void multByNegOne(byte whichBinary)  {
        if (binaryNumber[whichBinary][31]) {
            subtractOne(whichBinary);
            complement(whichBinary);
        }
        else    {
            complement(whichBinary);
            addOne(whichBinary);
        }
    }

    private boolean subtractOne(byte whichBinary)   {
        int i = 0;
        while (!binaryNumber[whichBinary][i] && i < 31)  {
            binaryNumber[whichBinary][i] = true;
            i++;
        }
        if (i == 31)
            return false;
        
        binaryNumber[whichBinary][i] = false;
        return true;
    }

    private void addOne(byte whichBinary)    {
        int i = 0;
        while (binaryNumber[whichBinary][i])   {
            binaryNumber[whichBinary][i] = false;
            i++;
        }
        binaryNumber[whichBinary][i] = true;
    }

    private void complement(byte whichBinary)    {
        for (int i = 0; i < 32; i++)    {
            binaryNumber[whichBinary][i] = !binaryNumber[whichBinary][i];
        }
    }

    private boolean removeNegatives()    {
        boolean result = binaryNumber[OPERAND1][31] ^ binaryNumber[OPERAND2][31];
        for (byte i = 0; i < 2; i++) {
            if (binaryNumber[i][31])
                multByNegOne(i);
        }
        return result;
    }
    
    private boolean bitShiftDouble(byte whichBinary)    {
        if (binaryNumber[whichBinary][31])
            return false;
        for (int i = 31; i > 0; i--)    {
            binaryNumber[whichBinary][i] = binaryNumber[whichBinary][i-1];
        }
        binaryNumber[whichBinary][0] = false;
        return true;
    }
}