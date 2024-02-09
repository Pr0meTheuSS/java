// Copyright 2023 Olimpiev Y. Y.
package IntValuePushExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class IntValuePushExecutor implements IExecutor{
    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        programData.pushInt(Integer.parseInt(commandsStream.getNextCommand()));
    }
}
