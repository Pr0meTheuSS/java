// Copyright 2023 Olimpiev Y. Y.
package IntMultExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntMultExecutor implements IExecutor {
    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var rightOp = programData.popInt();
        var leftOp = programData.popInt();
        programData.pushInt(leftOp * rightOp);
    }
}
