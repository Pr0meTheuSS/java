// Copyright 2023 Olimpiev Y.
package RegexCommandParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import ExecutorFactory.ExecutorFactory;

public class RegexCommandParser {

    public RegexCommandParser() {
        Properties prop = new Properties();

        try (InputStream input = ExecutorFactory.class.getClassLoader().getResourceAsStream("executors.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            System.err.println("Config Error: Regex Parser - cannot open .properties file.");
            throw new RuntimeException();
        }
        Enumeration<?> propNames = prop.propertyNames();

        while (propNames.hasMoreElements()) {
            String key = propNames.nextElement().toString();     
            commandsRegex.add(key);
        }
    }

    public String getMatchRegex(String command) {
        for (String regex: commandsRegex) {
            if (Pattern.matches(regex, command)) {
                return regex;
            }
        }
        // If command has not any confirm regex - throw exception.
        throw new IllegalArgumentException("Error: undefined symbol: " + command + "Code: " + command.codePointAt(0));
    }

    private List<String> commandsRegex = new ArrayList<>();
}
