package bitcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcFrame extends JFrame   {
    CalcActions calc = new CalcActions(this);
    EntryPanel entryField = new EntryPanel();
    DisplayPanel display = new DisplayPanel();
    NumberPanel keypad = new NumberPanel();
    OperPanel operators = new OperPanel();

    public CalcFrame()  {
        super("bitCalc");
        setSize(250, 400);
        GridLayout calcLayout = new GridLayout(4, 1);
        setLayout (calcLayout);
        
        entryField.entryText.addKeyListener(calc);
        keypad.b1.addActionListener(calc);
        keypad.b2.addActionListener(calc);
        keypad.b3.addActionListener(calc);
        keypad.b4.addActionListener(calc);
        keypad.b5.addActionListener(calc);
        keypad.b6.addActionListener(calc);
        keypad.b7.addActionListener(calc);
        keypad.b8.addActionListener(calc);
        keypad.b9.addActionListener(calc);
        keypad.b0.addActionListener(calc);
        keypad.point.addActionListener(calc);
        
        
        add(display);
        add(entryField);
        add(keypad);
        add(operators);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class EntryPanel extends JPanel  {
    JTextField entryText = new JTextField("", 12);
    
    public EntryPanel() {
        super();
        entryText.setEditable(false);
        add(entryText);
    }
}

class DisplayPanel extends JPanel    {
    JLabel operand1 = new JLabel();
    JLabel operator = new JLabel();
    JLabel operand2 = new JLabel();
    
    public DisplayPanel()   {
        super();
        add(operand1);
        add(operator);
        add(operand2);
    }
}

class NumberPanel extends JPanel    {
    JButton b1 = new JButton("1");
    JButton b2 = new JButton("2");
    JButton b3 = new JButton("3");
    JButton b4 = new JButton("4");
    JButton b5 = new JButton("5");
    JButton b6 = new JButton("6");
    JButton b7 = new JButton("7");
    JButton b8 = new JButton("8");
    JButton b9 = new JButton("9");
    JButton b0 = new JButton("0");
    JButton point = new JButton(".");
    
    public NumberPanel()    {
        super();
        GridLayout numberLayout = new GridLayout(4, 3);
        setLayout(numberLayout);
        
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);
        add(b0);
        add(point);
    }
}

class OperPanel extends JPanel  {
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JButton multiply = new JButton("x");
    JButton divide = new JButton("/");
    JButton equals = new JButton("=");
    JButton clear = new JButton("C");
    
    public OperPanel()  {
        super();
//        GridLayout operLayout = new GridLayout(3, 6);
//        setLayout(operLayout);
        add(plus);
        add(minus);
        add(multiply);
        add(divide);
        add(equals);
        add(clear);
    }
}