// Copyright 2023 Olimpiev Y. Y.
package ForthInterpreter;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import CommandsStream.CommandsStream;
import ExecutorFactory.ExecutorFactory;
import IExecutor.IExecutor;
import IInterpreter.IInterpreter;
import ProgramData.ProgramData;
import RegexCommandParser.RegexCommandParser;

public class ForthInterpreter implements IInterpreter {
    
    public ForthInterpreter(String configFilename) {
        configFilename_ = (configFilename == null) ? "executors.properties": configFilename;
    }
 
    @Override
    public void Interpret(InputStream in, OutputStream out) {
        try {
            logger.info("Forth Interpreter is running!");

            ExecutorFactory execFactory = new ExecutorFactory(configFilename_);
            CommandsStream commandsStream = new CommandsStream(in);
            ProgramData programData = new ProgramData(out);
            RegexCommandParser commandRegexParser = new RegexCommandParser();

            // Run loop with handling tokens from commands stream.
            while (!commandsStream.isStreamOver()) {
                String currentCommand = commandsStream.getNextCommand();
                if (currentCommand != "\n") {
                    logger.info("Start to execute command: " + currentCommand);

                    String matchCommandRegex = commandRegexParser.getMatchRegex(currentCommand);
                    logger.info("Executing command with regex: " + matchCommandRegex);
    
                    IExecutor executor = execFactory.get(matchCommandRegex);
    
                    executor.Execute(commandsStream, programData);
                    commandsStream.removeNextCommand();    
                }
            }
        }
        catch(IllegalArgumentException argEx) {
            System.err.println("Forth interpreter aborted with error:\n" + argEx.getMessage());
            logger.error(argEx.getMessage());
            throw new RuntimeException();
        }

        logger.info("Shutdown, friends!\nBye. =D");
        //hello kitty ^^ <3 
    }

    private String configFilename_;
    private final static Logger logger = LogManager.getLogger(ForthInterpreter.class);
}
