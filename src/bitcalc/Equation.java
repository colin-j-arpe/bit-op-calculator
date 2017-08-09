package bitcalc;

public class Equation   {
    boolean[][] binaryNumber = new boolean[3][32];
    
    public void addition(byte operandA, byte operandB, boolean resultHolder[])    {
        boolean carryFlag = false;
        
        for (int i = 0; i < 32; i++)    {
            resultHolder[i] = (binaryNumber[operandA][i] ^ binaryNumber[operandB][i]);
            resultHolder[i] = (resultHolder[i] ^ carryFlag);
            if (carryFlag)
                carryFlag = (binaryNumber[operandA][i] || binaryNumber[operandB][i]);
            else
                carryFlag = (binaryNumber[operandA][i] && binaryNumber[operandB][i]);
        }
    }
    
    public void subtraction()   {
        multByNegOne((byte)1);
        addition((byte)0, (byte)1, binaryNumber[2]);
    }
    
    public void multiplication()    {
        boolean negativeResult = removeNegatives();
        
        boolean[] multiplier = new boolean[32];
        System.arraycopy(binaryNumber[1], 0, multiplier, 0, 32);
        System.arraycopy(binaryNumber[0], 0, binaryNumber[1], 0, 32);
        
        while (subtractOne(multiplier))    {
            addition((byte)0, (byte)1, binaryNumber[2]);
            System.arraycopy(binaryNumber[2], 0, binaryNumber[1], 0, 32);
        }
        if (negativeResult)
            multByNegOne((byte)2);
    }
    
    public void division()  {
        boolean negativeResult = removeNegatives();
        multByNegOne((byte)1);
        
        boolean[] resultCounter = new boolean[32];
        
        do  {
            addition((byte)0, (byte)1, binaryNumber[2]);
            if (!binaryNumber[2][31])
                addOne(resultCounter);
            System.arraycopy(binaryNumber[2], 0, binaryNumber[0], 0, 32);
        }   while (!binaryNumber[0][31]);
        
        System.arraycopy(resultCounter, 0, binaryNumber[2], 0, 32);
        return;
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
        else    {
            thisBinary[i] = false;
            return true;
        }
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

    public boolean removeNegatives()
        boolean result = binaryNumber[0][31] ^ binaryNumber[1][31];
        for (byte i = 0; i < 2; i++) {
            if (binaryNumber[i][31])
                multByNegOne(i);
        }
        return result;
}