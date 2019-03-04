public class GUILauncher implements Runnable{

    private ChatClientGUI gui;

    @Override
    public void run() {
        gui = new ChatClientGUI();
    }

    public ChatClientGUI getGUI() {
        return gui;
    }
}
