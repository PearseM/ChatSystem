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
            SwingUtilities.invokeLater(() -> {
                gui = new ChatServerGUI();
            });
        }
    }

    protected void addClientInfo(ServerUserThread client) {
        gui.addClient(client);
    }

    @Override
    protected void write(Message message) {
        if (isUsingGUI()) {
            SwingUtilities.invokeLater(() -> gui.addMessage(message));
        }
        else {
            System.out.println(message);
        }
    }

    protected void removeClient(ServerUserThread client) {
        gui.removeClient(client);
    }
}
