public class ExitException extends Exception {

    /**
     * Generates a new ExitException, to be used when the user has chosen to exit the program.
     * @param reason The reason why the exception was thrown.
     */
    public ExitException(String reason) {
        super(reason);
    }

}
