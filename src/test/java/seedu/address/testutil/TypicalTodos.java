package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.todo.Todo;

/**
 * A utility class containing a list of {@code Todo} objects to be used in tests.
 */
public class TypicalTodos {

    public static final Todo REVIEW_PROPOSAL = new TodoBuilder()
            .withTodoName("Review Project Proposal")
            .withDescription("Review the Q1 project proposal document")
            .withContactName("John Doe")
            .withCompleted(false)
            .build();

    public static final Todo SEND_INVITES = new TodoBuilder()
            .withTodoName("Send Meeting Invites")
            .withDescription("Send calendar invites for team meeting")
            .withContactName("Alice Smith")
            .withCompleted(true)
            .build();

    public static final Todo UPDATE_DOCS = new TodoBuilder()
            .withTodoName("Update Documentation")
            .withDescription("Update API documentation for new features")
            .withoutContactName()
            .withCompleted(false)
            .build();

    public static final Todo BACKUP_DATABASE = new TodoBuilder()
            .withTodoName("Backup Database")
            .withDescription("Create weekly database backup")
            .withoutContactName()
            .withCompleted(true)
            .build();

    public static final Todo CODE_REVIEW = new TodoBuilder()
            .withTodoName("Code Review")
            .withDescription("Review pull request #123")
            .withContactName("Bob Wilson")
            .withCompleted(false)
            .build();

    public static final Todo TESTING = new TodoBuilder()
            .withTodoName("Write Unit Tests")
            .withDescription("Add unit tests for new feature")
            .withContactName("Carol Brown")
            .withCompleted(false)
            .build();

    public static final Todo DEPLOYMENT = new TodoBuilder()
            .withTodoName("Deploy to Production")
            .withDescription("Deploy version 2.1.0 to production")
            .withContactName("David Lee")
            .withCompleted(true)
            .build();

    private TypicalTodos() {} // prevents instantiation

    /**
     * Returns a list of typical todos for testing.
     */
    public static List<Todo> getTypicalTodos() {
        return new ArrayList<>(Arrays.asList(REVIEW_PROPOSAL, SEND_INVITES, UPDATE_DOCS,
                BACKUP_DATABASE, CODE_REVIEW, TESTING, DEPLOYMENT));
    }
}
