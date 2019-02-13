public class ClientInputThread extends Thread {
    private ChatClient client;

    public ClientInputThread(ChatClient client) {
        this.client = client;
    }

    public void run() {
        while (true) {
            client.getServerThread().sendMessage(client.getIO().getMessage(client.getName()));
        }
    }
}
