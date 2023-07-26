package IfExecutor;

import java.util.ArrayList;
import java.util.Collection;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IfExecutor implements IExecutor {
    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        ArrayList<String> ifBody = new ArrayList<>();
        int balance = 1;
        // Delete first 'if' token.
        commandsStream.removeNextCommand();

        while (balance > 0 && !commandsStream.isStreamOver()) {
            String currentCommand = commandsStream.getNextCommand();
            ifBody.add(currentCommand);
            balance = recalcBalance(currentCommand, balance, "if", "then");
            if (balance < 0) {
                throw new IllegalArgumentException("Syntax Error: in if-then block:\n" + ifBody.toString());
            }
            commandsStream.removeNextCommand();
        }

        if (balance != 0) {
            throw new IllegalArgumentException("Syntax Error: in if-then block:\n" + ifBody.toString());
        }
    
        var branch = programData.topInt() != 0 ? getIfBranch(ifBody) : getElseBranch(ifBody);
        
        // Return 'if' token into beginning.
        branch.add(0, "if ");
        // Insert suitable brunch into commands stream.
        commandsStream.insert(branch, 0);
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

    private ArrayList<String> getIfBranch(Collection<String> ifBody) {
        ArrayList<String> ifBranch =  new ArrayList<String>();
        int balance = 1;
        for (String command : ifBody) {
            balance = recalcBalance(command, balance, "if", "else");
            if (balance > 0) {
                ifBranch.add(command);
            }
            if (balance < 0) {
                throw new IllegalArgumentException("Syntax Error: in if-then block:\n" + ifBody.toString());
            }
        }
        
        if (ifBranch.size() != 0 && ifBranch.get(ifBranch.size() - 1).equals("then")) {
            ifBranch.remove(ifBranch.size() - 1);
        }

        return ifBranch;
    }

    private ArrayList<String> getElseBranch(Collection<String> ifBody) {
        ArrayList<String> elseBranch =  new ArrayList<String>();
        int balance = 1;
        for (String command : ifBody) {
            balance = recalcBalance(command, balance, "if", "else");
            if (balance == 0) {
                elseBranch.add(command);
            }
            if (balance < 0) {
                throw new IllegalArgumentException("Syntax Error: in if-then block:\n" + ifBody.toString());
            }
        }
        
        if( elseBranch.size() != 0) {
            // Delete 'else' token.
            elseBranch.remove(0);
        }
        if (elseBranch.size() != 0) {
            // Delete closed token.
            elseBranch.remove(elseBranch.size() - 1);
        }

        return elseBranch;
    }
}
