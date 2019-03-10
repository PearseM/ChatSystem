import java.io.Closeable;
import java.io.IOException;

public class ShutDownHook extends Thread {
    private Closeable socket;
    private InputOutput inputOutput;

    /**
     * Creates a shutdown hook thread to be called upon when the program shuts down.
     * @param socket The server socket which should be closed on shutdown.
     * @param inputOutput The IO instance being used to handle outputs.
     */
    public ShutDownHook(Closeable socket, InputOutput inputOutput) {
        this.socket = socket;
        this.inputOutput = inputOutput;
    }

    /**
     * Closes the socket.
     */
    public void run() {
        try {
            socket.close();
            System.out.println("Socket closed.");
        }
        catch (IOException e) {
            inputOutput.error("IOException occurred when trying to close the ServerSocket during shutdown.");
        }
    }
}
