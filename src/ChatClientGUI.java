import javax.swing.*;
import java.awt.*;

public class ChatClientGUI {
    private JTextField messageInput;
    private JPanel chatPane;
    private JPanel messagesContainer;
    private JPanel bottomBar;

    public ChatClientGUI() {
        JFrame frame = new JFrame("Chatting System");
        messageInput = new JTextField();
        chatPane = new JPanel();
        messagesContainer = new JPanel();
        messagesContainer.setLayout(new BoxLayout(messagesContainer, BoxLayout.Y_AXIS));
        bottomBar = new JPanel();
        JScrollPane scrollPane = new JScrollPane(messagesContainer,
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
        bottomBar.setBackground(Color.gray);
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

    public void generateMessage(Message message, int side) {
        MessagePanel mp = new MessagePanel();
        mp.initialiseMessage(message);
        if (side==0) {
            mp.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        else if (side==1) {
            mp.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }
        messagesContainer.add(mp);
    }

    public JPanel getChatPane() {
        return chatPane;
    }

    /*
    @Override
    public void run() {
        JFrame frame = new JFrame("ChatClientView");
        ChatClientGUIThread view = new ChatClientGUIThread();
        frame.setContentPane(view.getChatPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    */
}

