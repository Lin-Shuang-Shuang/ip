import javafx.scene.image.Image;
import lery.LeryException;
import lery.Parser;
import lery.Storage;
import lery.Ui;

import java.util.Scanner;

/**
 * Lery is a chatbot program that is used to save tasks
 *
 * @author  Lin Shuang Shuang
 * @version 1.0
 * @since   2024-01-25
 */

public class Lery {


    private Image user = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image lery = new Image(this.getClass().getResourceAsStream("/images/LeryDog.jpg"));


    private Storage storage;
    private Ui ui;
    /**
     * Constructs a Lery chatbot object.
     *
     */
    public Lery() {
        this.ui = new Ui();
        this.storage = new Storage();
    }

    /**
     * Gets Lery's response based on user input.
     *
     * @param input The user input to be processed.
     * @return A response generated based on user's input.
     */
    public String getResponse(String input) throws LeryException {
        try {
            if (input.equalsIgnoreCase("bye")) {
                return ui.exit();
            }
            Parser p = new Parser(this.storage);
            return p.parseCommand(input);
        } catch (LeryException e) {
            throw new LeryException(e.getMessage());
        }
    }

    /**
     * Initializes the chat by loading tasks and displaying a greeting message.
     *
     * @return A greeting message generated by the UI.
     * @throws LeryException If there's an error during chat initialization, such as loading tasks.
     */
    public String initialiseChat() throws LeryException {
        String greeting = ui.greet();
        this.storage.loadTasks();
        return greeting;
    }

    /**
     * Runs the chatbot and parses the users comments.
     */
    public void run() {

        System.out.println(ui.greet());
        Scanner scanner = new Scanner(System.in);
        try {
            this.storage.loadTasks();
        } catch (LeryException e) {
            ui.printMessage(e.getMessage());
        }
        while (scanner.hasNext()) {
            Parser p = new Parser(this.storage);
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("bye")) {
                System.out.println(ui.exit());
                break;
            }
            try {
                ui.printMessage(p.parseCommand(command));
            } catch (LeryException e) {
                ui.printMessage(e.getMessage());
            }
        }
    }

    /**
     * This is the main method which starts the chatbot.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Lery lery = new Lery();
        lery.run();
    }

}
