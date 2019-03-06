import javax.swing.*;
import java.awt.event.ActionEvent;

public class LaunchClientAction extends AbstractAction {
    private JTextField portField;
    private JTextField hostnameField;
    private JTextField nicknameField;


    public LaunchClientAction(JTextField portField, JTextField hostnameField, JTextField nicknameField) {
        this.portField = portField;
        this.hostnameField = hostnameField;
        this.nicknameField = nicknameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int portInt;
        if (portField.getText().equals("")
                | hostnameField.getText().equals("")
                | nicknameField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter values in the fields.");
            return;
        }
        try {
            portInt = Integer.parseInt(portField.getText());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "The port must be an integer.");
            portField.setText("14001");
            return;
        }
        if (portInt < 0 | portInt > 65535) {
            JOptionPane.showMessageDialog(null, "Port number must be between 0 and 65535.");
            portField.setText("14001");
            return;
        }
        if (nicknameField.getText().contains("{") | nicknameField.getText().contains("}")) {
            JOptionPane.showMessageDialog(null, "Nickname may not contain '{' or '}'");
            return;
        }

        SendMessageAction.name = nicknameField.getText();

        ChatClient client = new ChatClient(portInt,
                hostnameField.getText(),
                new ClientIO(true),
                nicknameField.getText());
        ((JComponent) e.getSource()).getTopLevelAncestor().setVisible(false);
        client.start();
    }
}
