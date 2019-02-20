import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendMessageAction extends AbstractAction {
    public static ClientServerThread serverThread;
    private ChatClientGUI gui;

    public SendMessageAction(ChatClientGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField input = gui.getMessageInput();
        Message message = new Message(input.getText(), gui.getClient().getName());
        serverThread.sendMessage(message);
        input.setText("");
    }
}
