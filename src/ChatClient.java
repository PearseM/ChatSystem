import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private Socket socket;
    private ClientIO inputOutput;
    private ClientInputThread inputThread;
    private ClientServerThread serverThread;
    private final String name;

    public ChatClient(int port, String hostname, ClientIO inputOutput, String name) {
        this.inputOutput = inputOutput;
        this.name = name;
        connectToSocket(hostname, port);
        inputOutput.write("Connected to server.");
    }

    /**
     * Attempts to connect to the specified hostname and port. If the hostname does not work, the method will retry with
     * hostname "localhost".
     * @param hostname The desired address to connect to.
     * @param port The desired port to connect to.
     */
    private void connectToSocket(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
        }
        catch (UnknownHostException e) {
            if (hostname.equals("localhost")){
                inputOutput.error("Could not connect to server.");
                //TODO: exit the program
            }
            inputOutput.error("Could not connect to address " + hostname + ". Attempting to use localhost...");
            connectToSocket("localhost", port);
        }
        catch (IOException e) {
            inputOutput.error("IOException occurred when trying to connect.");
        }
    }

    public ClientServerThread getServerThread() {
        return serverThread;
    }

    public void setServerThread(ClientServerThread serverThread) {
        this.serverThread = serverThread;
    }

    protected ClientIO getIO() {
        return inputOutput;
    }

    protected String getName() {
        return name;
    }

    protected void initialize() {
        serverThread = new ClientServerThread(socket, this);
        inputThread = new ClientInputThread(this);
        serverThread.start();
        inputThread.start();
    }

    public static void main(String[] args) {
        ChatClientGUI gui = null;
        boolean useGUI = false;
        int port = 14001;
        String hostname = "localhost";
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-ccp":
                        if (args.length >= i+1) {
                            try {
                                port = Integer.parseInt(args[i+1]);
                            } catch (NumberFormatException e) {
                                System.out.println("The argument following the flag \"-ccp\" should be an integer.");
                                System.out.println("Using default port.");
                            }
                            i++; //Skips the argument which follows the flag.
                        }
                        else {
                            System.out.println("Expected argument after \"" + args[i] + "\".");
                        }
                        break;
                    case "-cca":
                        if (args.length >= i+1) {
                            hostname = args[i+1];
                            i++; //Skips the argument which follows the flag.
                        }
                        else {
                            System.out.println("Expected argument after \"" + args[i] + "\".");
                        }
                        break;
                    case "-gui":
                        useGUI = true;
                        gui = new ChatClientGUI();
                        break;
                    default:
                        System.out.println("Flag \"" + args[i] + "\"not recognised.");
                        System.out.println("Using default port and hostname.");
                }
            }
        }
        ClientIO inputOutput = new ClientIO(useGUI);
        if (useGUI) {
            port = inputOutput.promptForPort();
            inputOutput.setGui(gui);
        }
        String name = inputOutput.prompt("Please enter a nickname:");
        ChatClient client = new ChatClient(port, hostname, inputOutput, name);
        client.initialize();
    }
}
