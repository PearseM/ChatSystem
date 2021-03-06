import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private ServerSocket serverSocket;
    private ServerIO inputOutput;
    private ArrayList<ServerUserThread> clients = new ArrayList<>();

    /**
     * Constructs a ChatServer object which is the basis of the server functionality.
     * @param port The port to listen for users on.
     * @param inputOutput The instance of IO being used to handle inputs and outputs.
     */
    public ChatServer(int port, ServerIO inputOutput) {
        this.inputOutput = inputOutput;
        try {
            serverSocket = new ServerSocket(port);
            //Adds a shutdown hook so that the socket is closed cleanly when the program is exited.
            Runtime.getRuntime().addShutdownHook(new ShutDownHook(serverSocket, inputOutput));
        }
        catch (IOException e) {
            inputOutput.error("IOException occurred when trying to listen on port " + port + ".");
        }
    }

    /**
     * @return A list of all of the clients that are currently connected.
     */
    public ArrayList<ServerUserThread> getClients() {
        return clients;
    }

    /**
     * @return The instance of IO being used to handle inputs and outputs.
     */
    protected ServerIO getIO() {
        return inputOutput;
    }

    /**
     * Waits for users to join the server.
     */
    private void initialize() {
        inputOutput.write("Server listening.");

        while (true) {
            try {
                Socket userSocket = serverSocket.accept();
                inputOutput.write("Accepted connection on " + serverSocket.getLocalPort() +
                        " ; " + userSocket.getPort());
                ServerUserThread thread = new ServerUserThread(userSocket, this);
                clients.add(thread);
                thread.start();
            }
            catch (IOException e) {
                inputOutput.error("IOException occurred when trying to accept connection.");
            }/*
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                inputOutput.error("InterruptedException occurred while accepting connections.");
            }*/
        }
    }

    public synchronized void removeClient(ServerUserThread client) {
        clients.remove(client);
        inputOutput.write("User disconnected.");
    }

    public static void main(String[] args) {
        boolean useGUI = false;
        int port = 14001;
        if (args.length > 0) {
            switch (args[0]) {
                case "-csp":
                    if (args.length >= 2) {
                        try {
                            port = Integer.parseInt(args[1]);
                        }
                        catch (NumberFormatException e) {
                            System.out.println("The argument following the flag \"-csp\" should be an integer.");
                            System.out.println("Using default port.");
                        }
                    }
                    else {
                        System.out.println("Expected argument after \"-csp\".");
                    }
                    break;
                case "-gui":
                    useGUI = true;
                    break;
                default:
                    System.out.println("Flag \"" + args[0] + "\"not recognised.");
                    System.out.println("Using default port and hostname.");
            }
        }
        ServerIO inputOutput = new ServerIO(useGUI);
        //Sets up a thread to wait for commands from the console
        ServerReadConsoleThread readThread = new ServerReadConsoleThread(inputOutput);
        readThread.start();
        if (useGUI) {
            port = inputOutput.promptForPort();
        }
        ChatServer server = new ChatServer(port, inputOutput);
        server.initialize();
    }
}
