import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

/**
 * Defines some input/output methods which are universal to both the client and server programs.
 */
public abstract class InputOutput {
    private boolean useGUI;

    //Provides the necessary escape codes for colouring output text red on Linux and Mac, but not on Windows
    protected static final String COLOUR_RESET = "\u001B[0m";
    protected static final String COLOUR_RED = "\u001B[31m";

    /**
     * Initialises the output to be either command line or GUI.
     * @param useGUI True to use GUI or false to use command line.
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
                 * displayed and dismissed before exiting.
                 */
                SwingUtilities.invokeAndWait(() -> GUI.writeError(errorDescription));
            }
            catch (InvocationTargetException e) {
                System.out.println(COLOUR_RED +
                        "InvocationTargetException occurred while trying to display an error message." +
                        COLOUR_RESET);
            }
            catch (InterruptedException e) {
                System.out.println(COLOUR_RED +
                        "InterruptedException occurred while trying to display an error message." +
                        COLOUR_RESET);
            }
        }
        else {
            write(COLOUR_RED + "ERROR: " + errorDescription + COLOUR_RESET);
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
     * @throws ExitException If the user has typed <code>EXIT</code>.
     */
    protected String prompt(String desiredOutput) throws ExitException {
            System.out.println(desiredOutput);
            return readConsole();
    }

    /**
     * Read input from the command line.
     * @return The user's input.
     * @throws ExitException If the user has typed <code>EXIT</code>.
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
        System.out.println(message);
    }
}
