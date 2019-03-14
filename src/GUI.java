import javax.swing.*;
import java.awt.*;

/**
 * Defines some methods which are universal to both server and client GUIs.
 */
public abstract class GUI {

    /**
     * Creates a dialog which displays the error message.
     * @param errorMessage The error message to display.
     */
    protected static synchronized void writeError(String errorMessage) {
        JOptionPane optionPane = new JOptionPane(errorMessage, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Creates a dialog which displays information.
     * @param information The information to display.
     */
    protected static synchronized void writeInfo(String information) {
        JOptionPane optionPane = new JOptionPane(information, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Information");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Creates a title JLabel.
     * @param titleString The desired text for the title.
     * @return The title as a JLabel.
     */
    protected static JLabel generateTitle(String titleString) {
        JLabel title = new JLabel(titleString);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        return title;
    }

    /**
     * Displays a JFrame in the format of a launch screen menu.
     * @param frame The frame to display.
     * @param title The title to display near the top of the window.
     * @param inputsPanel The grid of input labels and fields which will be displayed in the middle of the window.
     * @param connectButton The button which will be displayed under the inputsPanel.
     * @param buttonBottomSpacing The amount by which the button should be spaced from the bottom of the window.
     */
    protected static void outputFrame(JFrame frame, JLabel title, JPanel inputsPanel, JButton connectButton,
                                      int buttonBottomSpacing) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(title);
        container.add(new JSeparator(SwingConstants.HORIZONTAL));
        container.add(inputsPanel);
        container.add(Box.createRigidArea(new Dimension(0, 25)));
        container.add(connectButton);
        container.add(Box.createRigidArea(new Dimension(0, buttonBottomSpacing)));

        frame.setContentPane(container);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
