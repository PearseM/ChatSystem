import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;

public class ClientServerThread extends Thread {
    private Socket socket;
    private ChatClient client;

    /**
     * Constructs a thread which is used to maintain a socket connection with the server.
     * @param socket The socket which has already been created between the client and the server.
     * @param client Should be the ChatClient which created this thread.
     */
    public ClientServerThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
    }

    /**
     * Sends a message to the server and prints the message to the client output.
     * @param message The message to be sent.
     */
    protected void sendMessage(Message message) {
        if (socket.isClosed()) {
            client.getIO().write("Exiting because connection to server has been lost.");
            System.exit(0);
        }
        else {
            try {
                PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
                //Sends the message to the server
                socketWriter.println(message.toTransportString());

                //Displays the message on this client
                client.getIO().write(message, true);
            }
            catch (IOException e) {
                client.getIO().error("IOException occurred when trying to send message.");
            }
        }
    }

    /**
     * Waits for messages from the server then writes them to the client output.
     */
    @Override
    public void run() {
        while (!socket.isClosed()) {
            String input;
            try {
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //This line blocks until a message has been received from the server
                input = socketReader.readLine();
                if (input!=null) {
                    if (!input.equals("")) {
                        try {
                            client.getIO().write(Message.parse(input), false);
                        }
                        catch (ParseException e) {
                            client.getIO().error("Could not parse incoming message properly:\n" + input);
                        }
                    }
                }
                //If the message is null, then the connection to the server has been lost
                else {
                    client.getIO().error("Exiting because connection to server has been lost.");
                    System.exit(0);
                }
            }
            catch (IOException e) {
                client.getIO().error("IOException occurred when trying to read from socket.");
                System.exit(0);
            }
        }
        client.getIO().error("Exiting because connection to server has been lost.");
        System.exit(0);
    }
}
