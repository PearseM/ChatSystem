public class ClientInputThread extends Thread {
    private ChatClient client;

    public ClientInputThread(ChatClient client) {
        this.client = client;
    }

    public void run() {
        while (true) {
            Message inputMessage = client.getIO().getMessage(client.getName());
            client.getServerThread().sendMessage(inputMessage);
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                client.getIO().error("InterruptedException occurred when trying to read messages from user.");
            }
        }
    }
}
