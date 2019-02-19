import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private final String content;
    private final Date date;
    private final String name;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");

    //TODO: Add the user's identity as a field

    /**
     * Creates a message with the specified content and name and initializes the date to be the current time.
     * @param content The desired content of the message.
     */
    public Message(String content, String name) {
        this.content = content;
        date = new Date();
        this.name = name;
    }

    /**
     * Creates a message with the specified content, name and date.
     * @param content The desired content of the message.
     */
    public Message(String content, String name, Date date) {
        this.content = content;
        this.date = date;
        this.name = name;
    }

    protected String getContent() {
        return content;
    }

    protected Date getDate() {
        return date;
    }

    protected String getDateString() {
        return dateFormat.format(date);
    }

    protected String getName() {
        return name;
    }

    protected static Message parse(String input) throws ParseException {
        int endOfSection = input.indexOf('}');
        String inputContent = input.substring((input.indexOf('{') + 1), input.indexOf('}'));
        input = input.substring(endOfSection + 1);
        endOfSection = input.indexOf('}');
        String inputDateString = input.substring((input.indexOf('{') + 1), endOfSection);
        input = input.substring(endOfSection + 1);
        endOfSection = input.indexOf('}');
        String inputName = input.substring((input.indexOf('{')+1), endOfSection);
        Date inputDate = dateFormat.parse(inputDateString);
        return new Message(inputContent, inputName, inputDate);
    }

    @Override
    public String toString() {
        return dateFormat.format(date) + " | " + name + " | " + content;
    }

    protected String toTransportString() {
        return "{" + content + "}{" + dateFormat.format(date) + "}{" + name + "}";
    }
}
