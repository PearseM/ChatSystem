import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Defines the action to be performed when the user chooses to launch either a server or client.
 */
public class LaunchAction extends AbstractAction {
    private JTextField portField;
    private JTextField hostnameField;
    private JTextField nicknameField;
    private boolean isClient;

    /**
     * Creates a <code>LaunchAction</code> which should be used to launch a chat server.
     * @param portField The field that was used to obtain the port input.
     */
    public LaunchAction(JTextField portField) {
        this.portField = portField;
        isClient = false;
    }

    /**
     * Creates a <code>LaunchAction</code> which should be used to launch a chat client.
     * @param portField The field that was used to obtain the port input.
     * @param hostnameField The field that was used to obtain the hostname input.
     * @param nicknameField The field that was used to obtain the nickname input.
     */
    public LaunchAction(JTextField portField, JTextField hostnameField, JTextField nicknameField) {
        this.portField = portField;
        this.hostnameField = hostnameField;
        this.nicknameField = nicknameField;
        isClient = true;
    }

    /**
     * Validates the input and starts the main application.
     * @param e The event which triggered this action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Reads and validates the port, which is common to both client and server
        int portInt;
        if (portField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter values in the fields.");
            return;
        }
        //Checks that the port is an integer
        try {
            portInt = Integer.parseInt(portField.getText());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "The port must be an integer.");
            portField.setText("14001");
            return;
        }
        //Checks if the port is within the valid port range
        if (portInt < 0 | portInt > 65535) {
            JOptionPane.showMessageDialog(null, "Port number must be between 0 and 65535.");
            portField.setText("14001");
            return;
        }
        if (isClient) {
            //Checks that the hostname and nickname fields are not blank
            if (hostnameField.getText().equals("") || nicknameField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter values in the fields.");
                return;
            }
            if (nicknameField.getText().contains("{") || nicknameField.getText().contains("}")) {
                JOptionPane.showMessageDialog(null, "Nickname may not contain '{' or '}'");
                return;
            }

            /* Sets the value of the static name field for the client's SendMessageAction so that the nickname is
             * associated with outgoing messages from the ChatClientGUI.
             */
            SendMessageAction.name = nicknameField.getText();

            ChatClient client = new ChatClient(portInt,
                    hostnameField.getText(),
                    new ClientIO(true),
                    nicknameField.getText());

            //Hides the launch window by getting the topLevelAncestor of the component which triggered this action
            ((JComponent) e.getSource()).getTopLevelAncestor().setVisible(false);

            client.start();
        }
        else {
            /* Sets the value of the static port field for the ChatServerGUI so that it can be displayed at the top of
             * the interface.
             */
            ChatServerGUI.port = portInt;
            ChatServer server = new ChatServer(portInt, new ServerIO(true));

            //Hides the launch window by getting the topLevelAncestor of the component which triggered this action
            ((JComponent) e.getSource()).getTopLevelAncestor().setVisible(false);

            server.start();
        }
    }
}
