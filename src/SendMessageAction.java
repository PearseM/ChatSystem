import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendMessageAction extends AbstractAction {
    public static ClientServerThread serverThread;
    private ChatClientGUI gui;

    /**
     * Constructs a new send message action.
     * @param gui The instance of the GUI that created this action.
     */
    public SendMessageAction(ChatClientGUI gui) {
        this.gui = gui;
    }

    /**
     * Gets input from the message text field and sends it to the server.
     * @param e The event which triggered this action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField input = gui.getMessageInput();
        Message message = new Message(input.getText(), gui.getClient().getName());
        serverThread.sendMessage(message);
        input.setText("");
    }
}
