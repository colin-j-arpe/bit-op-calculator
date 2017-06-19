package bitcalc;

import javax.swing.*;
import java.awt.event.*;

public class CalcActions implements KeyListener, ActionListener  {
    CalcFrame gui;
    boolean noDecimal = true, newEntry = true;
    String entryString = "0";

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
        else if (entry == 'C') {
            entryString = "0";
            noDecimal = true;
            newEntry = true;
            gui.entryField.entryText.setText(entryString);
        }
        
    }
}