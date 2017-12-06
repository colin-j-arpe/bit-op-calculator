package bitcalc;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class FloatEquationTest {
    public static FloatEquation[] testEqs = new FloatEquation[8];
    int[][] listOfOnes = {  {26, 25, 24, 23, 18},               //  12.125
                            {26, 24, 22, 21},                   //   5.5                            first pair:     operand1 greater
                            {26, 25, 24, 23, 21, 19},           //  13.25
                            {27, 24, 22, 20, 19, 17},           //  21.625                          second pair:    operand1 lesser
                            {30, 29, 28, 27, 25, 24, 22},       //   0.15625
                            {27, 25, 24, 23, 21, 19, 18, 16},   //  53.625                          third pair:     operand1 less than 1
                            {29, 28, 27, 26, 25, 24, 22, 20},   //  2,818,572,288
                            {29, 28, 27, 26, 24, 23},           //  1,610,612,736                   fourth pair:    too large
                            {30, 26, 24, 22},                   //  0.00000000116415321826...
                            {30, 25, 24, 23},                   //  0.00000000069849193096...       fifth pair:     too small
                            {27, 26, 24, 23, 20, 19, 18, 15, 14 ,11, 10, 7, 6, 3, 2},   //  103.2
                            {27, 26, 25, 24, 21, 18, 16, 13, 12, 9, 8, 5, 4, 1, 0},     //  146.6   sixth pair:     binary repeating decimals
                            {25, 24},       //  2
                            {25, 24},       //  2                                                   seventh pair:   equal operands
                            {26, 24},       //  4
                            {},             //  0                                                   eighth pair:    second operand zero
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

//  Addition tests
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
    
    @Test
    public void testAdditionRepeat()    {
        System.out.append("Test addition with irrational binary fraction");
        assertFalse(testEqs[5].addition()); //  249.8; in range
        assertFalse(testEqs[5].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);  //  positive
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][23]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][22]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][21]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][16]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][15]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][12]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][11]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][8]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][7]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][4]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][3]);
    }

    @Test
    public void testAdditionEqualOps()  {
        System.out.println("Test addition with both operands equal");
        testEqs[6].addition();  //  4
        assertTrue(testEqs[6].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[6].binaryNumber[Equation.RESULT][24]);
    }
    
    @Test
    public void testAdditionZero()  {
        System.out.println("Test addition with zero");
        testEqs[7].addition();  //  4
        assertFalse(testEqs[7].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[7].binaryNumber[Equation.RESULT][26]);
        assertFalse(testEqs[7].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[7].binaryNumber[Equation.RESULT][24]);
        assertFalse(testEqs[7].binaryNumber[Equation.RESULT][23]);
    }

//  Subtraction tests
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
    
    @Test
    public void testSubtractionRepeat() {
        System.out.println("Test subtraction with irrational binary fraction");
        assertFalse(testEqs[5].subtraction());  //  -43.4; in range
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][FloatEquation.SIGN_BIT]);   //  negative
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][22]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][17]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][16]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][13]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][12]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][9]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][8]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][5]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][4]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][2]);
    }
    
    @Test(timeout=2000)
    public void testSubtractionEqualOps()   {
        System.out.println("Test subtraction with equal operands, result zero");
        testEqs[6].subtraction();   //  0
        assertTrue(testEqs[6].binaryNumber[Equation.RESULT][25]);
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][24]);
    }

//  Multiplication tests
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
    
    @Test(timeout=2000)
    public void testMultiplicationTooLarge()    {
        System.out.println("Test multiplication where the result is out of range: too large");
        assertTrue(testEqs[3].multiplication());    //  OOR
    }

    @Test
    public void testMultiplicationTooSmall()    {
        System.out.println("Test multiplication where the result is out of range: too small");
        assertTrue(testEqs[4].multiplication());    //  OOR
    }
    
    @Test
    public void testMultiplicationRepeat()  {
        System.out.println("Test multiplication with irrational binary fraction");
        assertFalse(testEqs[5].multiplication());   //  15,129.12; in range
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][28]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][23]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][22]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][20]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][15]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][14]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][11]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][7]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][6]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][5]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][2]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][1]);
    }
    
    @Test
    public void testMultiplicationZero()    {
        System.out.println("Test multiplication by zero");
        testEqs[7].multiplication();    //  0
        assertFalse(testEqs[7].binaryNumber[Equation.RESULT][24]);
    }

//  Division tests
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
    
    @Test
    public void testDivisionRepeat()    {
        System.out.println("Test division with irrational binary fraction");
        assertFalse(testEqs[5].division()); //  0.7032742155...; in range
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][30]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][29]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][28]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][27]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][26]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][24]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][22]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][21]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][19]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][14]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][13]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][11]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][10]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][7]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][6]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][5]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][3]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][2]);
//        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][1]);
        assertTrue(testEqs[5].binaryNumber[Equation.RESULT][0]);
    }

    @Test
    public void testDivisionEqual() {
        System.out.println("Test division with equal operands");
        assertFalse(testEqs[6].division()); //  1; in range
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][30]);
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][29]);
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][28]);
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][27]);
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][26]);
        assertFalse(testEqs[6].binaryNumber[Equation.RESULT][25]);
        assertTrue(testEqs[6].binaryNumber[Equation.RESULT][24]);
    }

    @Test
    public void testDivisionByZero()    {
        System.out.println("Test division by zero");
        assertTrue(testEqs[7].division());  //  OOR
    }
    
/*
    Easter egg!
    If you casually work the phrase "neon mustard sandwich"
    into the conversation during a job interview,
    I will calmly hand you a lollipop, then carry on 
    as if nothing out of the ordinary has happened.
*/
    
//  Display boolean values to assist debugging failed tests
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
