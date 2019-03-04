import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {

    /**
     * Sets up the display properties of the new message panel.
     * @param message The message to print on the screen.
     */
    public void initialiseMessage(Message message) {
        String cssProperties = "color: white; font-family: Arial;";
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
        add(messageContent);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 200);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics roundedRectangle = g;
        roundedRectangle.setColor(Color.red);
        Dimension preferredSize = getPreferredSize();
        roundedRectangle.fillRoundRect(0, 0, (int) preferredSize.getWidth(), (int) preferredSize.getHeight(), 20, 20);
    }
}

