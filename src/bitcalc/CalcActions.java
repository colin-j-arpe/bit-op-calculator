package bitcalc;

//import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class CalcActions implements KeyListener, ActionListener  {
    CalcFrame gui;
    Equation thisEq;
    
    boolean noDecimal = true, newEntry = true, negative = false, outOfRange = false, isFloat = false;
    byte operand = 0;
    String entryString = "0";
    String opString = " ";
    String[] binaryString = new String[3];
    String[] decimalString = new String[3];

    byte selectedOp;
    static final byte PLUS = 0;
    static final byte MINUS = 1;
    static final byte MULTIPLY = 2;
    static final byte DIVIDE = 3;
    static final long TWO_TO_THE_30TH = 1073741824;

    public CalcActions (CalcFrame in)   {
        this.thisEq = new Equation();
        this.gui = in;
    }
    
    public void keyTyped(KeyEvent input)    {
        char key = input.getKeyChar();
        if (input.getKeyCode() == KeyEvent.VK_ENTER)
            key = '=';
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

            createBinaryArray(entryString, operand);
            binaryString[operand] = createBinaryString(operand);
            if (operand == 0)
                gui.display.binaryOperand1.setText(binaryString[0]);
            else
                gui.display.binaryOperand2.setText(binaryString[1]);
        }

        else if (entry == '.' && noDecimal)   {
            entryString += entry;
            noDecimal = false;
            newEntry = false;
            gui.entryField.entryText.setText(entryString);
        }

        else if (entry == '-')  {
            if (newEntry && !negative)   {
                negative = true;
//                newEntry = false;
                if (operand == 1)
                    gui.operators.minus.setEnabled(false);
                entryString = '-' + entryString;
                gui.entryField.entryText.setText(entryString);
            }
            else    {
                if (operand > 0) return;
                operatorButton(entry);
                selectedOp = MINUS;
            }
        }

        else if (entry == '+')  {
            if (operand > 0) return;
            operatorButton(entry);
            selectedOp = PLUS;
        }

        else if (entry == '*')  {
            if (operand > 0) return;
            operatorButton(entry);
            selectedOp = MULTIPLY;
        }

        else if (entry == '/')  {
            if (operand > 0) return;
            operatorButton(entry);
            selectedOp = DIVIDE;
        }

        else if (entry == '=')  {
            if (operand < 1) return;
            createBinaryArray(entryString, (byte)1);
            binaryString[1] = createBinaryString((byte)1);
            decimalString[1] = convertToDecimal(thisEq.binaryNumber[1]);
           
            gui.display.binaryOperand2.setText(binaryString[1]);
            gui.display.decimalOperand2.setText(decimalString[1]);

            switch (selectedOp) {
                case PLUS:
                    outOfRange = thisEq.addition();
                    break;
                case MINUS:
                    outOfRange = thisEq.subtraction();
                    break;
                case MULTIPLY:
                    outOfRange = thisEq.multiplication();
                    break;
                case DIVIDE:
                    thisEq.division();
                    break;
            }
            
            binaryString[2] = createBinaryString((byte)2);
            gui.display.binaryResult.setText(binaryString[2]);
            
            if (outOfRange) {
                gui.display.decimalResult.setText("ERROR: result out of range");
            }   else    {
                decimalString[2] = convertToDecimal(thisEq.binaryNumber[2]);
                gui.display.decimalResult.setText(decimalString[2]);
            }
        }
        
        else if (entry == 'C' || entry == 'c') {
            reset();
        }
        
    }
    
    public void reset() {
        thisEq = isFloat ? new FloatEquation() : new Equation();
        entryString = "0";
        noDecimal = true;
        newEntry = true;
        negative = false;
        outOfRange = false;
        operand = 0;
        opString = " ";
        Arrays.fill(binaryString, "");
        for (int i = 0; i < 3; i++)
            Arrays.fill(thisEq.binaryNumber[i], false);
        gui.entryField.entryText.setText("0");
        gui.display.binaryOperand1.setText("");
        gui.display.decimalOperand1.setText("");
        gui.display.operator.setText(opString);
        gui.display.binaryOperand2.setText("");
        gui.display.decimalOperand2.setText("");
        gui.display.binaryResult.setText("");
        gui.display.decimalResult.setText("");
        gui.operators.plus.setEnabled(true);
        gui.operators.minus.setEnabled(true);
        gui.operators.multiply.setEnabled(true);
        gui.operators.divide.setEnabled(true);
        gui.operators.equals.setEnabled(false);
    }
    
    public void operatorButton(char operator)    {
        createBinaryArray(entryString, (byte)0);
        binaryString[0] = createBinaryString((byte)0);
        decimalString[0] = convertToDecimal(thisEq.binaryNumber[0]);
        opString += operator;

        gui.display.binaryOperand1.setText(binaryString[0]);
        gui.display.decimalOperand1.setText(decimalString[0]);
        gui.display.operator.setText(opString);

        gui.operators.plus.setEnabled(false);
        gui.operators.multiply.setEnabled(false);
        gui.operators.divide.setEnabled(false);
        gui.operators.equals.setEnabled(true);

        noDecimal = true;
        newEntry = true;
        negative = false;
        operand = 1;
        entryString = "0";
        gui.entryField.entryText.setText(entryString);
    }
    
    public String convertToDecimal(boolean[] input) {
        long decimalResult = 0, powerOf2 = TWO_TO_THE_30TH;
        
        if (input[31])
            decimalResult = TWO_TO_THE_30TH * (-2);
        for (int i = 30; i >= 0; i--)   {
            if (input[i])
                decimalResult += powerOf2;
            powerOf2 /= 2;
        }
        
        return Long.toString(decimalResult);
    }

    public void createBinaryArray(String input, byte whichNumber)  {
        double inputValue = Double.parseDouble(input);
        if (!noDecimal) {
            createFloatArray(inputValue, whichNumber);
            return;
        }
        
        long powerOf2 = TWO_TO_THE_30TH;
        
        Arrays.fill(thisEq.binaryNumber[whichNumber], false);
        
        if (inputValue < 0) {
            thisEq.binaryNumber[whichNumber][31] = true;
            inputValue += (TWO_TO_THE_30TH * 2);
        }
        
        for (int i = 30; i >= 0; i--)    {
            if (inputValue >= powerOf2) {
                thisEq.binaryNumber[whichNumber][i] = true;
                inputValue -= powerOf2;
            }
            powerOf2 /= 2;
        }
    }
    
    public void createFloatArray(double value, byte whichNumber)    {
        double powerOf2 = 1;
        
        if (value == 0)  {
            Arrays.fill(thisEq.binaryNumber[whichNumber], false);
            return;
        }
        
        if (value < 0)  {
            thisEq.binaryNumber[whichNumber][31] = true;
            value *= (-1);
        }
        
        Arrays.fill(thisEq.binaryNumber[whichNumber], 25, 31, true);
        
        while (powerOf2 < value)    {
            powerOf2 *= 2;
            thisEq.addOne(whichNumber, 25, 6);
        }
        
        for (int i = 24; i >= 0; i--)  {
            if (value > powerOf2)   {
                thisEq.binaryNumber[whichNumber][i] = true;
                value -= powerOf2;
            }
            powerOf2 /= 2;
            if (value == 0) break;
        }
    }

    public String createBinaryString(byte whichBinary) {
        String output = "";
        for (int i = 31; i >= 0; i--)   {
            if (thisEq.binaryNumber[whichBinary][i])
                output += "1";
            else
                output += "0";
            if (i % 4 == 0)
                output += " ";
        }
        return output;
    }
}