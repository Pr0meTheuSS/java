/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package forth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ForthInterpreter.ForthInterpreter;

class AppTest {

    @Test 
    void emptyProgramTest() throws IOException {
        String str = "";

        InputStream in = new ByteArrayInputStream(str.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("", out.toString());
        in.close();
        out.close();
    }


    @Test 
    void arithmeticProgressionSumTest() throws IOException {
        StringBuffer strBuffer = new StringBuffer("1");
        for (int i = 0; i < 1000; i++) {
           strBuffer.append(" 1 +");
        }
        strBuffer.append(" .");

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        Assertions.assertEquals("1001", out.toString());
        in.close();
        out.close();
    }

    @Test 
    void arithmeticProgressionSubTest() throws IOException {
        // Prepearing part
        StringBuffer strBuffer = new StringBuffer("1001");
        for (int i = 0; i < 1000; i++) {
            strBuffer.append(" 1 -");
        }
        strBuffer.append(" .");
        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("1", out.toString());
        in.close();
        out.close();

    }

    @Test 
    void multInvariantTest() throws IOException {
        // Prepearing part
        StringBuffer strBuffer = new StringBuffer("1");
        for (int i = 0; i < 1000; i++) {
            strBuffer.append(" 1 *");
        }
        strBuffer.append(" .");

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("1", out.toString());
        in.close();
        out.close();
    }

    @Test 
    void geometricProgressionTest() throws IOException {
    // Prepearing part
        StringBuffer strBuffer = new StringBuffer("1");
        for (int i = 0; i < 30; i++) {
            strBuffer.append(" 2 *");
        }
        strBuffer.append(" .");

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("1073741824", out.toString());
        in.close();
        out.close();

    }

    @Test
    void CompareTest() throws IOException {
        // Init input strings
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("1 2 < if 1 else 0 then . ");
        strBuffer.append("1 2 > if 1 else 0 then . ");
        strBuffer.append("1 2 = if 1 else 0 then . ");
    
        strBuffer.append("0 0 = if 1 else 0 then . ");
        strBuffer.append("-1 -1 < if 1 else 0 then . ");
        strBuffer.append("2 2 > if 1 else 0 then . ");
    
        strBuffer.append("10 -1 > if 1 else 0 then . ");
        strBuffer.append("11 0 < if 1 else 0 then . ");
        strBuffer.append("15 3 = if 1 else 0 then . ");

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("100100100", out.toString());

        in.close();
        out.close();
    }
   
    @Test 
    void nestedIfTest() throws IOException {
        // Prepearing part
        StringBuffer strBuffer = new StringBuffer("1 if 1 . then");
        for (int i = 0; i < 100; i++) {
            strBuffer.insert(0, "1 if ");
            strBuffer.insert(strBuffer.length()," then");
        }

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("1", out.toString());

        in.close();
        out.close();
    }

    @Test 
    void ifElseTest() throws IOException {
        // Prepearing part
        String str = "1 if 1 . else 0 . then 0 if 1 . else 0 . then 1 if 1 . then";

        InputStream in = new ByteArrayInputStream(str.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("101", out.toString());

        in.close();
        out.close();
    }

    @Test 
    void DivTest() throws IOException {
        // Prepearing part
        StringBuffer strBuffer = new StringBuffer("1024");
        for (int i = 0; i < 12; i++) {
            strBuffer.append(" 2 / dup .");
        }

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("512256128643216842100", out.toString());
     
        in.close();
        out.close();
    }
    
    @Test 
    void CrTest() throws IOException {
        // Prepearing part
        StringBuffer strBuffer = new StringBuffer("cr cr cr");

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("\n\n\n", out.toString());

        in.close();
        out.close();
    }  

    @Test 
    void PrintStringTest() throws IOException {
        // Prepearing part
        StringBuffer strBuffer = new StringBuffer(".\" Hello friend\"");

        InputStream in = new ByteArrayInputStream(strBuffer.toString().getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);

        assertEquals("Hello friend", out.toString());

        in.close();
        out.close();
    }

    @Test 
    void divisionByZeroTest() throws IOException {
        // Prepearing part
        String inputStr = "1 0 /";
        // Initial part
        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        
        // Check in and out
        assertThrows(RuntimeException.class, () -> interpreter.Interpret(in, out));

        in.close();
        out.close();
    }

    @Test 
    void CircleInnerCounterTest() throws IOException {
        // Prepearing part
        String inputStr = "10 0 do i . loop";
        // Initial part
        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);        

        // Check in and out
        assertEquals("0123456789", out.toString());

        in.close();
        out.close();
    }

    @Test 
    void CircleInnerCounterDoubleAskTest() throws IOException {
        // Prepearing part
        String inputStr = "10 0 do i . i . loop";

        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);        

        // Check in and out
        assertEquals("00112233445566778899", out.toString());

        in.close();
        out.close();
    }

    @Test 
    void NestedDoLoopTest() throws IOException {
        // Prepearing part
        String inputStr = "3 0 do 5 0 do i . loop loop";

        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);
        interpreter.Interpret(in, out);        

        // Check in and out
        assertEquals("012340123401234", out.toString());

        in.close();
        out.close();
    }

    @Test 
    void popFromEmptyStackTest() throws IOException {
        // Prepearing part
        String inputStr = "1 +";
        // Initial part
        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);        
        // Check in and out
        assertThrows(RuntimeException.class, () -> interpreter.Interpret(in, out));

        in.close();
        out.close();
    }

    @Test 
    void lostLoopTokenTest() throws IOException {
        // Prepearing part
        String inputStr = "100 0 do i . ";
        // Initial part
        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);        
        // Check in and out
        assertThrows(RuntimeException.class, () -> interpreter.Interpret(in, out));

        in.close();
        out.close();
    }

    @Test 
    void lostThenTokenTest() throws IOException {
        // Prepearing part
        String inputStr = "100 if . else 0 .";
        // Initial part
        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);        
        // Check in and out
        assertThrows(RuntimeException.class, () -> interpreter.Interpret(in, out));

        in.close();
        out.close();
    }

    @Test 
    void undefinedTokenTest() throws IOException {
        // Prepearing part
        String inputStr = "undefined_command_token";
        // Initial part
        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ForthInterpreter interpreter = new ForthInterpreter(null);        
        // Check in and out
        assertThrows(RuntimeException.class, () -> interpreter.Interpret(in, out));

        in.close();
        out.close();
    }


    private int Factorial(int n) {
        if (0 >= n) return 1;
        return n * Factorial(n - 1);
    }
    
    @Test 
    void factorialTest() throws IOException {
        for (int i = 0; i < 12; i++) {
            String factCode = "1 1 $value 0 do dup 1 + rot rot * swap loop drop .".replace("$value", Integer.toString(i));

            InputStream in = new ByteArrayInputStream(factCode.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
    
            ForthInterpreter interpreter = new ForthInterpreter(null);        
            interpreter.Interpret(in, out);
            
            assertEquals(Integer.toString(Factorial(i)), out.toString());

            in.close();
            out.close();    
        }
    }
}