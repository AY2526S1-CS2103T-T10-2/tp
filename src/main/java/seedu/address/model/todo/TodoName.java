package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Todo's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTodoName(String)}
 */
public class TodoName {

    public static final String MESSAGE_CONSTRAINTS =
            "Todo names should be 1-50 characters long and may only contain"
                    + " letters, numbers, spaces, apostrophes ('), hyphens (-), ampersands (&) and commas (,). "
                    + "Extra whitespace is trimmed.";

    public static final String VALIDATION_REGEX = "^[A-Za-z0-9'&,\\- ]{1,50}$";

    public final String todoName;

    /**
     * Constructs a {@code TodoName}.
     * @param todoName A valid todo name.
     */
    public TodoName(String todoName) {
        requireNonNull(todoName);
        String trimmedName = todoName.trim();
        checkArgument(isValidTodoName(trimmedName), MESSAGE_CONSTRAINTS);
        this.todoName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid todo name.
     */
    public static boolean isValidTodoName(String test) {
        return test != null
                && !test.trim().isEmpty()
                && test.trim().length() <= 50
                && test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return todoName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof TodoName)) {
            return false;
        }
        TodoName otherTodoName = (TodoName) other;
        return todoName.equalsIgnoreCase(otherTodoName.todoName);
    }
}
