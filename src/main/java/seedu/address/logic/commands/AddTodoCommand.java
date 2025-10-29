package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODO_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;


/**
 * Adds a todo to the address book.
 */
public class AddTodoCommand extends Command {
    public static final String COMMAND_WORD = "add-todo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a todo to the address book. "
            + "Parameters: "
            + PREFIX_TODO_NAME + "TODO NAME "
            + PREFIX_TODO_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_NAME + "NAME (of contact)] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TODO_NAME + "Call for TSC2025 "
            + PREFIX_TODO_DESCRIPTION + "Call Taylor Swift's manager "
            + PREFIX_NAME + "Alex Yeoh ";
    public static final String MESSAGE_SUCCESS = "New Todo added: %1$s";


    private final Todo toAdd;

    /**
     * Creates an AddTodoCommand to add the specified {@code Todo}
     */
    public AddTodoCommand(Todo toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        final Name contactName = toAdd.getContactName();
        if (contactName != null && !hasContactName(model, contactName)) {
            throw new CommandException("Contact not found: " + contactName.fullName);
        }

        model.addTodo(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Checks if the input contact name exists in the current PersonList
     */
    private boolean hasContactName(Model model, Name name) {
        return model.getAddressBook().getPersonList().stream()
                .map(Person::getName)
                .anyMatch(name::equals);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddTodoCommand)) {
            return false;
        }
        AddTodoCommand e = (AddTodoCommand) other;
        return toAdd.equals(e.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
