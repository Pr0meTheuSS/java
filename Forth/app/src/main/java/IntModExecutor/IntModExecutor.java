// Copyright 2023 Olimpiev Y. Y.
package IntModExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntModExecutor implements IExecutor{

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var rightOp = programData.popInt();
        if (0 == rightOp) {
            throw new IllegalArgumentException("Error: try to get mod by zero");
        }
        var leftOp = programData.popInt();
        programData.pushInt(leftOp % rightOp);
    }
}
