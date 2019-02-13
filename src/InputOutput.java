import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class InputOutput {
    private boolean useGUI;

    /**
     * Initialises the output to be either command line or GUI.
     * @param useGUI True to use GUI and false to use command line.
     */
    public InputOutput(boolean useGUI) {
        this.useGUI = useGUI;
    }

    //TODO: Add a separate type of output for errors.
    protected void error(String errorDescription) {
        write(errorDescription);
    }

    protected boolean isUsingGUI() {
        return useGUI;
    }

    protected String prompt(String desiredOutput) {
        if (useGUI) {
            //TODO: Configure prompts for GUI.
            return "";
        }
        else {
            System.out.println(desiredOutput);
            return readConsole();
        }
    }

    protected int promptForPort() {
        //Prompt the user to enter a port number.
        return 14001;
    }

    protected String readConsole() {
        String input;
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                input = consoleReader.readLine();
                if (input!=null) {
                    if (!input.equals("")) {
                        return input;
                    }
                }
            } catch (IOException e) {
                error("IOException occurred while trying to read from console.");
            }
        }
    }

    //TODO: Configure output for GUI
    /**
     * Writes to the relevant output.
     * @param desiredOutput A string containing the desired output.
     */
    protected void write(String desiredOutput) {
        if (useGUI) {
            // Send the desiredOutput to the graphical user interface
        }
        else {
            System.out.println(desiredOutput);
        }
    }

    protected void write(Message message) {
        if (useGUI) {
            // Send the message to the GUI
        }
        else {
            System.out.println(message);
        }
    }
}
