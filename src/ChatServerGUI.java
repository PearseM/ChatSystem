import javax.swing.*;
import java.awt.*;

public class ChatServerGUI {
    private DefaultListModel<Message> messageListModel;

    public ChatServerGUI() {
        JFrame frame = new JFrame("Chatting System Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        messageListModel = new DefaultListModel();
        JList messagesTextList = new JList(messageListModel);
        messagesTextList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messagesTextList.setLayoutOrientation(JList.VERTICAL_WRAP);
        messagesTextList.setVisibleRowCount(-1);

        JScrollPane messagesScrollPane = new JScrollPane(messagesTextList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messagesScrollPane.setPreferredSize(new Dimension(600, 400));
        JPanel clientsPanel = new JPanel();

        tabbedPane.addTab("Messages", messagesScrollPane);
        tabbedPane.addTab("Clients", clientsPanel);

        frame.add(tabbedPane);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static synchronized void launchGUI(int port) {
        JFrame frame = new JFrame("Chatting System Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Open a chat server");
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.PLAIN, 30));

        Font textFieldFont = new Font("Arial", Font.PLAIN, 20);

        JLabel portLabel = new JLabel("Port:");
        portLabel.setFont(textFieldFont);

        JTextField portField = new JTextField(5);
        portField.setFont(textFieldFont);
        portField.setText(Integer.toString(port));

        portField.addActionListener(new LaunchAction(portField));

        JPanel inputsPanel = new JPanel();
        inputsPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
        inputsPanel.setLayout(new GridLayout(1, 2));

        inputsPanel.add(portLabel);
        inputsPanel.add(portField);
        JButton connectButton = new JButton("Launch");
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.setBorder(BorderFactory.createEmptyBorder(25, 0, 200, 0));
        connectButton.addActionListener(new LaunchAction(portField));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(title);
        container.add(new JSeparator(SwingConstants.HORIZONTAL));
        container.add(inputsPanel);
        container.add(connectButton);

        frame.setContentPane(container);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void addMessage(Message message) {
        messageListModel.addElement(message);
    }
}
