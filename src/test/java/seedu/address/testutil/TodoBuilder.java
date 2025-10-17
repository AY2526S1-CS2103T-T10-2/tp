package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoName;

/**
 * A utility class to help with building Todo objects.
 */
public class TodoBuilder {

    public static final String DEFAULT_TODO_NAME = "Review Project Proposal";
    public static final String DEFAULT_DESCRIPTION = "Review the Q1 project proposal document";
    public static final String DEFAULT_CONTACT_NAME = "John Doe";

    private TodoName todoName;
    private String description;
    private Name contactName;
    private boolean isCompleted;

    /**
     * Creates a {@code TodoBuilder} with the default details.
     */
    public TodoBuilder() {
        todoName = new TodoName(DEFAULT_TODO_NAME);
        description = DEFAULT_DESCRIPTION;
        contactName = new Name(DEFAULT_CONTACT_NAME);
        isCompleted = false;
    }

    /**
     * Initializes the TodoBuilder with the data of {@code todoToCopy}.
     */
    public TodoBuilder(Todo todoToCopy) {
        todoName = todoToCopy.getTodoName();
        description = todoToCopy.getDescription();
        contactName = todoToCopy.getContactName();
        isCompleted = todoToCopy.getIsCompleted();
    }

    /**
     * Sets the {@code TodoName} of the {@code Todo} that we are building.
     */
    public TodoBuilder withTodoName(String todoName) {
        this.todoName = new TodoName(todoName);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Todo} that we are building.
     */
    public TodoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code contactName} of the {@code Todo} that we are building.
     */
    public TodoBuilder withContactName(String contactName) {
        this.contactName = new Name(contactName);
        return this;
    }

    /**
     * Sets the {@code contactName} to null for the {@code Todo} that we are building.
     */
    public TodoBuilder withoutContactName() {
        this.contactName = null;
        return this;
    }

    /**
     * Sets the {@code isCompleted} status of the {@code Todo} that we are building.
     */
    public TodoBuilder withCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    /**
     * Builds the {@code Todo} object.
     */
    public Todo build() {
        return new Todo(todoName, description, contactName, isCompleted);
    }
}
