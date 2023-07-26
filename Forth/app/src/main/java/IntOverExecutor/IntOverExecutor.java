// Copyright 2023 Olimpiev Y. Y.
package IntOverExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntOverExecutor implements IExecutor{

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var top = programData.popInt();
        var second = programData.popInt();
 
        programData.pushInt(second);
        programData.pushInt(top);
        programData.pushInt(second);    
    }
}
