public interface GUI {

    /**
     * Outputs an error message.
     * @param errorMessage The message to output.
     */
    void writeError(String errorMessage);

    /**
     * Outputs information to the user.
     * @param information The message to output.
     */
    void writeInfo(String information);

    /**
     * Prompts the user to enter text.
     * @param message Instructions to the user.
     * @return The text which the user has input.
     */
    String promptUserForInput(String message);

}
