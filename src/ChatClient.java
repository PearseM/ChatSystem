import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private Socket socket;
    private ClientIO inputOutput;
    private ClientServerThread serverThread;
    private final String name;

    /**
     * Constructs a ChatClient object which provides the basis of the messaging client.
     * @param port The desired port to connect to
     * @param hostname The desired host to connect to
     * @param inputOutput The instance of IO being used to handle inputs and outputs
     * @param name The user's chosen name
     */
    public ChatClient(int port, String hostname, ClientIO inputOutput, String name) {
        this.inputOutput = inputOutput;
        this.name = name;
        if (!connectToSocket(hostname, port)) {
            inputOutput.error("Could not connect to server. Please try running the program again.");
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(new ShutDownHook(socket, inputOutput));
        inputOutput.write("Connected to server.");
        serverThread = new ClientServerThread(socket, this);
        //Pass this server thread to the GUI SendMessageAction so that messages can be sent to the server upon input
        SendMessageAction.serverThread = serverThread;
        ClientInputThread inputThread = new ClientInputThread(this);
        serverThread.start();
        inputThread.start();
    }

    /**
     * Attempts to connect to the specified hostname and port. If the hostname does not work, the method will retry with
     * hostname "localhost".
     * @param hostname The desired address to connect to.
     * @param port The desired port to connect to.
     * @return True if a socket was created and false otherwise.
     */
    private boolean connectToSocket(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
        }
        catch (UnknownHostException e) {
            if (hostname.equals("localhost")){
                inputOutput.error("Could not connect to server. Please try running the program again.");
                System.exit(1);
            }
            else {
                inputOutput.error("Could not connect to address " + hostname + ". Attempting to use localhost...");
                connectToSocket("localhost", port);
            }
        }
        catch (IOException e) {
            if (hostname.equals("localhost")) {
                inputOutput.error("Could not connect to server. Please try running the program again.");
                System.exit(1);
            }
            else {
                inputOutput.error("IOException occurred when trying to connect. Attempting to use localhost...");
                connectToSocket("localhost", port);
            }
        }
        return socket!=null;
    }

    /**
     * @return The thread which is maintaining the connection to the server.
     */
    protected ClientServerThread getServerThread() {
        return serverThread;
    }

    /**
     * @return The instance of IO which is being used to handle inputs and outputs.
     */
    protected ClientIO getIO() {
        return inputOutput;
    }

    /**
     * @return The user's chosen name.
     */
    protected String getName() {
        return name;
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
                        //Starts the GUI
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
            inputOutput.setGUI(gui);
            port = inputOutput.promptForPort();
        }
        String name = "";
        try {
            name = inputOutput.prompt("Please enter a nickname:");
        }
        catch (ExitException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        ChatClient client = new ChatClient(port, hostname, inputOutput, name);
        //Pass the newly created client object to the GUI, if the program is being used in GUI mode.
        if (useGUI) {
            gui.setClient(client);
        }
    }
}
