import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Thread {
    private Socket socket;
    private ClientIO inputOutput;
    private ClientServerThread serverThread;
    private final String name;
    private int port;
    private String hostname;

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
        this.port = port;
        this.hostname = hostname;
    }

    @Override
    public void run() {
        if (!connectToSocket(hostname, port)) {
            inputOutput.error("Could not connect to server. Please try running the program again.");
            System.exit(1);
        }
        //Adds a shutdown hook so that the socket will get closed safely when the program is exited.
        Runtime.getRuntime().addShutdownHook(new ShutDownHook(socket, inputOutput));

        inputOutput.write("Connected to server.");
        serverThread = new ClientServerThread(socket, this);

        //Pass this server thread to the GUI SendMessageAction so that messages can be sent to the server upon input
        SendMessageAction.serverThread = serverThread;

        //Pass this ChatClient object to the input thread so that it can access the input/output and the server thread
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
     * Gets the thread which is maintaining the connection to the server.
     * @return The thread which is maintaining the connection to the server.
     */
    protected ClientServerThread getServerThread() {
        return serverThread;
    }

    /**
     * Gets the instance of IO which is being used to handle inputs and outputs.
     * @return The instance of IO which is being used to handle inputs and outputs.
     */
    protected ClientIO getIO() {
        return inputOutput;
    }

    /**
     * Gets the user's chosen name.
     * @return The user's chosen name.
     */
    protected String getUserName() {
        return name;
    }

    public static void main(String[] args) {
        boolean useGUI = false;

        //Sets the default port and hostname
        int port = 14001;
        String hostname = "localhost";

        if (args.length > 0) {
            //Loops through all of the command line arguments
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-ccp":
                        //Sets the port to equal the argument following "-ccp", if it exists.
                        if (args.length > i+1) {
                            try {
                                port = Integer.parseInt(args[i+1]);
                            }
                            catch (NumberFormatException e) {
                                System.out.println(InputOutput.COLOUR_RED +
                                        "The argument following the flag \"-ccp\" should be an integer." +
                                        InputOutput.COLOUR_RESET);
                                System.out.println("Using default port.");
                            }
                            //Skips the argument which follows the flag.
                            i++;
                        }
                        else {
                            System.out.println(InputOutput.COLOUR_RED +
                                    "Expected argument after \"" + args[i] + "\"." +
                                    InputOutput.COLOUR_RESET);
                            System.out.println("Using default port.");
                        }
                        break;
                    case "-cca":
                        //Sets the hostname to equal the argument following "-cca", if it exists.
                        if (args.length > i+1) {
                            hostname = args[i+1];
                            //Skips the argument which follows the flag.
                            i++;
                        }
                        else {
                            System.out.println(InputOutput.COLOUR_RED +
                                    "Expected argument after \"" + args[i] + "\"." +
                                    InputOutput.COLOUR_RESET);
                        }
                        break;
                    case "-gui":
                        useGUI = true;
                        break;
                    default:
                        System.out.println(InputOutput.COLOUR_RED +
                                "Flag \"" + args[i] + "\" not recognised." +
                                InputOutput.COLOUR_RESET);
                }
            }
        }
        if (useGUI) {
            /* Instantiates final port and hostname constants to pass to the gui launcher so that potential changes to
             * the "port" and "hostname" variables won't be reflected in the GUI when it is eventually run on the Event
             * Dispatch Thread.
             */
            final int PORT_FINAL = port;
            final String HOSTNAME_FINAL = hostname;
            SwingUtilities.invokeLater(() -> ChatClientGUI.launchGUI(PORT_FINAL, HOSTNAME_FINAL));
        }
        else {
            ClientIO inputOutput = new ClientIO(false);
            //Sets the default nickname to be anonymous, if none is chosen.
            String name = "Anonymous";
            try {
                String inputName = inputOutput.prompt("Please enter a nickname:");
                if (inputName != null) {
                    name = inputName;
                }
            } catch (ExitException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            ChatClient client = new ChatClient(port, hostname, inputOutput, name);
            client.start();
        }
    }
}
