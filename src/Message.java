import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines a message object, encompassing the message's content, the date and time it was sent and the name of the user
 * who sent it.
 */
public class Message {
    private final String content;
    private final Date date;
    private final String name;

    /* Defines a constant date format which is used on all clients and servers so that any difference in locale settings
     * will not affect the way in which messages are parsed.
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");

    /**
     * Creates a message with the specified content and name and initializes the date to be the current time.
     * @param content The desired content of the message.
     * @param name The name of the user who sent the message.
     */
    public Message(String content, String name) {
        this.content = content;
        date = new Date();
        this.name = name;
    }

    /**
     * Creates a message with the specified content, name and date.
     * @param content The desired content of the message.
     * @param name The name of the user who sent the message.
     * @param date The date that the message was sent.
     */
    public Message(String content, String name, Date date) {
        this.content = content;
        this.date = date;
        this.name = name;
    }

    /**
     * @return The message body.
     */
    protected String getContent() {
        return content;
    }

    /**
     * @return The date the message was sent as a formatted string.
     */
    protected String getDateString() {
        return dateFormat.format(date);
    }

    /**
     * @return The name of the user who sent the message.
     */
    protected String getName() {
        return name;
    }

    /**
     * Parses an incoming message string.
     * @param input The string received from the socket of the format "{content}{date}{name}".
     * @return A message object containing the information received from the socket.
     * @throws ParseException If the date received cannot be parsed.
     */
    protected static Message parse(String input) throws ParseException {
        if (input.contains("{") && input.contains("}")) {
            //Parses the content section of the string between the first pair of curly braces
            int endOfSection = input.indexOf('}');
            String inputContent = input.substring((input.indexOf('{') + 1), input.indexOf('}'));

            //Parses the date section of the string between the second pair of curly braces
            input = input.substring(endOfSection + 1);
            endOfSection = input.indexOf('}');
            String inputDateString = input.substring((input.indexOf('{') + 1), endOfSection);

            //Parses the name section of the string between the third pair of curly braces
            input = input.substring(endOfSection + 1);
            endOfSection = input.indexOf('}');
            String inputName = input.substring((input.indexOf('{') + 1), endOfSection);

            Date inputDate = dateFormat.parse(inputDateString);

            return new Message(inputContent, inputName, inputDate);
        }
        else {
            /* If the incoming message does not conform to the correct then just create a message object with content
             * from the input string and name "Anonymous".
             */
            return new Message(input, "Anonymous");
        }
    }

    /**
     * @return A string of the form: <br />"date | name | content"
     */
    @Override
    public String toString() {
        return dateFormat.format(date) + " | " + name + " | " + content;
    }

    /**
     * Converts the message to a string so that it can be sent via the socket.
     * @return A string of the form "{content}{date}{name}".
     */
    protected String toTransportString() {
        return "{" + content + "}{" + dateFormat.format(date) + "}{" + name + "}";
    }
}
