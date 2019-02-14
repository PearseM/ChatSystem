import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;

public class ServerUserThread extends Thread {
    private Socket socket;
    private ChatServer server;

    public ServerUserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Sends the message to all clients and to the server's output.
     * @param message The message to be sent.
     */
    protected void addMessage(Message message) {
        server.getIO().write(message);
        for (ServerUserThread client:
                server.getClients()) {
            client.send(message);
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String message = inReader.readLine();
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
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    server.getIO().error("InterruptedException occurred when trying to sleep.");
                }
            }
        }
        catch (IOException e) {
            server.getIO().error("IOException occurred when trying to read from input stream.");
        }
    }

    protected void send(Message message) {
        try {
            PrintWriter outWriter = new PrintWriter(socket.getOutputStream(), true);
            outWriter.println(message.toTransportString());
        }
        catch (IOException e) {
            server.getIO().error("IOException occurred when trying to write to output stream.");
        }
    }
}
