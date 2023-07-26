// Copyright 2023 Olimpiev Y. Y.
package DoExecutor;

import java.util.ArrayList;
import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class DoExecutor implements IExecutor {

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        ArrayList<String> circleBody = getCircleBody(commandsStream);
        
        int index = programData.popInt();
        int limit = programData.popInt();

        programData.setInnerCircleCounter(index);
                
        int iterationsAmount = limit - index;
        for (int i = 0; i < iterationsAmount; i++) {
            // Insert circle body borders  '{' and '}'
            commandsStream.insert("}", 0);
            commandsStream.insert("{", 0);
            commandsStream.insert(circleBody, 1);
        }
        // Return 'do' token into beginning.
        commandsStream.insert("do ", 0);
    }

    private int recalcBalance(String command, int balance, String left, String right) {
        if (command.equals(left)) {
            return balance + 1;
        }
        if (command.equals(right)) {
            return balance - 1;
        }
        return balance;
    }

    private ArrayList<String> getCircleBody(CommandsStream commandsStream) {
        ArrayList<String> circleBody = new ArrayList<>();
        int balance = 1;
        // Delete 'do' token.
        commandsStream.removeNextCommand();
        while (balance > 0 && !commandsStream.isStreamOver()) {
            String currentCommand = commandsStream.getNextCommand();
            balance = recalcBalance(currentCommand, balance, "do", "loop");
            if (balance < 0) {
                throw new IllegalArgumentException("Syntax Error: in do-loop block:\n" + circleBody.toString());
            }
            circleBody.add(currentCommand);
            commandsStream.removeNextCommand();
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Syntax Error: in do-loop block:\n" + circleBody.toString());
        }
    // Delete 'loop' token.
        circleBody.remove(circleBody.size() - 1);        
        return circleBody;
    }
}
