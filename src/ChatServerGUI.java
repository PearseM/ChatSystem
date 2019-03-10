import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class ChatServerGUI {
    private DefaultListModel<Message> messageListModel;
    private DefaultListModel<ServerUserThread> clientListModel;
    protected static int port = 14001;

    /**
     * Creates a GUI for the server.
     */
    public ChatServerGUI() {
        //Creates the main window
        JFrame frame = new JFrame("Chatting System Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel portInfo = new JLabel();
        portInfo.setText("Port: " + port);
        JLabel ipInfo = new JLabel();
        try {
            //Pings AWS to find out the server's external ip address(s)
            URL ipCheckerURL = new URL("http://checkip.amazonaws.com");
            URLConnection ipCheckerConnection = ipCheckerURL.openConnection();
            //Times out if connection cannot be established within 2 seconds, and if nothing can be read within 3s.
            ipCheckerConnection.setConnectTimeout(2000);
            ipCheckerConnection.setReadTimeout(3000);
            BufferedReader br = new BufferedReader(new InputStreamReader(ipCheckerConnection.getInputStream()));
            String ipAddressFeed = br.readLine();
            //The first ip address is output to the user (sometimes multiple addresses are returned by AWS)
            ipInfo.setText("External IP Address: " + ipAddressFeed.split(",")[0]);
        }
        catch (SocketTimeoutException e) {
            ipInfo.setText("External IP Address: Could not be determined");
        }
        catch (IOException e) {
            GUI.writeError("IOException occurred while trying to determine external ip address.");
        }

        //Creates the panel which displays the port and ip address information at the top of the window
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        portInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(portInfo);
        infoPanel.add(ipInfo);

        JTabbedPane tabbedPane = new JTabbedPane();

        //Sets up the list which displays all messages
        messageListModel = new DefaultListModel<>();
        JList messagesTextList = new JList<>(messageListModel);
        messagesTextList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messagesTextList.setLayoutOrientation(JList.VERTICAL);
        messagesTextList.setVisibleRowCount(-1);

        //Makes the messages list scrollable
        JScrollPane messagesScrollPane = new JScrollPane(messagesTextList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        messagesScrollPane.setPreferredSize(new Dimension(600, 400));

        //Sets up the list which displays all of the clients
        clientListModel = new DefaultListModel<>();
        JList clientsList = new JList<>(clientListModel);
        messagesTextList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        messagesTextList.setLayoutOrientation(JList.VERTICAL);
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

    /**
     * Displays the server launch menu and populates the port field with the specified value.
     * @param port The desired default value to display in the port field.
     */
    protected static synchronized void launchGUI(int port) {
        //Creates the menu window
        JFrame frame = new JFrame("Chatting System Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = GUI.generateTitle("Open a chat server");

        Font textFieldFont = new Font("Arial", Font.PLAIN, 20);

        //Creates the port label and input field
        JLabel portLabel = new JLabel("Port:");
        portLabel.setFont(textFieldFont);

        JTextField portField = new JTextField(5);
        portField.setFont(textFieldFont);
        portField.setText(Integer.toString(port));
        portField.addActionListener(new LaunchAction(portField));

        //Creates the grid panel which lays out the label and input field
        JPanel inputsPanel = new JPanel();
        inputsPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));
        inputsPanel.setLayout(new GridLayout(1, 2));
        inputsPanel.add(portLabel);
        inputsPanel.add(portField);

        JButton connectButton = new JButton("Launch");
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.setBorder(BorderFactory.createEmptyBorder(25, 0, 330, 0));
        connectButton.addActionListener(new LaunchAction(portField));

        GUI.outputFrame(frame, title, inputsPanel, connectButton);

    }

    protected synchronized void addMessage(Message message) {
        messageListModel.addElement(message);
    }

    protected synchronized void addClient(ServerUserThread client) {
        clientListModel.addElement(client);
    }

    protected synchronized void removeClient(ServerUserThread client) {
        clientListModel.removeElement(client);
    }
}
