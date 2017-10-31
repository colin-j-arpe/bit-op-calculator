/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcalc;

import javax.swing.UIManager;

/**
 *
 * @author colin.j.arpe
 */
public class BitCalc {

    /**
     * @param args the command line arguments
     */
    private static void setLookAndFeel()   {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }   catch (Exception exc)   {
            // ignore
        }
    }

    
    public static void main(String[] args) {
        setLookAndFeel();
        CalcFrame thisFrame = new CalcFrame();
    }
    
}
