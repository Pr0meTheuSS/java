// Copyright 2023 Olimpiev Y. Y.
package IntSwapExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntSwapExecutor implements IExecutor{

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var first = programData.popInt();
        var second = programData.popInt();
        programData.pushInt(first);
        programData.pushInt(second);
    }
}
