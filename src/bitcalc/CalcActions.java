package bitcalc;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class CalcActions implements KeyListener, ActionListener  {
    CalcFrame gui;
    boolean noDecimal = true, newEntry = true, negative = false, secondOperand = false;
    String entryString = "0", resultString = "";
    boolean[] boolOp1 = new boolean[32];
    boolean[] boolOp2 = new boolean[32];
    boolean[] boolResult = new boolean[32];

    byte selectedOp;
    static final byte PLUS = 0;
    static final byte MINUS = 1;
    static final byte MULTIPLY = 2;
    static final byte DIVIDE = 3;
    static final long MAX_VALUE = 4194304;
    static final int MAX_EXP = 64;

    public CalcActions (CalcFrame in)   {
        gui = in;
    }
    
    public void keyTyped(KeyEvent input)    {
        char key = input.getKeyChar();
        buttonFunctions(key);
    }
    
    public void keyPressed(KeyEvent input)  {
        //  ignore
    }
    
    public void keyReleased(KeyEvent input) {
        //  ignore
    }
    
    public void actionPerformed(ActionEvent event)  {
        String buttonLabel = event.getActionCommand();
        System.out.println("Triggered button " + buttonLabel);
        char[] button = buttonLabel.toCharArray() ;
        buttonFunctions(button[0]);
    }
    
    public void buttonFunctions(char entry)    {
        if (entry >= '0' && entry <= '9')   {
            if (newEntry)   {
                entryString = "";
                if (negative) entryString = "-";
                newEntry = false;
            }
            entryString += entry;
            gui.entryField.entryText.setText(entryString);
        }
        else if (entry == '.' && noDecimal)   {
            if (entryString == "-")
                entryString += "0";
            entryString += entry;
            noDecimal = false;
            newEntry = false;
            gui.entryField.entryText.setText(entryString);
        }
        else if (entry == '-')  {
            if (newEntry)   {
                negative = true;
                newEntry = false;
                if (secondOperand)
                    gui.operators.minus.setEnabled(false);
                System.out.println("negative");
                entryString = "-";
                System.out.println(entryString);
                gui.entryField.entryText.setText(entryString);
            }
            else    {
                operatorButton(entry);
//                gui.display.operator.setText("-");
                selectedOp = MINUS;
            }
        }
        else if (entry == '+')  {
            operatorButton(entry);
//            gui.display.operator.setText("+");
            selectedOp = PLUS;
        }
        else if (entry == '*')  {
            operatorButton(entry);
//                gui.display.operator.setText("-");
            selectedOp = MULTIPLY;
        }
        else if (entry == '/')  {
            operatorButton(entry);
            selectedOp = DIVIDE;
        }
        else if (entry == '=')  {
            convertToFloatBinary(entryString, boolOp2);
            gui.display.operand2.setText(binaryOutput(boolOp2));
            System.out.println("MAde it here");
            switch (selectedOp) {
                case PLUS:
                    addition();
                    break;
                case MINUS:
                    subtraction();
                    break;
                case MULTIPLY:
                    multiplication();
                    break;
                case DIVIDE:
                    division();
                    break;
            }
//            gui.display.decimalResult.setText(resultString);
        }
        else if (entry == 'C' || entry == 'c') {
            entryString = "0";
            noDecimal = true;
            newEntry = true;
            negative = false;
            Arrays.fill(boolOp1, false);
            Arrays.fill(boolOp2, false);
            Arrays.fill(boolResult, false);
            gui.entryField.entryText.setText("0");
            gui.display.operand1.setText("");
            gui.display.operator.setText("");
            gui.display.operand2.setText("");
            gui.display.binaryResult.setText("");
            gui.display.decimalResult.setText("");
            gui.operators.plus.setEnabled(true);
            gui.operators.minus.setEnabled(true);
            gui.operators.multiply.setEnabled(true);
            gui.operators.divide.setEnabled(true);
            gui.operators.equals.setEnabled(false);
        }
        
    }
    
    public void operatorButton(char operator)    {
        gui.operators.plus.setEnabled(false);
        gui.operators.multiply.setEnabled(false);
        gui.operators.divide.setEnabled(false);
        gui.operators.equals.setEnabled(true);

//        String opString = "";
//        opString += operator;
        convertToFloatBinary(entryString, boolOp1);
        gui.display.operand1.setText(binaryOutput(boolOp1));
        gui.display.operator.setText("" + operator);

        entryString = "0";
        noDecimal = true;
        newEntry = true;
        negative = false;
        secondOperand = true;
        gui.entryField.entryText.setText(entryString);
    }
    
    public void addition()    {
        boolean firstExponentIsGreater = true, addFlag, carryFlag = false;
        int exponentDifference = 0, powerOf2;
        
        if (boolOp1[30] ^ boolOp2[30])  {
            if (boolOp1[30])
                firstExponentIsGreater = false;
        }
        else    {
            for (int i = 29; i > 22; i--)   {
                if (boolOp1[i] ^ boolOp2[i])  {
                    if (boolOp2[i])
                        firstExponentIsGreater = false;
                    break;
                }
            }
        }
        for (int i = 30; i > 22; i--)   {
            if (firstExponentIsGreater)
                boolResult[i] = boolOp1[i];
            else
                boolResult[i] = boolOp2[i];
        }
        if (firstExponentIsGreater)
            multByNegOne(boolOp2, 30, 8);
        else
            multByNegOne(boolOp1, 30, 8);
       
       powerOf2 = 1;
       for (int i = 23; i < 31; i++)    {
            addFlag = (boolOp1[i] ^ boolOp2[i]);
            addFlag = (addFlag ^ carryFlag);
            if (addFlag)
                exponentDifference += powerOf2;
            powerOf2 *= 2;
            if (carryFlag)
                carryFlag = (boolOp1[i] || boolOp2[i]);
            else
                carryFlag = (boolOp1[i] && boolOp2[i]);
        }
        
        if (firstExponentIsGreater)
            shiftMantissa(boolOp2, exponentDifference);
        else
            shiftMantissa(boolOp1, exponentDifference);
        
        if (boolOp1[31])
            multByNegOne(boolOp1, 22, 23);
        if (boolOp2[31])
            multByNegOne(boolOp2, 22, 23);
        
        addMantissae();
        gui.display.binaryResult.setText(binaryOutput(boolResult));
    }
    
    public void shiftMantissa (boolean[] boolArray, int distance)   {
        for (int i = 0; i < distance; i++)  {
            for (int j = 0; j < 22; j++)
                boolArray[j] = boolArray[j + 1];
            boolArray[22] = false;
        }
    }
    
    public void addMantissae()  {
        boolean carryFlag = false;
        for (int i = 0; i < 23; i++)    {
            boolResult[i] = (boolOp1[i] ^ boolOp2[i]);
            boolResult[i] = (boolResult[i] ^ carryFlag);
        }
        if (carryFlag)
            carryFlag = (boolOp1[i] || boolOp2[i]);
        else
            carryFlag = (boolOp1[i] && boolOp2[i]);
    }
    
    public void subtraction()   {
        multByNegOne();
        addition();
    }
    
    public void showResults()   {
        String binaryResult = "";
        long decimalResult = 0, powerOf2 = MAX_VALUE;
        for (int i = 31; i >= 0; i--)   {
            if (boolResult[i])
                binaryResult += '1';
            else
                binaryResult += '0';
        }
        gui.display.binaryResult.setText(binaryResult);
        
        if (boolResult[31])
            decimalResult = MAX_VALUE * (-2);
        for (int i = 30; i >= 0; i--)   {
            if (boolResult[i])
                decimalResult += powerOf2;
            powerOf2 /= 2;
        }
        gui.display.decimalResult.setText(Long.toString(decimalResult));
    }
    
    public String binaryOutput (boolean[] boolArray)    {
        String output = "s:";
        for (int i = 31; i >= 0; i--)   {
            if (boolArray[i])
                output += '1';
            else
                output += '0';
        }
        output = output.substring(0, 3) + " e:" +
                 output.substring(3, 7) + " " +
                 output.substring(7, 11) + " m:" +
                 output.substring(11, 14) + " " +
                 output.substring(14);
        for (int i = 22; i < output.length(); i += 5)    {
            output = output.substring(0, i) + " " +
                     output.substring(i);
        }
        return output;
    }
    
    public void convertToFloatBinary(String input, boolean[] boolOp) {
        int mantissaCount = 22, exponentCount = -1;
        double inputFraction, inputValue = Double.parseDouble(input), powerOfHalf = 0.5;
        long inputWhole, powerOf2 = MAX_VALUE;
        if (inputValue < 0) {
            boolOp[31] = true;
            System.out.println("negative");
            inputValue *= (-1);
            System.out.println("value is now " + inputValue);
        }
        inputWhole = (long) inputValue;
        inputFraction = inputValue % 1;
        
        if (inputWhole > 0) {
            while (inputWhole < powerOf2)   {
                powerOf2 /= 2;
            }

            while (powerOf2 >= 1)    {
                if (inputWhole >= powerOf2) {
                    boolOp[mantissaCount] = true;
                    inputWhole -= powerOf2;
                }
                powerOf2 /= 2;
                mantissaCount--;
                exponentCount++;
            }
        }
        if (inputFraction > 0)  {
            while (inputFraction < powerOfHalf) {
                powerOfHalf /= 2;
                if (exponentCount < 1)
                    exponentCount--;
                else
                    mantissaCount--;
            }
            
            while (mantissaCount >= 0)  {
                if (inputFraction >= powerOfHalf)   {
                    boolOp[mantissaCount] = true;
                    inputFraction -= powerOfHalf;
                }
                powerOfHalf /= 2;
                mantissaCount--;
            }
        }
        System.out.println("exponentCount is " + exponentCount);
        if (exponentCount < 0)  {
            boolOp[30] = true;
            exponentCount += (MAX_EXP * 2);
        }
        powerOf2 = MAX_EXP;
        for (int i = 29; i > 22; i--)   {
            if (exponentCount >= powerOf2)  {
                boolOp[i] = true;
                exponentCount -= powerOf2;
            }
            powerOf2 /= 2;
        }
    }
    
    public void multByNegOne(boolean[] boolArray, int start, int length)  {
//        boolean[] boolArray = boolOp;
        if (boolArray[start]) {
            while (!boolArray[start - length])  {
                boolArray[start - length] = true;
                length--;
            }
            boolArray[start - length] = false;
            complement(boolArray, start, length);
        }
        else    {
            complement(boolArray, start, length);
            while (boolArray[start - length])   {
                boolArray[start - length] = false;
                length--;
            }
            boolArray[start - length] = true;
        }
        
    }

    public void complement(boolean[] boolArray, int start, int length)    {
        for (int i = start; i > start - length; i--)    {
            boolArray[i] = !boolArray[i];
        }
    }
}