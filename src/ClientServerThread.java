import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;

public class ClientServerThread extends Thread {
    private Socket socket;
    private ChatClient client;

    public ClientServerThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
    }

    protected void sendMessage(Message message) {
        try {
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketWriter.println(message.toTransportString());
        }
        catch (IOException e) {
            client.getIO().error("IOException occurred when trying to send message.");
        }
    }

    @Override
    public void run() {
        while (true) {
            String input;
            try {
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                input = socketReader.readLine();
                if (input!=null) {
                    if (!input.equals("")) {
                        try {
                            client.getIO().write(Message.parse(input));
                        }
                        catch (ParseException e) {
                            client.getIO().error("Could not parse incoming message properly:\n" + input);
                        }
                    }
                }
            } catch (IOException e) {
                client.getIO().error("IOException occurred when trying to read from socket.");
            }
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                client.getIO().error("InterruptedException occurred when trying to read from server.");
            }
        }
    }
}
