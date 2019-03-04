import javax.swing.*;

public interface GUI {

    /**
     * Creates a dialog which displays the error message.
     * @param errorMessage The error message to display.
     */
    static void writeError(String errorMessage) {
        JOptionPane optionPane = new JOptionPane(errorMessage, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Creates a dialog which displays the information.
     * @param information The error message to display.
     */
    static void writeInfo(String information) {
        JOptionPane optionPane = new JOptionPane(information, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Information");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    /**
     * Prompts the user to enter text.
     * @param message Instructions to the user.
     * @return The text which the user has input.
     */
    String promptUserForInput(String message);

}
