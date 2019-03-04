import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendMessageAction extends AbstractAction {
    public static ClientServerThread serverThread;
    public static String name;

    /**
     * Gets input from the message text field and sends it to the server.
     * @param e The event which triggered this action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField input = (JTextField) e.getSource();
        Message message = new Message(input.getText(), name);
        serverThread.sendMessage(message);
        input.setText("");
    }
}
