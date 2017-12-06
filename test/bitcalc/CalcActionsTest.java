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

public class CalcActionsTest {
    public static CalcFrame testFrame;
    public static CalcActions testCalc;
    public int[][] listOfOnes = {   {29, 26, 25, 24, 21, 20, 19, 17, 16, 15, 11, 10, 8, 6, 1, 0},   //  646,250.0946
                                    {28, 25, 24, 22, 17, 16, 13, 1, 0},                             //  646.2500946
                                    {30, 29, 28, 27, 24, 19, 15, 13, 12, 10, 5, 4, 3, 0},           //  0.064625
                                    {31, 27, 25, 24, 21, 19, 18, 16, 14, 12}                        //  -37.6640625
                                };
    
    public CalcActionsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testFrame = new CalcFrame();
        testCalc = new CalcActions(testFrame);
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
    }

    @Test
    public void testCreateBinaryArray() {
        System.out.println("createBinaryArray with integers");
        testCalc.isFloat = false;
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
    public void testCreateFloatArray() {
        System.out.println("createBinaryArray with integers");
        testCalc.isFloat = true;
        String[] input = {"25.00946", "-0.00646", "11.6", "2.5"};
        for (byte i = 0; i < 4; i++) {
            testCalc.createBinaryArray(input[i], i);
        }
        //  test 25.00946
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

        //  test -0.00646
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
        
        //  test 11.6
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
        
        //  test 2.5
        assertTrue(testCalc.thisEq.binaryNumber[3][25]);
        assertTrue(testCalc.thisEq.binaryNumber[3][24]);
        assertTrue(testCalc.thisEq.binaryNumber[3][22]);
        assertFalse(testCalc.thisEq.binaryNumber[3][21]);
        assertFalse(testCalc.thisEq.binaryNumber[3][20]);
        assertFalse(testCalc.thisEq.binaryNumber[3][19]);
        assertFalse(testCalc.thisEq.binaryNumber[3][16]);
        assertFalse(testCalc.thisEq.binaryNumber[3][15]);
        assertFalse(testCalc.thisEq.binaryNumber[3][12]);
        assertFalse(testCalc.thisEq.binaryNumber[3][11]);
        assertFalse(testCalc.thisEq.binaryNumber[3][8]);
        assertFalse(testCalc.thisEq.binaryNumber[3][7]);
        assertFalse(testCalc.thisEq.binaryNumber[3][4]);
        assertFalse(testCalc.thisEq.binaryNumber[3][3]);
        assertFalse(testCalc.thisEq.binaryNumber[3][0]);
    }

    @Test
    public void testConvertToFloat()    {
        System.out.println("testing output string from float equations");
        testCalc.isFloat = true;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < listOfOnes[i].length; j++)
                testCalc.thisEq.binaryNumber[i][listOfOnes[i][j]] = true;
        
        assertEquals(testCalc.convertToInteger((byte)0), "646250.09375");
//        assertEquals(testCalc.convertToInteger((byte)1), "646.250091552784375");
        assertEquals(testCalc.convertToInteger((byte)1), "646.2500915527344");      //  rounded off by Java double datatype
//        assertEquals(testCalc.convertToInteger((byte)2), "0.0646249987185001373291015625");
        assertEquals(testCalc.convertToInteger((byte)2), "0.06462499871850014");    //  rounded off by Java double datatype
        assertEquals(testCalc.convertToInteger((byte)3), "-37.6640625");
        
    }
    
}