public class ClientIO extends InputOutput {

    private ChatClientGUI gui;

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
            return new Message(readConsole(), name);//TODO: Get message from GUI
        }
        else {
            return new Message(readConsole(), name);
        }
    }

    public void setGui(ChatClientGUI gui) {
        this.gui = gui;
    }

    /**
     * Writes message to the relevant output. If the output is the GUI, messages are drawn on the right side by default.
     * @param message
     */
    protected void write(Message message) {
        if (isUsingGUI()) {
            gui.generateMessage(message, 1);
            gui.getChatPane().revalidate();
        }
        else {
            System.out.println(message);
        }
    }

    /**
     * Writes message to the relevant output.
     * @param message
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
            gui.generateMessage(message, side);
            gui.getChatPane().revalidate();
            gui.scrollPaneToBottom();
        }
        else {
            System.out.println(message);
        }
    }

}
