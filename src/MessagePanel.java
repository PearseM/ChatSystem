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
        setLayout(new BorderLayout());

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
        messageContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageContent.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        //Calculates the necessary size of the message box
        int desiredWidth = messageContent.getPreferredSize().width + 20;
        System.out.println(desiredWidth);
        int desiredHeight = messageContent.getPreferredSize().height + 20;
        System.out.println(desiredHeight);
        int calculateHeight = (desiredWidth < 290) ? desiredHeight : (((desiredWidth/290)+1) * 18) + 230;
        int outputHeight = (calculateHeight < 140) ? 140 : calculateHeight;

        //Sets the preferred width to be the desired width if it less than 290, or 290 otherwise.
        setPreferredSize(new Dimension(((desiredWidth < 290) ? desiredWidth : 290),
                outputHeight));
        setMaximumSize(new Dimension(290, Integer.MAX_VALUE));

        add(messageContent, BorderLayout.CENTER);
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

