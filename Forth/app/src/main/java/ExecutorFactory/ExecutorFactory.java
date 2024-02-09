// Copyright 2023 Olimpiev Y. Y.
package ExecutorFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import AbstractFactory.AbstractFactory;
import IExecutor.IExecutor;
/**
// Class for creating and returning executors (implementations of IExecutor interface).
*/
public class ExecutorFactory implements AbstractFactory <IExecutor> {    
    /**
     * @param configFilename - .poperties file with pairs like:
     * regexToHandle=ClassHandlerPackage.ClassHandler
     */
    public ExecutorFactory(String configFilename) {
        ExecutorsClassNames_ = new Properties();

        try (InputStream input = ExecutorFactory.class.getClassLoader().getResourceAsStream(configFilename)) {
            // load a properties file.
            ExecutorsClassNames_.load(input);
            
        } catch (IOException ex) {
            System.err.println("Error in executor factory - cannot open .properties file.");
            throw new RuntimeException();
        }
    }
    
    @Override
    public boolean register(String id, String executorClassName) {
        ExecutorsClassNames_.put(id, executorClassName);
        return true;
    };

    @Override
    public boolean unregister(String id) {
        ExecutorsClassNames_.remove(id);
        return true;
    }

    @Override
    public IExecutor get(String executorId) {
        // Get executor class name.
        String executorClassName = ExecutorsClassNames_.getProperty(executorId);
        // If executor with that name is not in cashe - try to add to cashe.
        if (!ExecutorsCache_.containsKey(executorId)) {
            putInExecutorsCaсhe(executorId, executorClassName);
        } 
        return ExecutorsCache_.get(executorId);
    }

    private void putInExecutorsCaсhe(String executorId, String executorClassName) {
        Constructor<?> ctor;
        try {
            ctor = Class.forName(executorClassName).getConstructor();
        } catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            throw new RuntimeException("Error: Factory internal error.");
        }

        IExecutor executorInstance;

        try {
            executorInstance = (IExecutor) ctor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Error: Factory internal error.");
        }
        ExecutorsCache_.put(executorId, executorInstance);    
    }

    private Properties ExecutorsClassNames_;
    private Map<String, IExecutor> ExecutorsCache_ = new HashMap<>();;
}
