// Copyright 2023 Olimpiev Y. Y.
package CircleEndExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class CircleEndExecutor implements IExecutor{
    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        programData.endCircleBody();
    }
}
