// Copyright 2023 Olimpiev Y. Y.
package PrintIntExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class PrintIntExecutor implements IExecutor {

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        programData.printIntOut(programData.popInt());
    }        
}
