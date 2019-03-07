import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ChatServerGUI {
    private DefaultListModel<Message> messageListModel;
    private DefaultListModel<ServerUserThread> clientListModel;
    public static int port = 14001;

    public ChatServerGUI() {
        JFrame frame = new JFrame("Chatting System Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel portInfo = new JLabel();
        portInfo.setText("Port: " + port);
        JLabel ipInfo = new JLabel();
        try {
            URL ipChecker = new URL("http://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(ipChecker.openStream()));
            ipInfo.setText("External IP Address: " + br.readLine());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        portInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(portInfo);
        infoPanel.add(ipInfo);

        JTabbedPane tabbedPane = new JTabbedPane();

        messageListModel = new DefaultListModel<>();
        JList messagesTextList = new JList<>(messageListModel);
        messagesTextList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messagesTextList.setLayoutOrientation(JList.VERTICAL_WRAP);
        messagesTextList.setVisibleRowCount(-1);

        JScrollPane messagesScrollPane = new JScrollPane(messagesTextList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messagesScrollPane.setPreferredSize(new Dimension(600, 400));

        clientListModel = new DefaultListModel<>();
        JList clientsList = new JList<>(clientListModel);
        messagesTextList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messagesTextList.setLayoutOrientation(JList.VERTICAL_WRAP);
        messagesTextList.setVisibleRowCount(-1);

        JScrollPane clientsScrollPane = new JScrollPane(clientsList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        clientsScrollPane.setPreferredSize(new Dimension(600, 400));

        tabbedPane.addTab("Messages", messagesScrollPane);
        tabbedPane.addTab("Clients", clientsScrollPane);

        frame.add(infoPanel, BorderLayout.PAGE_START);
        frame.add(tabbedPane, BorderLayout.CENTER);

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

    public void addClient(ServerUserThread client) {
        clientListModel.addElement(client);
    }

    public void removeClient(ServerUserThread client) {
        clientListModel.removeElement(client);
    }
}
