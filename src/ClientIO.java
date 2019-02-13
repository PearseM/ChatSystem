public class ClientIO extends InputOutput {
    /**
     * Initialises the server output to be either command line or GUI.
     * @param useGUI True to use GUI and false to use command line.
     */
    public ClientIO(boolean useGUI) {
        super(useGUI);
    }

    /**
     * Waits for the user to enter a message.
     * @return The message which the user has entered.
     */
    protected Message getMessage(String name) {
        if (isUsingGUI()) {
            return null;//TODO: Get message from GUI
        }
        else {
            return new Message(readConsole(), name);
        }
    }

}
