// Copyright 2023 Olimpiev Y. Y.
package CircleBeginExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class CircleBeginExecutor implements IExecutor{
    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        programData.beginCircleBody();
    }
}
