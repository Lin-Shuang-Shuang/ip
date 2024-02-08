package lery;

/**
 * Represents a Ui.
 * The Ui class handles the user interface components and messages for the Lery chatbot application.
 * It provides methods to print formatted messages and the Lery logo.
 *
 * The class includes methods for printing messages with horizontal lines, greeting messages,
 * and exit messages.
 *
 */
public class Ui {
    private static final String LINE = "_".repeat(50);
    private static final String LOGO = " _                    \n"
            + "| |    ___ _ __ _   _ \n"
                    + "| |   / _ \\ '__| | | |\n"
                    + "| |__|  __/ |  | |_| |\n"
                    + "|_____\\___|_|   \\__, |\n"
                    + "                |___/ \n";

    /**
     * Prints a message surrounded by horizontal line breaks.
     *
     * @param m the message to be printed.
     */
    public void printMessageWithLine(String m) {
        System.out.println(this.LINE + m + "\n" + this.LINE);
    }

    /**
     * Prints a greeting message with the Lery logo.
     *
     * @return
     */
    public String greet() {
        return this.LINE + "\nHello! I'm Lery."
                + "\n" + "What can I do for you?\n" + this.LINE;
    }

    /**
     * Prints an exit message.
     */
    public String exit() {
        return this.LINE + "\nBye. Hope to see you again soon!\n" + this.LINE;
    }

}
