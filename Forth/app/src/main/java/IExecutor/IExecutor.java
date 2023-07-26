package IExecutor;

import CommandsStream.*;
import ProgramData.*;

public interface IExecutor {
    /**
     * @param commandsStream
     * @param programData
     */
    public void Execute(CommandsStream commandsStream, ProgramData programData);
}