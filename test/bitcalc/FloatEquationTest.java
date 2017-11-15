/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcalc;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author cspanw74
 */
public class FloatEquationTest {
    public FloatEquation testEq = new FloatEquation();
//    Arrays.fill(testEq.binaryNumber, false);
    
    public FloatEquationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
//        FloatEquation testEq = new FloatEquation();
//        Arrays.fill(testEq.binaryNumber, false);
//        int[] onesInGreater = {26, 25, 24, 23, 18};
//        int[] onesInLesser  = {26, 24, 22, 21};
//        for (int i = 0; i < 4; i++) {
//            Arrays.fill(testEq.binaryNumber[i], false);
//        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        for (boolean[] eachNumber: testEq.binaryNumber)
            Arrays.fill(eachNumber, false);
        int[] onesInGreater = {26, 25, 24, 23, 18}; //  12.125
        int[] onesInLesser  = {26, 24, 22, 21};     //   5.5
//  Initialise operands
        for (int i = 0; i < onesInGreater.length; i++)  {
            testEq.binaryNumber[testEq.OPERAND1][onesInGreater[i]] = true;
        }
        for (int i = 0; i < onesInLesser.length; i++)  {
            testEq.binaryNumber[testEq.OPERAND2][onesInLesser[i]] = true;
        }
//        printOnes(testEq.OPERAND1);
//        printOnes(testEq.OPERAND2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addition method, of class FloatEquation.
     */
    @Test
    public void testAdditionPosPosGtr() {
        System.out.println("Test addition() with both operands positive, first operand greater abs val");
        testEq.addition();
//        printOnes(testEq.RESULT);
//        printOnes(testEq.OPERAND1);
//        printOnes(testEq.OPERAND2);
        assertFalse(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][27]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][20]);
        assertTrue(testEq.binaryNumber[2][19]);
        assertTrue(testEq.binaryNumber[2][17]);
    }

    @Test
    public void testAdditionNegNegGtr() {
        System.out.println("Test addition() with both operands negative, first operand greater abs val");
        testEq.binaryNumber[0][31] = true;
        testEq.binaryNumber[1][31] = true;
        testEq.addition();
//        printOnes(testEq.RESULT);
        assertTrue(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][27]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][20]);
        assertTrue(testEq.binaryNumber[2][19]);
        assertTrue(testEq.binaryNumber[2][17]);
    }
    
    @Test
    public void testAdditionNegPosGtr() {
        System.out.println("Test addition() with first operand negative, second positive, first operand greater abs val");
        testEq.binaryNumber[0][31] = true;
        testEq.binaryNumber[1][31] = false;
        testEq.addition();
//        printOnes(testEq.RESULT);
        assertTrue(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][26]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][23]);
        assertTrue(testEq.binaryNumber[2][21]);
        assertTrue(testEq.binaryNumber[2][19]);
    }
    
    @Test
    public void testAdditionPosNegGtr() {
//  Second operand negative
        System.out.println("Test addition() with first operand positive, second negative, first operand greater abs val");
        testEq.binaryNumber[0][31] = false;
        testEq.binaryNumber[1][31] = true;
        testEq.addition();
//        printOnes(testEq.RESULT);
        assertFalse(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][26]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][23]);
        assertTrue(testEq.binaryNumber[2][21]);
        assertTrue(testEq.binaryNumber[2][19]);
    }
    
    @Ignore
    @Test
    public void testAdditionLesser()  {
        System.out.println("testing addition with operand1 abs value lesser");

//        FloatEquation testEq = new FloatEquation();
//        Arrays.fill(testEq.binaryNumber, false);
        int[] onesInGreater = {26, 25, 24, 22, 19, 17};     //  10.15625
        int[] onesInLesser  = {30, 29, 28, 27, 26, 24, 21}; //   0.28125
//  Initialise operands
        for (int i = 0; i < onesInGreater.length; i++)  {
            testEq.binaryNumber[1][i] = true;
        }
        for (int i = 0; i < onesInLesser.length; i++)  {
            testEq.binaryNumber[0][i] = true;
        }

//  Both operands positive
        System.out.println("Test addition() with both operands positive, first operand lesser abs val");
        testEq.addition();
        assertFalse(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][26]);
        assertTrue(testEq.binaryNumber[2][25]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][22]);
        assertTrue(testEq.binaryNumber[2][19]);
        assertTrue(testEq.binaryNumber[2][18]);
        assertTrue(testEq.binaryNumber[2][17]);
        
//  Both operands negative
        System.out.println("Test addition() with both operands negative, first operand lesser abs val");
        testEq.binaryNumber[0][31] = true;
        testEq.binaryNumber[1][31] = true;
        testEq.addition();
        assertTrue(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][26]);
        assertTrue(testEq.binaryNumber[2][25]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][22]);
        assertTrue(testEq.binaryNumber[2][19]);
        assertTrue(testEq.binaryNumber[2][18]);
        assertTrue(testEq.binaryNumber[2][17]);
        
//  First operand negative
        System.out.println("Test addition() with first operand negative, second positive, first operand lesser abs val");
//        testEq.binaryNumber[0][31] = true;
        testEq.binaryNumber[1][31] = false;
        testEq.addition();
        assertFalse(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][26]);
        assertTrue(testEq.binaryNumber[2][25]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][21]);
        assertTrue(testEq.binaryNumber[2][20]);
        assertTrue(testEq.binaryNumber[2][19]);
        assertTrue(testEq.binaryNumber[2][18]);
    
//  Second operand negative
        System.out.println("Test addition() with first operand positive, second negative, first operand lesser abs val");
//        testEq.binaryNumber[0][31] = true;
        testEq.binaryNumber[1][31] = false;
        testEq.addition();
        assertTrue(testEq.binaryNumber[2][31]);
        assertTrue(testEq.binaryNumber[2][26]);
        assertTrue(testEq.binaryNumber[2][25]);
        assertTrue(testEq.binaryNumber[2][24]);
        assertTrue(testEq.binaryNumber[2][21]);
        assertTrue(testEq.binaryNumber[2][20]);
        assertTrue(testEq.binaryNumber[2][19]);
        assertTrue(testEq.binaryNumber[2][18]);
    }   

    /**
     * Test of subtraction method, of class FloatEquation.
     */
    @Ignore
    @Test
    public void testSubtraction() {
//        System.out.println("subtraction");
//        FloatEquation instance = new FloatEquation();
//        boolean expResult = false;
//        boolean result = instance.subtraction();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of multiplication method, of class FloatEquation.
     */
    @Test
    public void testMultiplicationPos() {
        System.out.println("Test multiplication with a positive outcome");
        assertFalse(testEq.multiplication());
//        printOnes(testEq.OPERAND1);
//        printOnes(testEq.OPERAND2);
//        printOnes(testEq.RESULT);
        assertFalse(testEq.binaryNumber[testEq.RESULT][31]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][27]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][26]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][24]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][19]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][17]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][15]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][14]);
    }

    @Test
    public void testMultiplicationNeg() {
        System.out.println("Test multiplication with a negative outcome");
        testEq.binaryNumber[testEq.OPERAND1][31] = true;
        assertFalse(testEq.multiplication());
//        testEq.multiplication();
        printOnes(testEq.RESULT);
        assertTrue(testEq.binaryNumber[testEq.RESULT][31]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][27]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][26]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][24]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][19]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][17]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][15]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][14]);
    }

    /**
     * Test of division method, of class FloatEquation.
     */
    @Test
    public void testDivision() {
        System.out.println("testing division with a positive outcome, both operands greater than one");
        testEq.division();
        printOnes(testEq.RESULT);
        assertTrue(testEq.binaryNumber[testEq.RESULT][25]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][24]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][20]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][19]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][17]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][13]);
        assertTrue(testEq.binaryNumber[testEq.RESULT][11]);
    }

    public void printOnes(byte whichNumber) {
        int trues = 0;
        for (int i = 31; i >= 0; i--)   {
            if (testEq.binaryNumber[whichNumber][i])  {
                System.out.print(i + ":" + testEq.binaryNumber[whichNumber][i] + "; ");
                trues++;
            }
        }
        System.out.println(trues + " ones");
    }
    
}
