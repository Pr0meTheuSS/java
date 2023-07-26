// Copyright 2023 Olimpiev Y. Y.
package IntDupExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntDupExecutor implements IExecutor{

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        var top = programData.topInt();
        programData.pushInt(top);
    }
}
