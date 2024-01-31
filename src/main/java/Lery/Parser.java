package Lery;

import Lery.task.*;

/**
 * Represents a Parser.
 * A Parser handles the parsing of user commands in the Lery chatbot application.
 *
 */
public class Parser {
    private Storage storage;

    /**
     * Constructs a Parser object with the specified Storage.
     *
     * @param s the Storage object used for managing tasks.
     */
    public Parser(Storage s) {
        this.storage = s;

    }


    /**
     * Parses the given command and executes the corresponding action.
     *
     * @param command the user command to be parsed.
     * @return a string containing the result or feedback of the command execution.
     * @throws LeryException if the user command has an error.
     */
    public String parseCommand(String command) throws LeryException {

        TaskList taskList = this.storage.getTaskList();
        if (command.equalsIgnoreCase("list")) {
            return taskList.printList();
        } else if (command.startsWith("mark")) {
            try {
                Integer id = Integer.parseInt(command.substring(5));
                return taskList.getTask(id - 1).markAsDone();
            } catch (NumberFormatException e) {
                throw new LeryException("Erm... Please enter a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                throw new LeryException("Erm... Please enter a task number.");
            }

        } else if (command.startsWith("unmark")) {
            try {
                Integer id = Integer.parseInt(command.substring(7));
                return taskList.getTask(id - 1).markAsDone();
            } catch (NumberFormatException e) {
                throw new LeryException("Erm... Please enter a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                throw new LeryException("Erm... Please enter a task number.");
            }
        } else if (command.startsWith("delete")) {
            try {
                Integer id = Integer.parseInt(command.substring(7));
                return this.parseDeleteTaskCommand(taskList.getTask(id - 1));
            } catch (NumberFormatException e) {
                throw new LeryException("Erm... Please enter a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                throw new LeryException("Erm... Please enter a task number.");
            }
        } else if (command.startsWith("find")) {
            String d = command.substring(5);
            return this.parseFindTaskCommand(d);

        } else {
            return this.parseAddTaskCommand(command);

        }
    }

    /**
     * Parses the command and adds a new task to the task list.
     *
     * @param command the user command to add a new task.
     * @return a string containing the result or feedback of the command execution.
     * @throws LeryException if the user command has an error.
     */
    public String parseAddTaskCommand(String command) throws LeryException {
        String msg = "Got it. I've added this task:\n";
        Task newTask;
        if (command.startsWith("todo")) {
            try {
                newTask = new Todo(command.substring(5));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new LeryException("Erm... Please provide event name.");
            }
        } else if (command.startsWith("deadline")){
            try {
                String[] taskDesc = command.substring(9).split("/by ");
                this.storage.checkDateFormat(taskDesc[1]);
                newTask = new Deadline(taskDesc[0], taskDesc[1]);

            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new LeryException("Erm... Please provide event details." + e);
            }
        } else if (command.startsWith("event")) {
            try {
                newTask = new Event(command.substring(6));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new LeryException("Erm... Please provide event details.");
            }
        } else {
            throw new LeryException("Erm... Please provide a valid command.");
        }
        this.storage.saveTasks(newTask);

        msg = msg + "[" + newTask.getType() + "]" + "[" + newTask.getStatusIcon()
                + "]" + " " + newTask.getDescription() + newTask.getExtraInfo()
                + "\nNow you have " + Integer.toString(this.storage.getSize())
                + " tasks in the list.";
        return msg;

    }

    /**
     * Parses the command and deletes the specified task from the task list.
     *
     * @param task the task to be deleted.
     * @return a string containing the result or feedback of the command execution.
     */
    public String parseDeleteTaskCommand(Task task) {
        this.storage.delete(task);
        String msg = "Noted. I've removed this task:\n" + task.getType() + "["
                + task.getStatusIcon() + "]" + " " + task.getDescription()
                + task.getExtraInfo() + "\nNow you have "
                + Integer.toString(this.storage.getSize()) + " tasks in the list.\n";
        return msg;
    }

    public String parseFindTaskCommand(String d) {
        TaskList t = this.storage.getTaskList();
        TaskList findList = new TaskList();
        for (int i = 0; i < t.getSize(); i++) {
            Task curr = t.getTask(i);
            String description = curr.getDescription();
            if (description.contains(d)) {
                findList.add(curr);
            }
        }
        if (findList.getSize() == 0) {
            return "Sorry there are no tasks that fits your keyword.";
        }
        return findList.printList();
    }

}
