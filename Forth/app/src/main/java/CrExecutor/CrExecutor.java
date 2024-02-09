// Copyright 2023 Olimpiev Y. Y.
package CrExecutor;

import CommandsStream.CommandsStream;
import IExecutor.IExecutor;
import ProgramData.ProgramData;

public class CrExecutor implements IExecutor{

    @Override
    public void Execute(CommandsStream commandsStream, ProgramData programData) {
        programData.printCr();
    }
}
