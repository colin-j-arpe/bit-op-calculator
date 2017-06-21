package bitcalc;



import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class CalcActions implements KeyListener, ActionListener  {
    CalcFrame gui;
    boolean noDecimal = true, newEntry = true, negative = false;
    String entryString = "0", binary1 = "", binary2 = "", binary3 = "", resultString = "";
    boolean[] boolOp1 = new boolean[32];
    boolean[] boolOp2 = new boolean[32];
    boolean[] boolResult = new boolean[32];

    byte selectedOp;
    static final byte PLUS = 0;
    static final byte MINUS = 1;
    static final byte MULTIPLY = 2;
    static final byte DIVIDE = 3;
    static final int MAX_VALUE = 1073741824;

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
            binary2 = convertToBinary(entryString, boolOp2);
            System.out.println("MAde it here");
            gui.display.operand2.setText(binary2);
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
        else if (entry == 'C') {
            entryString = "0";
            noDecimal = true;
            newEntry = true;
            negative = false;
            binary1 = "";
            binary2 = "";
            Arrays.fill(boolOp1, false);
            Arrays.fill(boolOp2, false);
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
        gui.operators.minus.setEnabled(false);
        gui.operators.multiply.setEnabled(false);
        gui.operators.divide.setEnabled(false);
        gui.operators.equals.setEnabled(true);

        String opString = "";
        opString += operator;
        binary1 = convertToBinary(entryString, boolOp1);
        gui.display.operand1.setText(binary1);
        gui.display.operator.setText(opString);

        entryString = "0";
        noDecimal = true;
        newEntry = true;
        negative = false;
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
    
    public void showResults()   {
        String binaryResult = "";
        int decimalResult = 0, powerOf2 = MAX_VALUE;
        for (int i = 31; i >= 0; i--)   {
            if (boolResult[i])
                binaryResult += '1';
            else
                binaryResult += '0';
        }
        gui.display.binaryResult.setText(binaryResult);
        
        for (int i = 30; i >= 0; i--)   {
            if (boolResult[i])
                decimalResult += powerOf2;
            powerOf2 /= 2;
        }
        gui.display.decimalResult.setText(Integer.toString(decimalResult));
    }
    
    public String convertToBinary(String input, boolean[] boolOp) {
        String output = "";
        float inputValue = Float.parseFloat(input);
        int powerOf2 = MAX_VALUE;
        if (inputValue < 0) {
            boolOp[31] = true;
            output += '1';
            System.out.println("negative");
            inputValue *= (-1);
        }   else    output += '0';
        for (int i = 30; i >= 0; i--)    {
            if (inputValue >= powerOf2) {
                output += '1';
                boolOp[i] = true;
                inputValue -= powerOf2;
            }
            else
                output += '0';
            powerOf2 /= 2;
        }
        return output;
    }
    
}