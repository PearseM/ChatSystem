import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;

public class ServerUserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private String name;

    public ServerUserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = inReader.readLine();
            while(true) {
                String message = inReader.readLine();
                if (message!=null) {
                    if (!message.equals("")) {
                        try {
                            server.addMessage(Message.parse(message));
                        }
                        catch (ParseException e) {
                            server.getIO().error("Error occurred when trying to parse incoming message.");
                        }
                    }
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
            outWriter.println(message);
        }
        catch (IOException e) {
            server.getIO().error("IOException occurred when trying to write to output stream.");
        }
    }
}
