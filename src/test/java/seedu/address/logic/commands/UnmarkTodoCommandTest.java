package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTodos.getTypicalTodos;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Todo;
import seedu.address.ui.DisplayList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnmarkTodoCommand.
 */
public class UnmarkTodoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        
        // Add typical todos to both models
        List<Todo> typicalTodos = getTypicalTodos();
        for (Todo todo : typicalTodos) {
            model.addTodo(todo);
            expectedModel.addTodo(todo);
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Todo todoToUnmark = model.getFilteredTodoList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnmarkTodoCommand.MESSAGE_UNMARK_TODO_SUCCESS,
                Messages.format(todoToUnmark.withCompletionStatus(false)));

        expectedModel.setTodo(todoToUnmark, todoToUnmark.withCompletionStatus(false));

        assertCommandSuccess(unmarkTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTodoList().size() + 1);
        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTodoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Get the first todo before filtering
        Todo firstTodo = model.getFilteredTodoList().get(0);
        
        // Filter todos to show only the first one
        model.updateFilteredTodoList(todo -> todo.equals(firstTodo));
        expectedModel.updateFilteredTodoList(todo -> todo.equals(firstTodo));

        Todo todoToUnmark = model.getFilteredTodoList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnmarkTodoCommand.MESSAGE_UNMARK_TODO_SUCCESS,
                Messages.format(todoToUnmark.withCompletionStatus(false)));

        expectedModel.setTodo(todoToUnmark, todoToUnmark.withCompletionStatus(false));

        assertCommandSuccess(unmarkTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Get the first todo before filtering
        Todo firstTodo = model.getFilteredTodoList().get(0);
        
        // Filter todos to show only the first one
        model.updateFilteredTodoList(todo -> todo.equals(firstTodo));

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTodoList().size());

        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTodoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unmarkAlreadyIncompleteTodo_success() {
        // Get a todo that's not completed
        Todo incompleteTodo = getTypicalTodos().stream()
                .filter(todo -> !todo.getIsCompleted())
                .findFirst()
                .orElse(null);
        
        assertTrue(incompleteTodo != null, "Should have at least one incomplete todo in test data");
        
        // Find the index of this todo
        int todoIndex = model.getFilteredTodoList().indexOf(incompleteTodo);
        Index index = Index.fromZeroBased(todoIndex);
        
        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(index);

        String expectedMessage = String.format(UnmarkTodoCommand.MESSAGE_UNMARK_TODO_SUCCESS,
                Messages.format(incompleteTodo.withCompletionStatus(false)));

        expectedModel.setTodo(incompleteTodo, incompleteTodo.withCompletionStatus(false));

        assertCommandSuccess(unmarkTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_unmarkCompletedTodo_success() {
        // Get a todo that's completed
        Todo completedTodo = getTypicalTodos().stream()
                .filter(Todo::getIsCompleted)
                .findFirst()
                .orElse(null);
        
        assertTrue(completedTodo != null, "Should have at least one completed todo in test data");
        
        // Find the index of this todo
        int todoIndex = model.getFilteredTodoList().indexOf(completedTodo);
        Index index = Index.fromZeroBased(todoIndex);
        
        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(index);

        String expectedMessage = String.format(UnmarkTodoCommand.MESSAGE_UNMARK_TODO_SUCCESS,
                Messages.format(completedTodo.withCompletionStatus(false)));

        expectedModel.setTodo(completedTodo, completedTodo.withCompletionStatus(false));

        assertCommandSuccess(unmarkTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void equals() {
        UnmarkTodoCommand unmarkFirstCommand = new UnmarkTodoCommand(INDEX_FIRST_PERSON);
        UnmarkTodoCommand unmarkSecondCommand = new UnmarkTodoCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkTodoCommand unmarkFirstCommandCopy = new UnmarkTodoCommand(INDEX_FIRST_PERSON);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different todo -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkTodoCommand unmarkTodoCommand = new UnmarkTodoCommand(targetIndex);
        String expected = UnmarkTodoCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unmarkTodoCommand.toString());
    }
}
