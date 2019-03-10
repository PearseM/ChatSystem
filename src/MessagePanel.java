import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {

    /**
     * Creates a message 'bubble' for use in the <code>ChatClientGUI</code>.
     * @param message The message object to be displayed in the 'bubble'.
     */
    public MessagePanel(Message message) {
        super();
        setLayout(new BorderLayout());
        String cssProperties = "color: white; font-family: Arial;";

        //Defines the HTML markup of the message
        String messageText =
                "<html>" +
                    "<h4 style=\"" + cssProperties + "\">" + message.getName() + "</h4>" +
                    "<p style=\"" + cssProperties + "\">" + message.getContent() + "</p>" +
                    "<h5 style=\"" + cssProperties + "\"><em>" + message.getDateString() + "</em></h5>" +
                "</html>";

        JEditorPane messageContent = new JEditorPane("text/html", messageText);
        messageContent.setOpaque(false);
        messageContent.setPreferredSize(new Dimension(180, messageContent.getPreferredSize().height));
        messageContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageContent.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(messageContent, BorderLayout.CENTER);
        setPreferredSize(new Dimension(200, (messageContent.getPreferredSize().height + 20)));
        setMaximumSize(getPreferredSize());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 50);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics roundedRectangle = g.create();
        roundedRectangle.setColor(Color.red);
        roundedRectangle.fillRoundRect(0, 0, 200, (int) getPreferredSize().getHeight(), 40, 40);
    }
}

