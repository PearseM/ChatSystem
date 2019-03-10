import javax.swing.*;

public class ServerIO extends InputOutput {
    private ChatServerGUI gui;

    /**
     * Initialises the server output to be either command line or GUI.
     * @param useGUI True to use GUI and false to use command line.
     */
    public ServerIO(boolean useGUI) {
        super(useGUI);
        if (useGUI) {
            //Invokes the creation of the GUI on the Event Dispatch Thread, when it is ready
            SwingUtilities.invokeLater(() -> gui = new ChatServerGUI());
        }
    }

    /**
     * Adds the client to the list of clients under the 'Clients' tab in the GUI.
     * @param client The client to add to the list.
     */
    protected void addClientInfo(ServerUserThread client) {
        //Instructs the Event Dispatch Thread to add the client to the GUI clients list, when it is ready
        SwingUtilities.invokeLater(() -> gui.addClient(client));
    }

    @Override
    protected void write(Message message) {
        if (isUsingGUI()) {
            //Instructs the Event Dispatch Thread to add the message to the GUI messages list, when it is ready
            SwingUtilities.invokeLater(() -> gui.addMessage(message));
        }
        else {
            System.out.println(message);
        }
    }

    /**
     * Removes the client from the list of clients under the 'Clients' tab in the GUI.
     * @param client The client to remove from the list.
     */
    protected void removeClient(ServerUserThread client) {
        //Instructs the Event Dispatch Thread to remove the client from the GUI clients list, when it is ready
        SwingUtilities.invokeLater(() -> gui.removeClient(client));
    }
}
