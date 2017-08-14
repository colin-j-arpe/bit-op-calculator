package bitcalc;

//import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class CalcActions implements KeyListener, ActionListener  {
    CalcFrame gui;
    Equation thisEq;
    
    boolean noDecimal = true, newEntry = true, negative = false;
    byte operand = 0;
    String entryString = "0", resultString = "";
    String[] binaryString = new String[3];

    byte selectedOp;
    static final byte PLUS = 0;
    static final byte MINUS = 1;
    static final byte MULTIPLY = 2;
    static final byte DIVIDE = 3;

    static final long MAX_VALUE = 1073741824;

    public CalcActions (CalcFrame in)   {
        this.thisEq = new Equation();
        this.gui = in;
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

            createBinaryArray(entryString, operand);
            binaryString[operand] = createBinaryString(operand);
            if (operand == 0)
                gui.display.operand1.setText(binaryString[0]);
            else
                gui.display.operand2.setText(binaryString[1]);
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
                System.out.println("negative");
                entryString = '-' + entryString;
                System.out.println(entryString);
                gui.entryField.entryText.setText(entryString);
            }
            else    {
                operatorButton(entry);
                selectedOp = MINUS;
            }
        }

        else if (entry == '+')  {
            operatorButton(entry);
            selectedOp = PLUS;
        }

        else if (entry == '*')  {
            operatorButton(entry);
            selectedOp = MULTIPLY;
        }

        else if (entry == '/')  {
            operatorButton(entry);
            selectedOp = DIVIDE;
        }

        else if (entry == '=')  {
            createBinaryArray(entryString, (byte)1);
            binaryString[1] = createBinaryString((byte)1);
            gui.display.operand2.setText(binaryString[1]);

            switch (selectedOp) {
                case PLUS:
                    thisEq.addition();
                    break;
                case MINUS:
                    thisEq.subtraction();
                    break;
                case MULTIPLY:
                    thisEq.multiplication();
                    break;
                case DIVIDE:
                    thisEq.division();
                    break;
            }
            
            binaryString[2] = createBinaryString((byte)2);
            gui.display.binaryResult.setText(binaryString[2]);
            
            resultString = convertToDecimal(thisEq.binaryNumber[2]);
            gui.display.decimalResult.setText(resultString);
        }
        
        else if (entry == 'C' || entry == 'c') {
            thisEq = new Equation();
            entryString = "0";
            noDecimal = true;
            newEntry = true;
            negative = false;
            operand = 0;
            Arrays.fill(binaryString, "");
            for (int i = 0; i < 3; i++)
                Arrays.fill(thisEq.binaryNumber[i], false);
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
        createBinaryArray(entryString, (byte)0);
        binaryString[0] = createBinaryString((byte)0);
        String opString = "";
        opString += operator;

        gui.display.operand1.setText(binaryString[0]);
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
        long decimalResult = 0, powerOf2 = MAX_VALUE;
        
        if (input[31])
            decimalResult = MAX_VALUE * (-2);
        for (int i = 30; i >= 0; i--)   {
            if (input[i])
                decimalResult += powerOf2;
            powerOf2 /= 2;
        }
        
        return Long.toString(decimalResult);
    }

    public void createBinaryArray(String input, byte whichBinary)  {
        double inputValue = Double.parseDouble(input);
        long powerOf2 = MAX_VALUE;
        
        Arrays.fill(thisEq.binaryNumber[whichBinary], false);
        
        if (inputValue < 0) {
            thisEq.binaryNumber[whichBinary][31] = true;
            System.out.println("negative");
            inputValue += (MAX_VALUE * 2);
            System.out.println("value is now " + inputValue);
        }
        
        for (int i = 30; i >= 0; i--)    {
            if (inputValue >= powerOf2) {
                thisEq.binaryNumber[whichBinary][i] = true;
                inputValue -= powerOf2;
            }
            powerOf2 /= 2;
        }
    }

    public String createBinaryString(byte whichBinary) {
        String output = "";
        for (int i = 31; i >= 0; i--)   {
            if (thisEq.binaryNumber[whichBinary][i])
                output += "1";
            else
                output += "0";
        }
        return output;
    }
}