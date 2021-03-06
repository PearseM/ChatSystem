import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public abstract class InputOutput {
    private boolean useGUI;
    private GUI gui;

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
    protected void error(String errorDescription) {
        if (isUsingGUI()) {
            try {
                /* This invokeAndWait is necessary so that the program will wait until the error message has been
                 * displayed before exiting.
                 */
                SwingUtilities.invokeAndWait(() -> GUI.writeError(errorDescription));
            }
            catch (InvocationTargetException e) {
                System.out.println("InvocationTargetException occurred while trying to display an error message.");
            }
            catch (InterruptedException e) {
                System.out.println("InterruptedException occurred while trying to display an error message.");
            }
        }
        else {
            write("ERROR:" + errorDescription);
        }
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
            try {
                SwingUtilities.invokeAndWait(() -> gui.promptUserForInput(desiredOutput));

            }
            catch (InterruptedException e) {
                error("InterruptedException occurred while trying to prompt the user.");
            } catch (InvocationTargetException e) {
                error("InvocationTargetException occurred while trying to prompt the user.");
            }
        }
        else {
            System.out.println(desiredOutput);
            return readConsole();
        }
        return gui.promptUserForInput(desiredOutput);
    }

    /**
     * Specialised variant of prompt to get the port and validate it.
     * @return The user's chosen port or 14001 if their chosen port is invalid.
     */
    protected int promptForPort() {
        String portInput;
        try {
            portInput = prompt("Please enter a port number. The default is 14001.");
            if (portInput == null) {
                return 14001;
            }
            int port = Integer.parseInt(portInput);
            return (port>=0 && port<=65535) ? port:14001;
        }
        catch (NumberFormatException e) {
            promptForPort();
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

    /**
     * @param gui The GUI instance being used.
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * Writes to the relevant output.
     * @param desiredOutput A string containing the desired output.
     */
    protected void write(String desiredOutput) {
        if (isUsingGUI()) {
            SwingUtilities.invokeLater(() -> GUI.writeInfo(desiredOutput));
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
            //TODO add default message output for GUI (primarily for the server as this method is overridden for the client)
        }
        else {
            System.out.println(message);
        }
    }
}
