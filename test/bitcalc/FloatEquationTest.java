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
    public static FloatEquation[] testEqs = new FloatEquation[6];
//    int[] nextList = {26, 25, 24, 23, 18};              //  12.125
//    int[] onesInEq0Op2  = {26, 24, 22, 21};                 //   5.5
//    int[] onesInEq1Op1 = {26, 25, 24, 23, 21, 19};          //  13.25
//    int[] onesInEq1Op2  = {27, 24, 22, 20, 19, 17};         //  21.625
//    int[] onesInEq2Op1 = {30, 29, 28, 27, 25, 24, 22};      //   0.15625
//    int[] onesInEq2Op2  = {27, 25, 24, 23, 21, 19, 18, 16}; //  53.625
    
    int[][] listOfOnes = {  {26, 25, 24, 23, 18},               //  12.125
                            {26, 24, 22, 21},                   //   5.5                            first pair:     operand1 greater
                            {26, 25, 24, 23, 21, 19},           //  13.25
                            {27, 24, 22, 20, 19, 17},           //  21.625                          second pair:    operand1 lesser
                            {30, 29, 28, 27, 25, 24, 22},       //   0.15625
                            {27, 25, 24, 23, 21, 19, 18, 16},   //  53.625                          third pair:     operand1 less than 1
                            {29, 28, 27, 26, 25, 24, 22, 20},   //  2,818,572,288
                            {29, 28, 27, 26, 24, 23},           //  1,610,612,736                   fourth pair:    too large
                            {30, 29, 28, 27, 25, 24, 22},       //  0.00000000116415321826...
                            {30, 29, 28, 27, 26, 24, 23},       //  0.00000000069849193096...       fifth pair:     too small
                            {27, 26, 24, 23, 20, 19, 18, 15, 14 ,11, 10, 7, 6, 3, 2},   //  103.2
                            {27, 26, 25, 24, 21, 18, 16, 13, 12, 9, 8, 5, 4, 1, 0}      //  146.6   sixth pair:     binary repeating decimals
                        };
    
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
//  Initialise operands for all test equations
        for (int i = 0; i < testEqs.length; i++)    {
            for (int j = 0; j < listOfOnes[i * 2].length; j++)
                testEqs[i].binaryNumber[Equation.OPERAND1][listOfOnes[i * 2][j]] = true;
            for (int j = 0; j < listOfOnes[(i * 2) + 1].length; j++)
                testEqs[i].binaryNumber[Equation.OPERAND2][listOfOnes[(i * 2) + 1][j]] = true;
        }
////  Initialise operands in first test equation; first operand greater absolute value
//        for (int i = 0; i < onesInEq0Op1.length; i++)  {
//            testEqs[0].binaryNumber[Equation.OPERAND1][onesInEq0Op1[i]] = true;
//        }
//        for (int i = 0; i < onesInEq0Op2.length; i++)  {
//            testEqs[0].binaryNumber[Equation.OPERAND2][onesInEq0Op2[i]] = true;
//        }
//
////  Initialise operands in second test equation; first operand lesser absolute value
//        for (int i = 0; i < onesInEq1Op1.length; i++)  {
//            testEqs[1].binaryNumber[Equation.OPERAND1][onesInEq1Op1[i]] = true;
//        }
//        for (int i = 0; i < onesInEq1Op2.length; i++)  {
//            testEqs[1].binaryNumber[Equation.OPERAND2][onesInEq1Op2[i]] = true;
//        }
//
////  Initialise operands in third test equation; first operand less than one
//        for (int i = 0; i < onesInEq2Op1.length; i++)  {
//            testEqs[2].binaryNumber[Equation.OPERAND1][onesInEq2Op1[i]] = true;
//        }
//        for (int i = 0; i < onesInEq2Op2.length; i++)  {
//            testEqs[2].binaryNumber[Equation.OPERAND2][onesInEq2Op2[i]] = true;
//        }
    }
    
    @After
    public void tearDown() {
//  Reset all binary numbers to zero
        for (FloatEquation testEq : testEqs) {
            for (boolean[] eachNumber : testEq.binaryNumber) {
                Arrays.fill(eachNumber, false);
            }
        }
    }

    /***
     * Test of addition method, of class FloatEquation.
     */
    
    @Test
    public void testAdditionPosPosGtr() {
        System.out.println("Test addition() with both operands positive, first operand greater abs val");
        testEqs[0].addition(); //  17.625
        assertFalse(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][17]);
    }

    @Test
    public void testAdditionNegNegGtr() {
        System.out.println("Test addition() with both operands negative, first operand greater abs val");
        testEqs[0].binaryNumber[Equation.OPERAND1][FloatEquation.SIGN_BIT] = true;
        testEqs[0].binaryNumber[Equation.OPERAND2][FloatEquation.SIGN_BIT] = true;
        testEqs[0].addition(); //  -17.625
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);   //  negative
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][17]);
    }
    
    @Test
    public void testAdditionNegPosGtr() {
        System.out.println("Test addition() with first operand negative, second positive, first operand greater abs val");
        testEqs[0].binaryNumber[Equation.OPERAND1][FloatEquation.SIGN_BIT] = true;
        testEqs[0].addition(); //  -6.625
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);   //  negative
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][23]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][21]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
    }
    
    @Test
    public void testAdditionPosNegGtr() {
        System.out.println("Test addition() with first operand positive, second negative, first operand greater abs val");
        testEqs[0].binaryNumber[Equation.OPERAND2][FloatEquation.SIGN_BIT] = true;
        testEqs[0].addition(); //  6.625
        assertFalse(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][23]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][21]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
    }
    
    @Test(timeout=2000)
    public void testAdditionPosPosLsr() {
        System.out.println("Test addition() with both operands positive, first operand lesser abs val");
        testEqs[1].addition(); //  34.875
        assertFalse(testEqs[1].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][18]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][16]);
    }

    @Test(timeout=2000)
    public void testAdditionNegNegLsr() {
        System.out.println("Test addition() with both operands positive, first operand lesser abs val");
        testEqs[1].binaryNumber[Equation.OPERAND1][FloatEquation.SIGN_BIT] = true;
        testEqs[1].binaryNumber[Equation.OPERAND2][FloatEquation.SIGN_BIT] = true;
        testEqs[1].addition(); //  -34.875
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  negative
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][18]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][16]);
    }

    @Test(timeout=2000)
    public void testAdditionNegPosLsr() {
        System.out.println("Test addition() with first operand negative, second operand positive, first operand lesser abs val");
        testEqs[1].binaryNumber[Equation.OPERAND1][FloatEquation.SIGN_BIT] = true;
        testEqs[1].addition(); //  8.375
        assertFalse(testEqs[1].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][18]);
    }

    @Test(timeout=2000)
    public void testAdditionPosNegLsr() {
        System.out.println("Test addition() with first operand positive, second operand negative, first operand lesser abs val");
        testEqs[1].binaryNumber[Equation.OPERAND2][FloatEquation.SIGN_BIT] = true;
        testEqs[1].addition(); //  -8.375
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  negative
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[1].binaryNumber[Equation.RESULT][18]);
    }

    @Test
    public void testAdditionPosPosLessOne() {
        System.out.println("Test addition() with both operands positive, first operand less than one");
        testEqs[2].addition(); //  53.78125
        assertFalse(testEqs[2].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][23]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][21]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][18]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][14]);
    }

    /**
     * Test of subtraction method, of class FloatEquation.
     */
    @Test
    public void testSubtractionPosPosLessOne() {
        System.out.println("Test subtraction() with both operands positive, first op less than one");
        testEqs[2].subtraction(); //  -53.46875
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  negative
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][23]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][21]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][16]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][15]);
        assertTrue(testEqs[2].binaryNumber[Equation.RESULT][14]);
    }

    /**
     * Test of multiplication method, of class FloatEquation.
     */
    @Test
    public void testMultiplicationPos() {
        System.out.println("Test multiplication with a positive outcome");
        assertFalse(testEqs[0].multiplication());  //  66.6875
        assertFalse(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][15]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][14]);
    }

    @Test
    public void testMultiplicationNeg() {
        System.out.println("Test multiplication with a negative outcome");
        testEqs[0].binaryNumber[Equation.OPERAND1][FloatEquation.SIGN_BIT] = true;
        assertFalse(testEqs[0].multiplication());  //  -66.6875
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);   //  negative
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][15]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][14]);
    }
    
    @Test
    public void testMultiplicationTooLarge()    {
        System.out.print("Test multiplication where the result is out of range: too large");
        assertTrue(testEqs[3].multiplication());    //  OOR
    }

    @Test
    public void testMultiplicationTooSmall()    {
        System.out.print("Test multiplication where the result is out of range: too small");
        assertTrue(testEqs[4].multiplication());    //  OOR
    }

    /**
     * Test of division method, of class FloatEquation.
     */
    @Test
    public void testDivisionPos() {
        System.out.println("testing division with a positive outcome, both operands greater than one");
        testEqs[0].division(); //  2.2045(45)
        assertFalse(testEqs[0].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][13]);
        assertTrue(testEqs[0].binaryNumber[Equation.RESULT][11]);
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
