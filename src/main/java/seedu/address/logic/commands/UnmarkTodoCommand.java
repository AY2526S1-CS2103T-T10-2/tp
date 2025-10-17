package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;
import seedu.address.ui.DisplayList;

/**
 * Marks a todo as incomplete identified using its displayed index from the address book.
 */
public class UnmarkTodoCommand extends Command {

    public static final String COMMAND_WORD = "unmark-todo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the todo identified by the index number used in the displayed todo list "
            + "as incomplete.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TODO_SUCCESS = "Marked todo as incomplete: %1$s";

    private final Index targetIndex;

    public UnmarkTodoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Todo> lastShownList = model.getFilteredTodoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        Todo todoToUnmark = lastShownList.get(targetIndex.getZeroBased());
        Todo unmarkedTodo = todoToUnmark.withCompletionStatus(false);
        model.setTodo(todoToUnmark, unmarkedTodo);
        return new CommandResult(
                String.format(MESSAGE_UNMARK_TODO_SUCCESS, Messages.format(unmarkedTodo)),
                DisplayList.TODO);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkTodoCommand)) {
            return false;
        }

        UnmarkTodoCommand otherUnmarkTodoCommand = (UnmarkTodoCommand) other;
        return targetIndex.equals(otherUnmarkTodoCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
