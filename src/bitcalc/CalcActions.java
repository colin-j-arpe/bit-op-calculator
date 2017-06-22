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
                entryString = '-' + entryString;
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
            convertToBinary(entryString, boolOp2);
            System.out.println("MAde it here");
            gui.display.operand2.setText(binaryOutput(boolOp2));
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
        boolean carryFlag = false;
        
        for (int i = 0; i < 32; i++)    {
            boolResult[i] = (boolOp1[i] ^ boolOp2[i]);
            boolResult[i] = (boolResult[i] ^ carryFlag);
            if (carryFlag)
                carryFlag = (boolOp1[i] || boolOp2[i]);
            else
                carryFlag = (boolOp1[i] && boolOp2[i]);
        }
        showResults();
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
        int mantissaCount = 22, exponentCount = 22;
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
                exponentCount--;
            }

            while (powerOf2 > 1)    {
                if (inputWhole >= powerOf2) {
                    boolOp[mantissaCount] = true;
                    inputWhole -= powerOf2;
                }
                powerOf2 /= 2;
                mantissaCount--;
            }
        }
        if (inputFraction > 0)  {
            while (inputFraction < powerOfHalf) {
                powerOfHalf /= 2;
                if (exponentCount < 1)
                    exponentCount--;
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
    }
    
    public void multByNegOne()  {
//        boolean[] boolArray = boolOp;
        if (boolOp2[31]) {
            subtractOne();
            complement();
        }
        else    {
            complement();
            addOne();
        }
        
    }

    public void complement()    {
        for (int i = 0; i < 32; i++)    {
            boolOp2[i] = !boolOp2[i];
        }
    }

    public void subtractOne()   {
        int i = 0;
        while (!boolOp2[i])  {
            boolOp2[i] = true;
            i++;
        }
        boolOp2[i] = false;
    }

    public void addOne()    {
        int i = 0;
        while (boolOp2[i])   {
            boolOp2[i] = false;
            i++;
        }
        boolOp2[i] = true;
    }
}