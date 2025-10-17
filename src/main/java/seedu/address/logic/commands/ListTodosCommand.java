package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;

import seedu.address.model.Model;
import seedu.address.ui.DisplayList;

/**
 * Lists all todos in the address book to the user.
 * This command displays all todos in the UI using TodoCard components.
 */
public class ListTodosCommand extends Command {

    /** The command word used to invoke this command. */
    public static final String COMMAND_WORD = "list-todos";

    /** Success message displayed when todos are listed. */
    public static final String MESSAGE_SUCCESS = "Listed all todos";

    /**
     * Executes the list-todos command.
     *
     * @param model The model containing the address book data and todo management functionality.
     *              Must not be null.
     * @return CommandResult containing success message.
     * @throws NullPointerException if model is null.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Update the filtered todo list to show all todos
        model.updateFilteredTodoList(PREDICATE_SHOW_ALL_TODOS);
        return new CommandResult(MESSAGE_SUCCESS, DisplayList.TODO);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListTodosCommand)) {
            return false;
        }

        return true; // All ListTodosCommand instances are equal since they have no state
    }
}
