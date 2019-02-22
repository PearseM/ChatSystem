public class ServerReadConsoleThread extends Thread {
    private InputOutput inputOutput;

    /**
     * Creates a thread which listens for commands from the console e.g. "EXIT".
     * @param inputOutput The IO instance which is being used to handle inputs and outputs.
     */
    public ServerReadConsoleThread(InputOutput inputOutput) {
        this.inputOutput = inputOutput;
    }

    @Override
    public void run() {
        while (true) {
            try {
                inputOutput.readConsole();
            }
            catch (ExitException e) {
                inputOutput.write(e.getMessage());
                System.exit(0);
            }
        }
    }
}
