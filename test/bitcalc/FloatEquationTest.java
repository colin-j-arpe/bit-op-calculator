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
    public static FloatEquation[] testEqs = new FloatEquation[4];
    int[] onesInEq0Op1 = {26, 25, 24, 23, 18};          //  12.125
    int[] onesInEq0Op2  = {26, 24, 22, 21};             //   5.5
    int[] onesInEq1Op1 = {26, 25, 24, 23, 21, 19};      //  13.25
    int[] onesInEq1Op2  = {27, 24, 22, 20, 19, 17};     //  21.625
    int[] onesInEq2Op1 = {30, 29, 28, 27, 26, 24, 21};  //   0.28125
    int[] onesInEq2Op2  = {26, 25, 24, 22, 19, 17};     //  10.15625
    
//    Arrays.fill(testEq.binaryNumber, false);
    
    public FloatEquationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        for (int i = 0; i < testEqs.length; i++)
            testEqs[i] = new FloatEquation();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
//  Reset all binary numbers to zero
        for (FloatEquation testEq : testEqs) {
            for (boolean[] eachNumber : testEq.binaryNumber) {
                Arrays.fill(eachNumber, false);
            }
        }
//  Initialise operands in first test equation; first operand greater absolute value
        for (int i = 0; i < onesInEq0Op1.length; i++)  {
            testEqs[0].binaryNumber[testEqs[0].OPERAND1][onesInEq0Op1[i]] = true;
        }
        for (int i = 0; i < onesInEq0Op2.length; i++)  {
            testEqs[0].binaryNumber[testEqs[0].OPERAND2][onesInEq0Op2[i]] = true;
        }

//  Initialise operands in second test equation; first operand lesser absolute value
        for (int i = 0; i < onesInEq1Op1.length; i++)  {
            testEqs[0].binaryNumber[testEqs[1].OPERAND1][onesInEq1Op1[i]] = true;
        }
        for (int i = 0; i < onesInEq1Op2.length; i++)  {
            testEqs[0].binaryNumber[testEqs[1].OPERAND2][onesInEq1Op2[i]] = true;
        }

//  Initialise operands in third test equation; first operand less than one
        for (int i = 0; i < onesInEq2Op1.length; i++)  {
            testEqs[0].binaryNumber[testEqs[2].OPERAND1][onesInEq2Op1[i]] = true;
        }
        for (int i = 0; i < onesInEq2Op2.length; i++)  {
            testEqs[0].binaryNumber[testEqs[2].OPERAND2][onesInEq2Op2[i]] = true;
        }
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
        testEqs[0].addition(); //  17.625
        assertFalse(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);  //  positive
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][20]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][17]);
    }

    @Test
    public void testAdditionNegNegGtr() {
        System.out.println("Test addition() with both operands negative, first operand greater abs val");
        testEqs[0].binaryNumber[testEqs[0].OPERAND1][testEqs[0].SIGN_BIT] = true;
        testEqs[0].binaryNumber[testEqs[0].OPERAND2][testEqs[0].SIGN_BIT] = true;
        testEqs[0].addition(); //  -17.625
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);   //  negative
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][20]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][17]);
    }
    
    @Test
    public void testAdditionNegPosGtr() {
        System.out.println("Test addition() with first operand negative, second positive, first operand greater abs val");
        testEqs[0].binaryNumber[testEqs[0].OPERAND1][testEqs[0].SIGN_BIT] = true;
        testEqs[0].binaryNumber[testEqs[0].OPERAND2][testEqs[0].SIGN_BIT] = false;
        testEqs[0].addition(); //  -6.625
        printOnes(0, testEqs[0].RESULT);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);   //  negative
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][23]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][21]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
    }
    
    @Test
    public void testAdditionPosNegGtr() {
        System.out.println("Test addition() with first operand positive, second negative, first operand greater abs val");
        testEqs[0].binaryNumber[testEqs[0].OPERAND1][testEqs[0].SIGN_BIT] = false;
        testEqs[0].binaryNumber[testEqs[0].OPERAND2][testEqs[0].SIGN_BIT] = true;
        testEqs[0].addition(); //  6.625
        assertFalse(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);  //  positive
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][23]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][21]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
    }
    
    @Test(timeout=5000)
    public void testAdditionPosPosLsr() {
        System.out.println("Test addition() with both operands positive, first operand lesser abs val");
        printOnes(1, testEqs[1].OPERAND1);
        printOnes(1, testEqs[1].OPERAND2);
        testEqs[1].addition(); //  10.4375
        printOnes(1, testEqs[1].RESULT);
        assertFalse(testEqs[1].binaryNumber[testEqs[1].RESULT][testEqs[0].SIGN_BIT]);  //  positive
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][26]);
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][25]);
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][24]);
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][22]);
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][19]);
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][18]);
        assertTrue(testEqs[1].binaryNumber[testEqs[1].RESULT][17]);
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
        assertFalse(testEqs[0].multiplication());  //  66.6875
        assertFalse(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);  //  positive
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][17]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][15]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][14]);
    }

    @Test
    public void testMultiplicationNeg() {
        System.out.println("Test multiplication with a negative outcome");
        testEqs[0].binaryNumber[testEqs[0].OPERAND1][testEqs[0].SIGN_BIT] = true;
        assertFalse(testEqs[0].multiplication());  //  -66.6875
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);   //  negative
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][17]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][15]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][14]);
    }

    /**
     * Test of division method, of class FloatEquation.
     */
    @Test
    public void testDivisionPos() {
        System.out.println("testing division with a positive outcome, both operands greater than one");
        testEqs[0].division(); //  2.20r(45)
//        printOnes(testEqs[0].RESULT);
        assertFalse(testEqs[0].binaryNumber[testEqs[0].RESULT][testEqs[0].SIGN_BIT]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][25]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][20]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][17]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][13]);
        assertTrue(testEqs[0].binaryNumber[testEqs[0].RESULT][11]);
    }

    public void printOnes(int whichEquation, byte whichNumber) {
        int trues = 0;
        for (int i = 31; i >= 0; i--)   {
            if (testEqs[whichEquation].binaryNumber[whichNumber][i])  {
                System.out.print(i + ":" + testEqs[whichEquation].binaryNumber[whichNumber][i] + "; ");
                trues++;
            }
        }
        System.out.println(trues + " ones");
    }
    
}
