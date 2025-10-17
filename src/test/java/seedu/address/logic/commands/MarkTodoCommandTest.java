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
 * Contains integration tests (interaction with the Model) and unit tests for MarkTodoCommand.
 */
public class MarkTodoCommandTest {

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
        Todo todoToMark = model.getFilteredTodoList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkTodoCommand markTodoCommand = new MarkTodoCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MarkTodoCommand.MESSAGE_MARK_TODO_SUCCESS,
                Messages.format(todoToMark.withCompletionStatus(true)));

        expectedModel.setTodo(todoToMark, todoToMark.withCompletionStatus(true));

        assertCommandSuccess(markTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTodoList().size() + 1);
        MarkTodoCommand markTodoCommand = new MarkTodoCommand(outOfBoundIndex);

        assertCommandFailure(markTodoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Get the first todo before filtering
        Todo firstTodo = model.getFilteredTodoList().get(0);
        
        // Filter todos to show only the first one
        model.updateFilteredTodoList(todo -> todo.equals(firstTodo));
        expectedModel.updateFilteredTodoList(todo -> todo.equals(firstTodo));

        Todo todoToMark = model.getFilteredTodoList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkTodoCommand markTodoCommand = new MarkTodoCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MarkTodoCommand.MESSAGE_MARK_TODO_SUCCESS,
                Messages.format(todoToMark.withCompletionStatus(true)));

        expectedModel.setTodo(todoToMark, todoToMark.withCompletionStatus(true));

        assertCommandSuccess(markTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
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

        MarkTodoCommand markTodoCommand = new MarkTodoCommand(outOfBoundIndex);

        assertCommandFailure(markTodoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void execute_markAlreadyCompletedTodo_success() {
        // Get a todo that's already completed
        Todo completedTodo = getTypicalTodos().stream()
                .filter(Todo::getIsCompleted)
                .findFirst()
                .orElse(null);
        
        assertTrue(completedTodo != null, "Should have at least one completed todo in test data");
        
        // Find the index of this todo
        int todoIndex = model.getFilteredTodoList().indexOf(completedTodo);
        Index index = Index.fromZeroBased(todoIndex);
        
        MarkTodoCommand markTodoCommand = new MarkTodoCommand(index);

        String expectedMessage = String.format(MarkTodoCommand.MESSAGE_MARK_TODO_SUCCESS,
                Messages.format(completedTodo.withCompletionStatus(true)));

        expectedModel.setTodo(completedTodo, completedTodo.withCompletionStatus(true));

        assertCommandSuccess(markTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_markIncompleteTodo_success() {
        // Get a todo that's not completed
        Todo incompleteTodo = getTypicalTodos().stream()
                .filter(todo -> !todo.getIsCompleted())
                .findFirst()
                .orElse(null);
        
        assertTrue(incompleteTodo != null, "Should have at least one incomplete todo in test data");
        
        // Find the index of this todo
        int todoIndex = model.getFilteredTodoList().indexOf(incompleteTodo);
        Index index = Index.fromZeroBased(todoIndex);
        
        MarkTodoCommand markTodoCommand = new MarkTodoCommand(index);

        String expectedMessage = String.format(MarkTodoCommand.MESSAGE_MARK_TODO_SUCCESS,
                Messages.format(incompleteTodo.withCompletionStatus(true)));

        expectedModel.setTodo(incompleteTodo, incompleteTodo.withCompletionStatus(true));

        assertCommandSuccess(markTodoCommand, model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void equals() {
        MarkTodoCommand markFirstCommand = new MarkTodoCommand(INDEX_FIRST_PERSON);
        MarkTodoCommand markSecondCommand = new MarkTodoCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkTodoCommand markFirstCommandCopy = new MarkTodoCommand(INDEX_FIRST_PERSON);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different todo -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkTodoCommand markTodoCommand = new MarkTodoCommand(targetIndex);
        String expected = MarkTodoCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markTodoCommand.toString());
    }
}
