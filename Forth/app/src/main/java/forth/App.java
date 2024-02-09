// Copyright 2023 Olimpiev Y. Y.
package forth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ForthInterpreter.ForthInterpreter;
import IInterpreter.IInterpreter;

public class App {
    public static void main(String[] args) {
        IInterpreter interpreter = new ForthInterpreter("executors.properties");

        if (args.length != 0) {
            try(InputStream codeInput = new FileInputStream(args[0])) {
                interpreter.Interpret(codeInput, System.out);
            }
            catch(IOException ex) {
                System.err.println(ex.getMessage());;
            }    
        } else {
            interpreter.Interpret(System.in, System.out);
        }
    }
}
