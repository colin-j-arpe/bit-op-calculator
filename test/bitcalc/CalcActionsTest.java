/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcalc;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author cspanw74
 */
public class CalcActionsTest {
    public static CalcFrame testFrame;
    public static CalcActions testCalc;
//    public static Equation testEq;
//    public static FloatEquation testFloat;
    
    public CalcActionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testFrame = new CalcFrame();
        testCalc = new CalcActions(testFrame);
//        testEq = new Equation();
//        testFloat = new FloatEquation();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        testCalc.reset();
//        for (boolean[] numArray : testCalc.thisEq.binaryNumber)
//            Arrays.fill(numArray, false);
//        testCalc.outOfRange = false;
    }

    /**
     * Test of keyTyped method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testKeyTyped() {
        System.out.println("keyTyped");
        KeyEvent input = null;
        CalcActions instance = null;
        instance.keyTyped(input);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of keyPressed method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testKeyPressed() {
        System.out.println("keyPressed");
        KeyEvent input = null;
        CalcActions instance = null;
        instance.keyPressed(input);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of keyReleased method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testKeyReleased() {
        System.out.println("keyReleased");
        KeyEvent input = null;
        CalcActions instance = null;
        instance.keyReleased(input);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionPerformed method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent event = null;
        CalcActions instance = null;
        instance.actionPerformed(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buttonFunctions method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testButtonFunctions() {
        System.out.println("buttonFunctions");
        char entry = ' ';
        CalcActions instance = null;
        instance.buttonFunctions(entry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testReset() {
        System.out.println("reset");
        CalcActions instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of operatorButton method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testOperatorButton() {
        System.out.println("operatorButton");
        char operator = ' ';
        CalcActions instance = null;
        instance.operatorButton(operator);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertToDecimal method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testConvertToDecimal() {
        System.out.println("convertToDecimal");
        boolean[] input = null;
        CalcActions instance = null;
        String expResult = "";
        String result = instance.convertToDecimal(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBinaryArray method, of class CalcActions.
     */
    @Test
    public void testCreateBinaryArrayInt() {
        System.out.println("createBinaryArray with integers");
        String[] input = {"3242010", "-3242010", "999999999999", "-99999999999999"};
        for (byte i = 0; i < 4; i++) {
            testCalc.createBinaryArray(input[i], i);
            if (i < 2)  {
                assertFalse(testCalc.outOfRange);
            }
            else    {
                assertTrue(testCalc.outOfRange);
            }
        }
        for (int i = 31; i > 21; i--)   {
            assertFalse(testCalc.thisEq.binaryNumber[0][i]);    //  first value is positive
            assertTrue(testCalc.thisEq.binaryNumber[1][i]);     //  second value is negative
        }
        assertTrue(testCalc.thisEq.binaryNumber[0][21]);
        assertTrue(testCalc.thisEq.binaryNumber[0][20]);
        assertTrue(testCalc.thisEq.binaryNumber[0][16]);
        assertTrue(testCalc.thisEq.binaryNumber[0][14]);
        assertTrue(testCalc.thisEq.binaryNumber[0][13]);
        assertTrue(testCalc.thisEq.binaryNumber[0][12]);
        assertTrue(testCalc.thisEq.binaryNumber[0][11]);
        assertTrue(testCalc.thisEq.binaryNumber[0][4]);
        assertTrue(testCalc.thisEq.binaryNumber[0][3]);
        assertTrue(testCalc.thisEq.binaryNumber[0][1]);
        
        assertTrue(testCalc.thisEq.binaryNumber[1][19]);
        assertTrue(testCalc.thisEq.binaryNumber[1][18]);
        assertTrue(testCalc.thisEq.binaryNumber[1][17]);
        assertTrue(testCalc.thisEq.binaryNumber[1][15]);
        assertTrue(testCalc.thisEq.binaryNumber[1][10]);
        assertTrue(testCalc.thisEq.binaryNumber[1][9]);
        assertTrue(testCalc.thisEq.binaryNumber[1][8]);
        assertTrue(testCalc.thisEq.binaryNumber[1][7]);
        assertTrue(testCalc.thisEq.binaryNumber[1][6]);
        assertTrue(testCalc.thisEq.binaryNumber[1][5]);
        assertTrue(testCalc.thisEq.binaryNumber[1][2]);
        assertTrue(testCalc.thisEq.binaryNumber[1][1]);
    }

    @Test
    public void testCreateBinaryArrayFloat() {
        System.out.println("createBinaryArray with integers");
        testCalc.isFloat = true;
        String[] input = {"25.00946", "-0.00646", "11.6", "0.0000000000125"};
        for (byte i = 0; i < 4; i++) {
            testCalc.createBinaryArray(input[i], i);
            if (i < 3)  {
                assertFalse(testCalc.outOfRange);
            }
            else    {
                assertTrue(testCalc.outOfRange);
            }
        System.out.println(i);
        System.out.println(testCalc.createBinaryString(i));
        }
        assertTrue(testCalc.thisEq.binaryNumber[0][27]);
        assertTrue(testCalc.thisEq.binaryNumber[0][24]);
        assertTrue(testCalc.thisEq.binaryNumber[0][23]);
        assertTrue(testCalc.thisEq.binaryNumber[0][20]);
        assertTrue(testCalc.thisEq.binaryNumber[0][13]);
        assertTrue(testCalc.thisEq.binaryNumber[0][10]);
        assertTrue(testCalc.thisEq.binaryNumber[0][9]);
        assertTrue(testCalc.thisEq.binaryNumber[0][7]);
        assertTrue(testCalc.thisEq.binaryNumber[0][5]);
        assertTrue(testCalc.thisEq.binaryNumber[0][4]);
        assertTrue(testCalc.thisEq.binaryNumber[0][3]);
        assertTrue(testCalc.thisEq.binaryNumber[0][1]);
        assertTrue(testCalc.thisEq.binaryNumber[0][0]);
        
//        System.out.println(testCalc.createBinaryString((byte)1));
        assertTrue(testCalc.thisEq.binaryNumber[1][31]);
        assertTrue(testCalc.thisEq.binaryNumber[1][30]);
        assertTrue(testCalc.thisEq.binaryNumber[1][29]);
        assertTrue(testCalc.thisEq.binaryNumber[1][28]);
        assertTrue(testCalc.thisEq.binaryNumber[1][24]);
        assertTrue(testCalc.thisEq.binaryNumber[1][23]);
        assertTrue(testCalc.thisEq.binaryNumber[1][21]);
        assertTrue(testCalc.thisEq.binaryNumber[1][18]);
        assertTrue(testCalc.thisEq.binaryNumber[1][17]);
        assertTrue(testCalc.thisEq.binaryNumber[1][16]);
        assertTrue(testCalc.thisEq.binaryNumber[1][14]);
        assertTrue(testCalc.thisEq.binaryNumber[1][12]);
        assertTrue(testCalc.thisEq.binaryNumber[1][11]);
        assertTrue(testCalc.thisEq.binaryNumber[1][10]);
        assertTrue(testCalc.thisEq.binaryNumber[1][7]);
        assertTrue(testCalc.thisEq.binaryNumber[1][6]);
        assertTrue(testCalc.thisEq.binaryNumber[1][4]);
        assertFalse(testCalc.thisEq.binaryNumber[1][3]);
        assertFalse(testCalc.thisEq.binaryNumber[1][2]);
        assertFalse(testCalc.thisEq.binaryNumber[1][1]);
        assertFalse(testCalc.thisEq.binaryNumber[1][0]);
        
        assertTrue(testCalc.thisEq.binaryNumber[2][26]);
        assertTrue(testCalc.thisEq.binaryNumber[2][25]);
        assertTrue(testCalc.thisEq.binaryNumber[2][24]);
        assertTrue(testCalc.thisEq.binaryNumber[2][22]);
        assertTrue(testCalc.thisEq.binaryNumber[2][21]);
        assertTrue(testCalc.thisEq.binaryNumber[2][20]);
        assertTrue(testCalc.thisEq.binaryNumber[2][17]);
        assertTrue(testCalc.thisEq.binaryNumber[2][16]);
        assertTrue(testCalc.thisEq.binaryNumber[2][13]);
        assertTrue(testCalc.thisEq.binaryNumber[2][12]);
        assertTrue(testCalc.thisEq.binaryNumber[2][9]);
        assertTrue(testCalc.thisEq.binaryNumber[2][8]);
        assertTrue(testCalc.thisEq.binaryNumber[2][5]);
        assertTrue(testCalc.thisEq.binaryNumber[2][4]);
        assertTrue(testCalc.thisEq.binaryNumber[2][1]);
        assertTrue(testCalc.thisEq.binaryNumber[2][0]);
    }
            /**
     * Test of createFloatArray method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testCreateFloatArray() {
        System.out.println("createFloatArray");
        double value = 0.0;
        byte whichNumber = 0;
        CalcActions instance = null;
        instance.createFloatArray(value, whichNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBinaryString method, of class CalcActions.
     */
    @Ignore
    @Test
    public void testCreateBinaryString() {
        System.out.println("createBinaryString");
        byte whichBinary = 0;
        CalcActions instance = null;
        String expResult = "";
        String result = instance.createBinaryString(whichBinary);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}