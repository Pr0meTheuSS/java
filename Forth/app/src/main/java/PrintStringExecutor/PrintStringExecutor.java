// Copyright 2023 Olimpiev Y. Y.
package PrintStringExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class PrintStringExecutor implements IExecutor {

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        // delete executing command ."
        commandsStream.removeNextCommand();

        StringBuffer rawString = new StringBuffer(commandsStream.getNextCommand());
        commandsStream.removeNextCommand();

        while (rawString.charAt(rawString.length() - 1) != '"') {
            rawString.append(" ");
            rawString.append(commandsStream.getNextCommand());
            commandsStream.removeNextCommand();
        }
        //delete closed symbol "
        //commandsStream.removeNextCommand();

        programData.printString(rawString.substring(0, rawString.length() - 1).toString());
        // return executing token in the beginning.
        commandsStream.insert(".\"", 0);       
    }
}



