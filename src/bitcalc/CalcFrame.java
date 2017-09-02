package bitcalc;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class CalcFrame extends JFrame   {
    CalcActions calc = new CalcActions(this);
    EntryPanel entryField = new EntryPanel();
    DisplayPanel display = new DisplayPanel();
    NumberPanel keypad = new NumberPanel();
    OperPanel operators = new OperPanel();

    public CalcFrame()  {
        super("bitCalc");
        setSize(300, 420);
        JPanel calcWindow = new JPanel();
        BoxLayout calcLayout = new BoxLayout(calcWindow, BoxLayout.Y_AXIS);
        calcWindow.setLayout (calcLayout);
        
        calcWindow.add(entryField);
        calcWindow.add(display);
        calcWindow.add(keypad);
        calcWindow.add(operators);
        add(calcWindow);

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
        operators.plus.addActionListener(calc);
        operators.minus.addActionListener(calc);
        operators.multiply.addActionListener(calc);
        operators.divide.addActionListener(calc);
        operators.equals.addActionListener(calc);
        operators.clear.addActionListener(calc);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class EntryPanel extends JPanel  {
    JTextField entryText = new JTextField("0", 10);
    
    public EntryPanel() {
        super();
        entryText.setEditable(false);
        add(entryText);
    }
}

class DisplayPanel extends JPanel    {
    JTextField binaryOperand1 = new JTextField();
    JTextField decimalOperand1 = new JTextField();
    JLabel operator = new JLabel(" ");
    JTextField binaryOperand2 = new JTextField();
    JTextField decimalOperand2 = new JTextField();
    JTextField binaryResult = new JTextField();
    JTextField decimalResult = new JTextField();
    
    public DisplayPanel()   {
        super();
        binaryOperand1.setEditable(false);
        decimalOperand1.setEditable(false);
        binaryOperand2.setEditable(false);
        decimalOperand2.setEditable(false);
        binaryResult.setEditable(false);
        decimalResult.setEditable(false);
        
        BoxLayout displayLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(displayLayout);
        this.add(binaryOperand1);
        this.add(decimalOperand1);
        this.add(operator);
        this.add(binaryOperand2);
        this.add(decimalOperand2);
        this.add(binaryResult);
        this.add(decimalResult);
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
        GridLayout numberLayout = new GridLayout(4, 3, 10, 10);
        setLayout(numberLayout);
        point.setEnabled(false);
        
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
//        add(point);
    }
    
    public Insets getInsets()   {
        Insets borders = new Insets (0, 40, 0, 40);
        return borders;
    }
}

class OperPanel extends JPanel  {
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JButton multiply = new JButton("*");
    JButton divide = new JButton("/");
    JButton equals = new JButton("=");
    JButton clear = new JButton("C");
    
    public OperPanel()  {
        super();
        equals.setEnabled(false);

        add(plus);
        add(minus);
        add(multiply);
        add(divide);
        add(equals);
        add(clear);
    }
}