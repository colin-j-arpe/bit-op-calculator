package bitcalc;

import javax.swing.*;
import java.awt.*;

public class CalcFrame extends JFrame   {
    public CalcFrame()  {
        super("bitCalc");
        System.out.println("Here #3");
        setLookAndFeel();
        setSize(240, 400);
        BoxLayout calcLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout (calcLayout);
        
        EntryPanel entryField = new EntryPanel();
//        FlowLayout entryLayout = new FlowLayout();
//        entryField.setLayout(entryLayout);
        DisplayPanel display = new DisplayPanel();
//        FlowLayout displayLayout = new FlowLayout();
//        display.setLayout(displayLayout);
        NumberPanel keypad = new NumberPanel();
        OperPanel operators = new OperPanel();
        
//        keypad.setLayout(calcLayout);
//        operators.setLayout(calcLayout);
        
        this.add(entryField);
        this.add(display);
        this.add(keypad);
        this.add(operators);
        add(this);

        System.out.println("Here #8");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void setLookAndFeel()   {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }   catch (Exception exc)   {
            // ignore
        }
    }
}

class EntryPanel extends JPanel  {
    JTextField entryText = new JTextField("0", 10);
    
    public EntryPanel() {
        super();
        System.out.println("Here #4");
        add(entryText);
    }
}

class DisplayPanel extends JPanel    {
    JLabel operand1 = new JLabel();
    JLabel operator = new JLabel();
    JLabel operand2 = new JLabel();
    
    public DisplayPanel()   {
        super();
        System.out.println("Here #5");
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
//        setSize(200, 200);
        GridLayout numberLayout = new GridLayout(4,3);
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
        System.out.println("Here #6");
    }
}

class OperPanel extends JPanel  {
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JButton multiply = new JButton("x");
    JButton divide = new JButton("/");
    
    public OperPanel()  {
        super();
        System.out.println("Here #7");
        add(plus);
        add(minus);
        add(multiply);
        add(divide);
    }
}