import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class MessagePanel extends JPanel {

    /**
     * Creates a message 'bubble' for use in the <code>ChatClientGUI</code>.
     * @param message The message object to be displayed in the 'bubble'.
     */
    public MessagePanel(Message message) {
        //super();
        setLayout(new BorderLayout());
        String cssProperties = "color: white; font-family: Arial; word-break: break-all;";

        //Defines the HTML markup of the message
        String messageText =
                "<html>" +
                    "<h4 style=\"" + cssProperties + "\">" + message.getName() + "</h4>" +
                    "<p style=\"" + cssProperties + "\">" + message.getContent() + "</p>" +
                    "<h5 style=\"" + cssProperties + "\"><em>" + message.getDateString() + "</em></h5>" +
                "</html>";

        /*JEditorPane messageContent = new JEditorPane("text/html", messageText);
        messageContent.setOpaque(false);
        messageContent.setEditable(false);
        messageContent.setPreferredSize(new Dimension(180, messageContent.getPreferredSize().height));
        messageContent.setMaximumSize(new Dimension(180, messageContent.getPreferredSize().height));
        messageContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageContent.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(messageContent, BorderLayout.CENTER);
        */
        JTextPane messageContent = new JTextPane();
        messageContent.setOpaque(false);
        messageContent.setEditable(false);

        StyledDocument messageDocument = messageContent.getStyledDocument();

        Style nameStyle = messageContent.addStyle("Name style", null);
        StyleConstants.setForeground(nameStyle, Color.WHITE);
        StyleConstants.setBold(nameStyle, true);
        StyleConstants.setFontSize(nameStyle, 14);

        Style contentStyle = messageContent.addStyle("Content style", null);
        StyleConstants.setForeground(contentStyle, Color.WHITE);
        StyleConstants.setFontSize(contentStyle, 18);

        Style dateStyle = messageContent.addStyle("Date style", null);
        StyleConstants.setForeground(dateStyle, Color.WHITE);
        StyleConstants.setItalic(dateStyle, true);
        StyleConstants.setFontSize(dateStyle, 14);

        try {
            messageDocument.insertString(messageDocument.getLength(), message.getName() + "\n\n", nameStyle);
            messageDocument.insertString(messageDocument.getLength(), message.getContent() + "\n\n", contentStyle);
            messageDocument.insertString(messageDocument.getLength(), message.getDateString(), dateStyle);
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
        //messageContent.setPreferredSize(new Dimension(300, messageContent.getPreferredSize().height));
        //messageContent.setMaximumSize(messageContent.getPreferredSize());
        messageContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageContent.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        add(messageContent, BorderLayout.CENTER);

        setPreferredSize(new Dimension((messageContent.getPreferredSize().width + 20),
                (messageContent.getPreferredSize().height + 20)));
        setMaximumSize(getPreferredSize());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }


    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
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
        roundedRectangle.fillRoundRect(0, 0, (int) getPreferredSize().getWidth(),
                (int) getPreferredSize().getHeight(), 40, 40);
    }
}

