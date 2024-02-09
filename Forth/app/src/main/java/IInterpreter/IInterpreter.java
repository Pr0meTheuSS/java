// Copyright 2023 Olimpiev Y. Y.
package IInterpreter;

import java.io.*;

/** 
 * Интерфейс интерпретатора.
 * @author Olimpiev Y. Y.
 * */
public interface IInterpreter {
    /**
     * Метод обработки текстового содержимого 
     * входного потока в результат работы интерпретатора 
     * в выходной поток.
     *
     * @param in - входной поток с кодом интерпретируемой программы в текстовом формате.
     * @param out - выходной поток для вывода результата работы программы.
     *
     */
    public void Interpret(InputStream in, OutputStream out);
}
