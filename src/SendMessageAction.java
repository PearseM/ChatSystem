import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Defines the action to be performed when a message is sent from the <code>ChatClientGUI</code>.
 */
public class SendMessageAction extends AbstractAction {
    protected static ClientServerThread serverThread;
    protected static String name;
    private JTextField inputField;

    /**
     * Creates an action to be performed when the message is to be sent.
     * @param inputField The text field where the message is input.
     */
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
        /* Checks to make sure the message does not contain any curly braces as these would interfere with the way
         * messages are parsed.
         */
        if (text.contains("{") || text.contains("}")) {
            SwingUtilities.invokeLater(() -> GUI.writeInfo("Message cannot contain '{' or '}'!"));
            return;
        }

        Message message = new Message(text, name);
        serverThread.sendMessage(message);

        //Clears the input field
        inputField.setText("");
    }
}
