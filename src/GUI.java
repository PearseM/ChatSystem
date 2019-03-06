import javax.swing.*;

public abstract class GUI {

    /**
     * Creates a dialog which displays the error message.
     * @param errorMessage The error message to display.
     */
    public static synchronized void writeError(String errorMessage) {
        JOptionPane optionPane = new JOptionPane(errorMessage, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Creates a dialog which displays the information.
     * @param information The error message to display.
     */
    public static synchronized void writeInfo(String information) {
        JOptionPane optionPane = new JOptionPane(information, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Information");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Creates a dialog which waits for a user to input text.
     * @param message Instructions telling the user what they should input.
     * @return The user's input.
     */
    public static synchronized String promptUserForInput(String message) {
        return JOptionPane.showInputDialog(message);
    }
}
