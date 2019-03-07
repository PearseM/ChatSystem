import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;

public class ServerUserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private int clientID;

    /**
     * Constructs a new thread to maintain a socket with a user.
     * @param socket The opened user socket.
     * @param server The server instance that created this thread.
     */
    public ServerUserThread(Socket socket, ChatServer server, int clientID) {
        this.socket = socket;
        this.server = server;
        this.clientID = clientID;
    }

    /**
     * Sends the message to all clients and to the server's output.
     * @param message The message to be sent.
     */
    protected void addMessage(Message message) {
        server.getIO().write(message);
        for (ServerUserThread client:
                server.getClients()) {
            if (client!=this) {
                client.send(message);
            }
        }
    }

    /**
     * Reads from the client stream. If a message is detected, addMessage is called.
     */
    @Override
    public void run() {
        try {
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!socket.isClosed()) {
                String message = inReader.readLine();/*
                try {
                    wait();
                }
                catch (InterruptedException e) {
                    server.getIO().error("InterruptedException occurred when trying to wait.");
                }*/
                if (message!=null) {
                    if (!message.equals("")) {
                        try {
                            addMessage(Message.parse(message));
                        }
                        catch (ParseException e) {
                            server.getIO().error("Error occurred when trying to parse incoming message:\n" + message);
                        }
                    }
                }
                else {
                    //If message is null, then the client has disconnected.
                    socket.close();
                    server.removeClient(this);
                    return; //Stop this thread from running
                }/*
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    server.getIO().error("InterruptedException occurred when trying to sleep.");
                }*/
            }
        }
        catch (IOException e) {
            server.getIO().error("IOException occurred when trying to read from input stream.");
        }
    }

    /**
     * Sends a message to the client.
     * @param message The message to be sent.
     */
    protected void send(Message message) {
        if (socket.isClosed()) {
            server.removeClient(this);
        }
        else {
            try {
                PrintWriter outWriter = new PrintWriter(socket.getOutputStream(), true);
                outWriter.println(message.toTransportString());
            } catch (IOException e) {
                server.getIO().error("IOException occurred when trying to write to output stream.");
            }
        }
    }


    @Override
    public String toString() {
        return "Port: " + socket.getPort() + " | Client ID: " + clientID;
    }
}
