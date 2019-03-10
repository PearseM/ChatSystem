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
    @Override
    public void run() {
        while (true) {
            try {
                //This line will block until a message is received
                Message inputMessage = client.getIO().getMessage(client.getUserName());

                /* Validates the message to make sure that curly braces have not been input as these would interfere
                 * with the method I have used to send messages over the socket.
                 */
                String messageContent = inputMessage.getContent();
                if (messageContent.contains("{") || messageContent.contains("}")) {
                    client.getIO().error("Message may not contain '{' or '}'!");
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
