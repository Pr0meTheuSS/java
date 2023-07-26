/*
 * Project: Torrent
 * Created Date: Sunday, June 27th 2023, 3:33:25 am
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package ConsoleView;

public class ConsoleView {
    public void setLoadingStatus(int currentTask, int totalTasks) {
       int progress = (int) ((double) currentTask / totalTasks * 100);
        int barLength = 50;
        int filledLength = (int) (barLength * progress / 100);

        StringBuilder progressBar = new StringBuilder();
        progressBar.append('[');
        for (int i = 0; i < filledLength; i++) {
            progressBar.append('=');
        }
        for (int i = filledLength; i < barLength; i++) {
            progressBar.append(' ');
        }
        progressBar.append(']');

        System.out.print("\r" + "Progress: " + progress + "% " + progressBar.toString());

        if (currentTask == totalTasks) {
           System.out.println();
        }   
    }
}






