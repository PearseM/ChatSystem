public class ClientInputThread extends Thread {
    private ChatClient client;

    /**
     * Constructs a new input thread to wait for the user to enter a message.
     * @param client Should be the client which created this thread.
     */
    public ClientInputThread(ChatClient client) {
        this.client = client;
    }

    /**
     * Waits for user input. If a message is input, this method will send it to the server and output it to this
     * client's output.
     */
    public void run() {
        while (true) {
            try {
                Message inputMessage = client.getIO().getMessage(client.getUserName());
                String messageContent = inputMessage.getContent();
                if (messageContent.contains("{") || messageContent.contains("}")) {
                    client.getIO().write("Message may not contain '{' or '}'!");
                }
                else {
                    client.getServerThread().sendMessage(inputMessage);
                }
            }
            catch (ExitException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }
}
