package bitcalc;

//import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import static java.lang.Math.abs;

public class CalcActions implements KeyListener, ActionListener  {
    CalcFrame gui;
    Equation thisEq;
    
    boolean noDecimal = true, newEntry = true, negative = false, outOfRange = false, isFloat = false;
    byte operand = Equation.OPERAND1;
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
                if (operand > 0) gui.operators.minus.setEnabled(false);
                newEntry = false;
            }
            entryString += entry;
            gui.entryField.entryText.setText(entryString);

            createBinaryArray(entryString, operand);
            binaryString[operand] = (outOfRange ? "ERROR: out of range" : createBinaryString(operand));
            if (operand == 0)
                gui.display.binaryOperand1.setText(binaryString[operand]);
            else
                gui.display.binaryOperand2.setText(binaryString[operand]);
        }

        else if (entry == '.' && noDecimal && isFloat)   {
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
                if (operand == 1) return;
                operatorButton(entry);
                selectedOp = MINUS;
            }
        }

        else if (entry == '+')  {
            if (operand == 1) return;
            operatorButton(entry);
            selectedOp = PLUS;
        }

        else if (entry == '*')  {
            if (operand == 1) return;
            operatorButton(entry);
            selectedOp = MULTIPLY;
        }

        else if (entry == '/')  {
            if (operand == 1) return;
            operatorButton(entry);
            selectedOp = DIVIDE;
        }

        else if (entry == '=')  {
            if (operand < Equation.OPERAND2) return;
            createBinaryArray(entryString, Equation.OPERAND2);
            binaryString[Equation.OPERAND2] = createBinaryString(Equation.OPERAND2);
            decimalString[Equation.OPERAND2] = convertToInteger(Equation.OPERAND2);
           
            gui.display.binaryOperand2.setText(binaryString[Equation.OPERAND2]);
            gui.display.decimalOperand2.setText(decimalString[Equation.OPERAND2]);

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
                    outOfRange = thisEq.division();
                    break;
            }
            
            binaryString[Equation.RESULT] = createBinaryString(Equation.RESULT);
            gui.display.binaryResult.setText(binaryString[Equation.RESULT]);
            
            if (outOfRange) {
                gui.display.decimalResult.setText("ERROR: result out of range");
            }   else    {
                decimalString[Equation.RESULT] = convertToInteger(thisEq.RESULT);
                gui.display.decimalResult.setText(decimalString[Equation.RESULT]);
            }

            operand = Equation.RESULT;
            gui.operators.plus.setEnabled(true);
            gui.operators.minus.setEnabled(true);
            gui.operators.multiply.setEnabled(true);
            gui.operators.divide.setEnabled(true);
            gui.operators.equals.setEnabled(false);
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
        operand = Equation.OPERAND1;
        opString = " ";
        Arrays.fill(binaryString, "");
        for (int i = 0; i < 4; i++)
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
        if (operand == Equation.RESULT) chainEquation();
        else    {
            createBinaryArray(entryString, Equation.OPERAND1);
            binaryString[Equation.OPERAND1] = createBinaryString(Equation.OPERAND1);
            decimalString[Equation.OPERAND1] = convertToInteger(thisEq.OPERAND1);
        }

        gui.display.binaryOperand1.setText(binaryString[Equation.OPERAND1]);
        gui.display.decimalOperand1.setText(decimalString[Equation.OPERAND1]);
        opString += operator;
        gui.display.operator.setText(opString);

        gui.operators.plus.setEnabled(false);
        gui.operators.multiply.setEnabled(false);
        gui.operators.divide.setEnabled(false);
        gui.operators.equals.setEnabled(true);

        noDecimal = true;
        newEntry = true;
        negative = false;
        operand = Equation.OPERAND2;
        entryString = "0";
        gui.entryField.entryText.setText(entryString);
    }
    
    public void chainEquation() {
        System.arraycopy(thisEq.binaryNumber[Equation.RESULT], 0, thisEq.binaryNumber[Equation.OPERAND1], 0, 32);
        binaryString[Equation.OPERAND1] = binaryString[Equation.RESULT];
        decimalString[Equation.OPERAND1] = decimalString[Equation.RESULT];
        for (int i = Equation.OPERAND2; i < thisEq.binaryNumber.length; i++)
            Arrays.fill(thisEq.binaryNumber[i], false);
        for (int i = Equation.OPERAND2; i < binaryString.length; i++) {
            binaryString[i] = "";
            decimalString[i] = "";
        }
        outOfRange = false;
        opString = " ";

        gui.display.binaryOperand2.setText("");
        gui.display.decimalOperand2.setText("");
        gui.display.binaryResult.setText("");
        gui.display.decimalResult.setText("");
    }
    
    public String convertToInteger(byte whichNumber) {
        if (isFloat)
            return convertToFloat(whichNumber);
        long integerResult = 0, powerOf2 = TWO_TO_THE_30TH;
        
        if (thisEq.binaryNumber[whichNumber][31])
            integerResult = TWO_TO_THE_30TH * (-2);
        for (int i = 30; i >= 0; i--)   {
            if (thisEq.binaryNumber[whichNumber][i])
                integerResult += powerOf2;
            powerOf2 /= 2;
        }
        
        return Long.toString(integerResult);
    }
    
    public String convertToFloat(byte whichNumber)  {
        double floatResult = 0, powerOf2 = 1;
        System.arraycopy(thisEq.binaryNumber[whichNumber], FloatEquation.EXP_START, thisEq.binaryNumber[Equation.TEMP], FloatEquation.EXP_START, FloatEquation.EXP_LENGTH);
        
        if (thisEq.binaryNumber[Equation.TEMP][30])   {
            while (!thisEq.checkZero(Equation.TEMP, FloatEquation.EXP_START, FloatEquation.EXP_LENGTH))   {
                thisEq.addOne(Equation.TEMP, FloatEquation.EXP_START, FloatEquation.EXP_LENGTH);
                powerOf2 /= 2;
            }
        }   else    {
            while (!thisEq.checkZero(Equation.TEMP, FloatEquation.EXP_START, FloatEquation.EXP_LENGTH))   {
                thisEq.subtractOne(Equation.TEMP, FloatEquation.EXP_START, FloatEquation.EXP_LENGTH);
                powerOf2 *= 2;
            }
        }
        
        for (int i = FloatEquation.MANT_LENGTH - 1; i >= 0; i--)    {
            if (thisEq.binaryNumber[whichNumber][i])
                floatResult += powerOf2;
            powerOf2 /= 2;
        }
        
        if (thisEq.binaryNumber[whichNumber][31])
            floatResult *= (-1);
        return Double.toString(floatResult);
    }

    public void createBinaryArray(String input, byte whichNumber)  {
        double inputValue = Double.parseDouble(input);
        if (isFloat) {
            createFloatArray(inputValue, whichNumber);
            return;
        }
        
        long powerOf2 = TWO_TO_THE_30TH;
        if (outOfRange =/*sic*/ (abs(inputValue) >= powerOf2 * 2)) return;  //  INTENTIONAL: value assigned and evaluated within conditional
        
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
        Arrays.fill(thisEq.binaryNumber[whichNumber], false);
        if (value == 0) return;
        
        if (value < 0)  {
            thisEq.binaryNumber[whichNumber][FloatEquation.SIGN_BIT] = true;
            value *= (-1);
        }
        
        if (value > 1)  {
            Arrays.fill(thisEq.binaryNumber[whichNumber], FloatEquation.EXP_START, FloatEquation.EXP_START + FloatEquation.EXP_LENGTH, true);
            while (powerOf2 <= value && !outOfRange)    {
                powerOf2 *= 2;
                outOfRange = thisEq.addOne(whichNumber, FloatEquation.EXP_START, FloatEquation.EXP_LENGTH);
            }
            powerOf2 /= 2;
        }   else    {
            while (powerOf2 > value && !outOfRange)    {
                powerOf2 /= 2;
                outOfRange = thisEq.subtractOne(whichNumber, FloatEquation.EXP_START, FloatEquation.EXP_LENGTH);
            }
        }
        if (outOfRange) return;
        
        for (int i = FloatEquation.MANT_LENGTH - 1; i >= 0; i--)  {
            if (value >= powerOf2)   {
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
            if (i > 24) {
                if ((!isFloat && i % 4 == 0) || (isFloat && (i == 31 || i == 25)))
                    output += " ";
            }   else if (i % 4 == 0)
                    output += " ";
        }
        return output;
    }
}