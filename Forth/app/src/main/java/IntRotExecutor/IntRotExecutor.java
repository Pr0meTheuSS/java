// Copyright 2023 Olimpiev Y. Y.
package IntRotExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntRotExecutor implements IExecutor{

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var first = programData.popInt();
        var second = programData.popInt();
        var third = programData.popInt();
    
        programData.pushInt(second);
        programData.pushInt(first);
        programData.pushInt(third);    
    }
}
