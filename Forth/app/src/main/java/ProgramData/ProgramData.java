// Copyright 2023 Olimpiev Y. Y.
package ProgramData;

import java.io.*;
import java.util.*;

/**
 * Сlass for storing and accessing the context of program execution.
 */
public class ProgramData {

    /**
     * @param out - OutputStream for program output. ;}
     */
    public ProgramData(OutputStream out) {
        Output = new PrintWriter(out, true);
    }

    /**
     * @param val - Pushing value.
     */
    public void pushInt(Integer val) {
        IntStack.add(val);
    }

    /**
     * @return Top value in the stack. Don't change stack's state.
     * @throws IllegalArgumentException in case: try to get top from empty stack.
     */
    public Integer topInt() {
        if (IntStack.size() != 0) {
            return IntStack.get(IntStack.size() - 1);
        } else {
            throw new IllegalArgumentException("Error: try to get top grom empty stack");
        }
    }

    /**
     * @return Top value in a stack and remove this value from the stack.
     */
    public Integer popInt() {
        Integer ret =  IntStack.get(IntStack.size() - 1);
        IntStack.remove(IntStack.size() - 1);
        return ret;
    }
    
    /**
     * @param value - Int value to print out.
     */
    public void printIntOut(int value) {
        Output.print(value);
        Output.flush();
    }

    /**
     * Print new line symbol in program output.
     */
    public void printCr() {
        Output.print("\n");
        Output.flush();
    }

    /**
     * Print string in program output.
     */
    public void printString(String str) {
        Output.print(str);
        Output.flush();
    }

    /**
     * Update program context - increment circle depth counter.
     */
    public void beginCircleBody() {
        CircleBodyDepth++;
    }

    /**
     * Update program context - decrement circle depth and increment inner
     */
    public void endCircleBody() {
        // increment inner counter.
        InnerCircleCounter.set(CircleBodyDepth - 1, InnerCircleCounter.get(CircleBodyDepth - 1) + 1);
        CircleBodyDepth--;
    }

    /**
     * @return value of current circle's counter on current circle depth. 
     */
    public Integer getInnerCircleCounter() {
        return InnerCircleCounter.get(CircleBodyDepth - 1);
    }

    public void setInnerCircleCounter(Integer value) {
        if (InnerCircleCounter.size() <= CircleBodyDepth) {
            InnerCircleCounter.add(InnerCircleCounter.size(), value);
        } else {
            InnerCircleCounter.set(CircleBodyDepth, value);
        }
    }

    /**
     * @param varName - name of int variable.
     */
    public void initIntVariable(String varName) {

    }

    /**
     * @param varName - name of int variable.
     * @return value of this variable.
     * @throws IllegalArgumentException - in case varName is undefined.
     */
    public int getIntVariable(String varName) {
        return 0;
    }

    private ArrayList<Integer> IntStack = new ArrayList<>();
    private PrintWriter Output;

    private ArrayList<Integer> InnerCircleCounter = new ArrayList<>();
    private int CircleBodyDepth = 0;

    // private HashMap<String, Integer> IntVariables_;
}
