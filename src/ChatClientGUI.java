import javax.swing.*;
import java.awt.*;

public class ChatClientGUI {
    private JPanel chatPane;
    private JPanel messagesContainer;
    private JScrollPane scrollPane;
    private int messageRow;

    /**
     * Creates a new GUI for the client.
     */
    public ChatClientGUI() {
        //Creates the main window
        JFrame frame = new JFrame("Chatting System");

        Font font = new Font("Arial", Font.PLAIN, 22);

        //Creates the text field for user message input
        JTextField messageInput = new JTextField();
        messageInput.addActionListener(new SendMessageAction(messageInput));
        messageInput.setPreferredSize(new Dimension(500, 40));
        messageInput.setMaximumSize(messageInput.getPreferredSize());
        messageInput.setMinimumSize(new Dimension(100, 40));
        messageInput.setFont(font);

        //Creates the messages container in which all of the MessagePanel objects are placed
        messagesContainer = new JPanel();
        messagesContainer.setLayout(new GridBagLayout());

        //Adds placeholder panels at the top to initialize the grid layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(200,10));
        messagesContainer.add(left, constraints);
        constraints.gridx = 1;
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(200,10));
        messagesContainer.add(right, constraints);
        messageRow = 1;
        messagesContainer.revalidate();

        //Creates the scroll pane which contains the messageContainer, such that the user can scroll to see messages
        scrollPane = new JScrollPane(messagesContainer,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Speeds up scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        //Creates the bottom bar which houses the message input and send button
        JPanel bottomBar = new JPanel();
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.setFont(font);
        sendMessageButton.addActionListener(new SendMessageAction(messageInput));
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.add(Box.createRigidArea(new Dimension(100, -1)));
        bottomBar.add(messageInput);
        bottomBar.add(sendMessageButton);
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

        //Adds everything to the main frame and displays it
        frame.setContentPane(chatPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
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
    protected synchronized void generateMessage(Message message, int side) {
        MessagePanel mp = new MessagePanel(message);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.fill = GridBagConstraints.NONE;
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
     * Creates a GUI launch window with the specified default port and hostname in their respective text fields
     * @param port The default port to be displayed
     * @param hostname The default port to be displayed
     */
    protected static void launchGUI(int port, String hostname) {
        //Creates the main window
        JFrame frame = new JFrame("Chatting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = GUI.generateTitle("Connect to a chat server");

        Font textFieldFont = new Font("Arial", Font.PLAIN, 20);

        //Generates the field labels
        JLabel portLabel = new JLabel("Port:");
        portLabel.setFont(textFieldFont);
        JLabel hostnameLabel = new JLabel("Hostname:");
        hostnameLabel.setFont(textFieldFont);
        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameLabel.setFont(textFieldFont);

        //Generates the input fields
        JTextField portField = new JTextField(5);
        portField.setFont(textFieldFont);
        portField.setText(Integer.toString(port));
        JTextField hostnameField = new JTextField(40);
        hostnameField.setFont(textFieldFont);
        hostnameField.setText(hostname);
        JTextField nicknameField = new JTextField(40);
        nicknameField.setFont(textFieldFont);

        portField.addActionListener(new LaunchAction(portField, hostnameField, nicknameField));
        hostnameField.addActionListener(new LaunchAction(portField, hostnameField, nicknameField));
        nicknameField.addActionListener(new LaunchAction(portField, hostnameField, nicknameField));

        //Creates the grid panel which contains all of the input fields and their labels
        JPanel inputsPanel = new JPanel();
        inputsPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
        inputsPanel.setLayout(new GridLayout(3, 2));

        inputsPanel.add(portLabel);
        inputsPanel.add(portField);
        inputsPanel.add(hostnameLabel);
        inputsPanel.add(hostnameField);
        inputsPanel.add(nicknameLabel);
        inputsPanel.add(nicknameField);

        JButton connectButton = new JButton("Connect");
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //connectButton.setBorder(BorderFactory.createEmptyBorder(25, 0, 200, 0));
        connectButton.addActionListener(new LaunchAction(portField, hostnameField, nicknameField));

        GUI.outputFrame(frame, title, inputsPanel, connectButton, 200);
    }

    /**
     * Scrolls the messages pane to the bottom.
     */
    protected void scrollPaneToBottom() {
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
    }
}

