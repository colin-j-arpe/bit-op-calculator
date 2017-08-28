package bitcalc;

import java.util.Arrays;

public class Equation   {
    boolean[][] binaryNumber = new boolean[3][32];
    
    public void addition()    {
        boolean carryFlag = false;
        
        for (int i = 0; i < 32; i++)    {
            binaryNumber[2][i] = (binaryNumber[0][i] ^ binaryNumber[1][i]);
            binaryNumber[2][i] = (binaryNumber[2][i] ^ carryFlag);
            if (carryFlag)
                carryFlag = (binaryNumber[0][i] || binaryNumber[1][i]);
            else
                carryFlag = (binaryNumber[0][i] && binaryNumber[1][i]);
        }
    }
    
    public void subtraction()   {
        multByNegOne((byte)1);
        addition();
    }
    
    public boolean multiplication()    {
        boolean negativeResult = removeNegatives();
        int multiplierIndex = 0;
        
        boolean[] multiplier = new boolean[32];
        System.arraycopy(binaryNumber[1], 0, multiplier, 0, 32);
        Arrays.fill(binaryNumber[1], false);
        
        do {
            if (multiplier[multiplierIndex])    {
                addition();
                System.arraycopy(binaryNumber[2], 0, binaryNumber[1], 0, 32);
            }
            multiplierIndex++;
        } while (bitShiftDouble());
        
        for (int i = multiplierIndex; i < 32; i++)  {
            if (multiplier[i])  {
                System.out.println("Result is too large");
                return false;
            }
        }
        
        if (negativeResult)
            multByNegOne((byte)2);
        return true;
    }
    
    public void division()  {
        boolean negativeResult = removeNegatives();
        multByNegOne((byte)1);
        
        boolean[] resultCounter = new boolean[32];
        
        do  {
            addition();
            if (!binaryNumber[2][31])
                addOne(resultCounter);
            System.arraycopy(binaryNumber[2], 0, binaryNumber[0], 0, 32);
        }   while (!binaryNumber[0][31]);
        
        System.arraycopy(resultCounter, 0, binaryNumber[2], 0, 32);
        if (negativeResult)
            multByNegOne((byte)2);
    }
    
    public void multByNegOne(byte whichBinary)  {
        if (binaryNumber[whichBinary][31]) {
            subtractOne(binaryNumber[whichBinary]);
            complement(whichBinary);
        }
        else    {
            complement(whichBinary);
            addOne(binaryNumber[whichBinary]);
        }
    }

    public boolean subtractOne(boolean[] thisBinary)   {
        int i = 0;
        while (!thisBinary[i] && i < 31)  {
            thisBinary[i] = true;
            i++;
        }
        if (i == 31)
            return false;
        
        thisBinary[i] = false;
        return true;
    }

    public void addOne(boolean[] thisBinary)    {
        int i = 0;
        while (thisBinary[i])   {
            thisBinary[i] = false;
            i++;
        }
        thisBinary[i] = true;
    }

    public void complement(byte whichBinary)    {
        for (int i = 0; i < 32; i++)    {
            binaryNumber[whichBinary][i] = !binaryNumber[whichBinary][i];
        }
    }

    public boolean removeNegatives()    {
        boolean result = binaryNumber[0][31] ^ binaryNumber[1][31];
        for (byte i = 0; i < 2; i++) {
            if (binaryNumber[i][31])
                multByNegOne(i);
        }
        return result;
    }
    
    private boolean bitShiftDouble()    {
        if (binaryNumber[0][31])
            return false;
        for (int i = 31; i > 0; i--)    {
            binaryNumber[0][i] = binaryNumber[0][i-1];
        }
        binaryNumber[0][0] = false;
        return true;
    }
}