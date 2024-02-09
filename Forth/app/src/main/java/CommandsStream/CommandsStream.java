package CommandsStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class CommandsStream {
    
    public CommandsStream(InputStream in) {
        input = new Scanner(in);
    };
    
    public String getNextCommand() { 
        if (0 == Commands_.size()) {
            if (!input.hasNextLine()) {
                return "";
            }

            String line = input.nextLine();
            if (line.isEmpty()) {
                return getNextCommand();
            }

            // Convert multispaces into single space.
            line.replaceAll(" +", " ");
            // Delete spaces from start and end of line.
            line.replaceAll("^ +", "");
            line.replaceAll(" +$", "");
            line.replaceAll("\n+", "");
            
            String[] commands = line.split(" ");
            for (String cmd: commands) {
                Commands_.add(cmd);
            }
        }

        return Commands_.get(0);
    };

    public void removeNextCommand() {
        Commands_.remove(0);
    }

    public boolean isStreamOver() {
        // if Commands List empty and scanner has not a new line.
        return (0 == Commands_.size() && !input.hasNextLine()); 
     }

    public void insert(Collection<String> elements, int pos) {
        Commands_.addAll(pos, elements);
    }

    public void insert(String elements, int pos) {
        Commands_.add(pos, elements);
    }

    private Scanner input;
    private ArrayList<String> Commands_ = new ArrayList<>();
}
