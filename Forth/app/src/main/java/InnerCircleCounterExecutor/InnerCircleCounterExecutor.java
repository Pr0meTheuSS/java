// Copyright 2023 Olimpiev Y. Y.
package InnerCircleCounterExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class InnerCircleCounterExecutor implements IExecutor {

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        commandsStream.insert(programData.getInnerCircleCounter().toString(), 1);
    }
}
