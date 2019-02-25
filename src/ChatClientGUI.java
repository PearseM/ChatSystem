import javax.swing.*;
import java.awt.*;

public class ChatClientGUI implements GUI {
    private JTextField messageInput;
    private JPanel chatPane;
    private JPanel messagesContainer;
    private JScrollPane scrollPane;
    private ChatClient client;
    private int messageRow;

    /**
     * Creates a new GUI for the client.
     */
    public ChatClientGUI() {
        //Creates the main window
        JFrame frame = new JFrame("Chatting System");
        //Creates the text field for user message input
        messageInput = new JTextField();
        messageInput.addActionListener(new SendMessageAction(this));
        messageInput.setPreferredSize(new Dimension(500, 40));
        messageInput.setMaximumSize(messageInput.getPreferredSize());
        messageInput.setMinimumSize(new Dimension(100, 40));
        messageInput.setFont(new Font("basicFont", Font.PLAIN, 22));

        //Creates the messages container in which all of the MessagePanel objects are placed
        messagesContainer = new JPanel();
        messagesContainer.setLayout(new GridBagLayout());
        messageRow = 0;

        //Creates the scroll pane which contains the messageContainer, such that the user can scroll to see messages
        scrollPane = new JScrollPane(messagesContainer,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //Speeds up scrolling

        //Creates the bottom bar which houses the message input
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.add(Box.createRigidArea(new Dimension(100, -1)));
        bottomBar.add(messageInput);
        bottomBar.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        bottomBar.setBackground(Color.LIGHT_GRAY);
        bottomBar.setPreferredSize(new Dimension(-1, 60));

        //Creates the chat pane which is the main container of the chat window
        chatPane = new JPanel();
        chatPane.setLayout(new BorderLayout());
        chatPane.setPreferredSize(new Dimension(800, 600));
        chatPane.setMinimumSize(new Dimension (400, 300));
        chatPane.add(scrollPane, BorderLayout.CENTER);
        chatPane.add(bottomBar, BorderLayout.PAGE_END);

        //Adds everything to the main frame
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
     *              <li>0 = Left</li>
     *              <li>1 = Right</li>
     *             </ul>
     */
    public void generateMessage(Message message, int side) {
        MessagePanel mp = new MessagePanel();
        mp.initialiseMessage(message);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(10, 0, 10, 0);
        if (side==0) {
            constraints.gridx = 0;
        }
        else if (side==1) {
            constraints.gridx = 1;
        }
        constraints.gridy = messageRow++;
        messagesContainer.add(mp, constraints);
        chatPane.revalidate();
        scrollPaneToBottom();
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

