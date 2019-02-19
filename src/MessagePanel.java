import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {

    public void initialiseMessage(Message message) {
        String messageText =
                "<html>" +
                    "<h4 style=\"color: white; font-family: Arial;\">" + message.getName() + "</h4>" +
                    "<p style=\"color: white; font-family: Arial;\">" + message.getContent() + "</p>" +
                    "<h5 style=\"color: white; font-family: Arial;\"><em>" + message.getDateString() + "</em></h5>" +
                "</html>";
        JEditorPane messageContent = new JEditorPane("text/html", messageText);
        messageContent.setOpaque(false);
        messageContent.setMaximumSize(new Dimension(180, 200));
        messageContent.setPreferredSize(new Dimension(180, 200));
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
        return new Dimension(200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics roundedRectangle = g;
        roundedRectangle.setColor(Color.red);
        roundedRectangle.fillRoundRect(0, 0, 200, 200, 20, 20);
    }
}

