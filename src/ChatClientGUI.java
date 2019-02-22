import javax.swing.*;
import java.awt.*;

public class ChatClientGUI implements GUI {
    private JTextField messageInput;
    private JPanel chatPane;
    private JPanel messagesContainer;
    private JPanel bottomBar;
    private JScrollPane scrollPane;
    private ChatClient client;

    /**
     * Creates a new GUI for the client.
     */
    public ChatClientGUI() {
        JFrame frame = new JFrame("Chatting System");
        messageInput = new JTextField();
        messageInput.addActionListener(new SendMessageAction(this));
        chatPane = new JPanel();
        messagesContainer = new JPanel();
        messagesContainer.setLayout(new BoxLayout(messagesContainer, BoxLayout.Y_AXIS));
        bottomBar = new JPanel();
        scrollPane = new JScrollPane(messagesContainer,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //Speeds up scrolling
        messageInput.setPreferredSize(new Dimension(500, 40));
        messageInput.setMaximumSize(messageInput.getPreferredSize());
        messageInput.setMinimumSize(new Dimension(100, 40));
        messageInput.setFont(new Font("basicFont", Font.PLAIN, 22));
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.add(Box.createRigidArea(new Dimension(100, -1)));
        bottomBar.add(messageInput);
        bottomBar.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        bottomBar.setBackground(Color.LIGHT_GRAY);
        bottomBar.setPreferredSize(new Dimension(-1, 60));
        chatPane.setLayout(new BorderLayout());
        chatPane.setPreferredSize(new Dimension(800, 600));
        chatPane.setMinimumSize(new Dimension (400, 300));
        chatPane.add(scrollPane, BorderLayout.CENTER);
        chatPane.add(bottomBar, BorderLayout.PAGE_END);
        frame.setContentPane(chatPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Outputs a message to the screen.
     * @param message The message to be output.
     * @param side The side to draw the message on:
     *             <ul>
     *              <li>1 = Left</li>
     *              <li>0 = Right</li>
     *             </ul>
     */
    public void generateMessage(Message message, int side) {
        MessagePanel mp = new MessagePanel();
        mp.initialiseMessage(message);
        if (side==1) {
            mp.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        else if (side==0) {
            mp.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }
        messagesContainer.add(mp);
    }

    //TODO Review whether the entire chatPane needs to be returned
    /**
     * @return The main chat pane container of the GUI.
     */
    public JPanel getChatPane() {
        return chatPane;
    }

    /**
     * @return The main client object.
     */
    public ChatClient getClient() {
        return client;
    }

    //TODO review whether the entire TextField needs to returned
    /**
     * @return The text field which the user
     */
    public JTextField getMessageInput() {
        return messageInput;
    }

    /**
     * Creates a dialog which waits for a user to input text.
     * @param message Instructions telling the user what they should input.
     * @return The user's input.
     */
    public String promptUserForInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    /**
     * Scrolls the messages pane to the bottom.
     */
    public void scrollPaneToBottom() {
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    /**
     * @param client The main client object.
     */
    public void setClient(ChatClient client) {
        this.client = client;
    }

    /**
     * Creates a dialog which displays the error message.
     * @param errorMessage The error message to display.
     */
    public void writeError(String errorMessage) {
        JOptionPane optionPane = new JOptionPane(errorMessage, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Creates a dialog which displays the information.
     * @param information The error message to display.
     */
    public void writeInfo(String information) {
        JOptionPane optionPane = new JOptionPane(information, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Information");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}

