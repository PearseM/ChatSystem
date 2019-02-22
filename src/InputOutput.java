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

    /**
     * Prints a string out to the relevant output as an error.
     * @param errorDescription A description of the error that has occurred.
     */
    //TODO: Add a separate type of output for errors.
    protected void error(String errorDescription) {
        write(errorDescription);
    }

    /**
     * @return Whether the program is in GUI mode.
     */
    protected boolean isUsingGUI() {
        return useGUI;
    }

    /**
     * Prompts the user for input.
     * @param desiredOutput The message you would like to output to the user. This should describe what type of input
     *                      you expect.
     * @return The user's input.
     * @throws ExitException If the user has typed <samp>EXIT</samp>.
     */
    protected String prompt(String desiredOutput) throws ExitException{
        if (useGUI) {
            //TODO: Configure prompts for GUI.
            System.out.println(desiredOutput);
            return readConsole();
        }
        else {
            System.out.println(desiredOutput);
            return readConsole();
        }
    }

    /**
     * Specialised variant of prompt to get the port and validate it.
     * @return The user's chosen port or 14001 if their chosen port is invalid.
     */
    protected int promptForPort() {
        String portInput;
        try {
            portInput = prompt("Please enter a port number:");
            int port = Integer.parseInt(portInput);
            return (port>=0 && port<=65535) ? port:14001;
        }
        catch (ExitException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return 14001;
    }

    /**
     * Read input from the command line.
     * @return The user's input.
     * @throws ExitException If the user has typed <samp>EXIT</samp>.
     */
    protected String readConsole() throws ExitException {
        String input;
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                input = consoleReader.readLine();
                if (input!=null) {
                    if (input.equals("EXIT")) {
                        throw new ExitException("Exiting upon user request.");
                    }
                    else if (!input.equals("")) {
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

    /**
     * Writes to the relevant output.
     * @param message The message to be output.
     */
    protected void write(Message message) {
        if (useGUI) {

        }
        else {
            System.out.println(message);
        }
    }
}
