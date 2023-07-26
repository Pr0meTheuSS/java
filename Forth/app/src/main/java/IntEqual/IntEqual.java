// Copyright 2023 Olimpiev Y. Y.
package IntEqual;
import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntEqual implements IExecutor {

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var rightOp = programData.popInt();
        var leftOp = programData.popInt();
        programData.pushInt(Integer.compare(leftOp, rightOp) == 0 ? 1: 0);
    }    
}
