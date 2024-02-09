// Copyright 2023 Olimpiev Y. Y.
package IntDivExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntDivExecutor implements IExecutor {
    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var rightOp = programData.popInt();
        var leftOp = programData.popInt();
        if (rightOp == 0) {
            throw new IllegalArgumentException("Error: division by zero");
        }
        programData.pushInt(leftOp / rightOp);
    }
}
