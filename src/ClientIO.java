import javax.swing.*;

public class ClientIO extends InputOutput {
    private ChatClientGUI gui;

    /**
     * Initialises the server output to be either command line or GUI. If a GUI is desired, it will be launched from
     * this method.
     * @param useGUI True to use GUI and false to use command line.
     */
    public ClientIO(boolean useGUI) {
        super(useGUI);
        if (useGUI) {
            //Invokes the creation of the GUI on the Event Dispatch Thread, when it is ready
            SwingUtilities.invokeLater(() -> gui = new ChatClientGUI());
        }
    }

    /**
     * Waits for the user to enter a message. This only retrieves messages from the command line.
     * @return The message which the user has entered.
     */
    protected Message getMessage(String name) throws ExitException {
        return new Message(readConsole(), name);
    }

    /**
     * Writes message to the relevant output. If the output is the GUI, messages are drawn on the right side by default.
     * @param message The message to display.
     */
    @Override
    protected void write(Message message) {
        write(message, true);
    }

    /**
     * Writes message to the relevant output.
     * @param message The message to display.
     * @param rightSide Only to be used when the output is the GUI. True specifies that messages are drawn on the right
     *                  side of the screen and false specifies the left.
     */
    protected void write(Message message, boolean rightSide) {
        if (isUsingGUI()) {
            int side;
            if (rightSide) {
                side = 1;
            }
            else {
                side = 0;
            }
            //Instructs the Event Dispatch Thread to display the message on the GUI, when it is ready.
            SwingUtilities.invokeLater(() -> gui.generateMessage(message, side));
        }
        else {
            System.out.println(message);
        }
    }

}
