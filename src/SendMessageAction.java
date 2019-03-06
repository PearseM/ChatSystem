import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendMessageAction extends AbstractAction {
    public static ClientServerThread serverThread;
    public static String name;
    private JTextField inputField;

    public SendMessageAction(JTextField inputField) {
        this.inputField = inputField;
    }

    /**
     * Gets input from the message text field and sends it to the server.
     * @param e The event which triggered this action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = inputField.getText();
        if (text.contains("{") || text.contains("}")) {
            SwingUtilities.invokeLater(() -> GUI.writeInfo("Message cannot contain '{' or '}'!"));
            return;
        }
        Message message = new Message(text, name);
        serverThread.sendMessage(message);
        inputField.setText("");
    }
}
