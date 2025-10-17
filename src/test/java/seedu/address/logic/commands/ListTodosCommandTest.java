package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTodos.getTypicalTodos;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.Todo;
import seedu.address.ui.DisplayList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTodosCommand.
 */
public class ListTodosCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listTodosIsNotFiltered_showsSameList() {
        // Add some todos to the model for testing
        List<Todo> typicalTodos = getTypicalTodos();
        for (Todo todo : typicalTodos) {
            model.addTodo(todo);
            expectedModel.addTodo(todo);
        }

        String expectedMessage = ListTodosCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(new ListTodosCommand(), model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_listTodosIsFiltered_showsEverything() {
        // Add some todos to the model for testing
        List<Todo> typicalTodos = getTypicalTodos();
        for (Todo todo : typicalTodos) {
            model.addTodo(todo);
            expectedModel.addTodo(todo);
        }

        // Filter todos (simulate some filtering)
        model.updateFilteredTodoList(todo -> todo.getTodoName().toString().contains("Review"));

        String expectedMessage = ListTodosCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(new ListTodosCommand(), model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_noTodos_showsNoTodosMessage() {
        String expectedMessage = ListTodosCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(new ListTodosCommand(), model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void execute_singleTodo_showsSingleTodo() {
        // Add a single todo
        Todo singleTodo = getTypicalTodos().get(0);
        model.addTodo(singleTodo);
        expectedModel.addTodo(singleTodo);

        String expectedMessage = ListTodosCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(new ListTodosCommand(), model, expectedMessage, expectedModel, DisplayList.TODO);
    }

    @Test
    public void equals() {
        ListTodosCommand listTodosCommand = new ListTodosCommand();

        // same object -> returns true
        assert listTodosCommand.equals(listTodosCommand);

        // same type -> returns true
        assert listTodosCommand.equals(new ListTodosCommand());

        // different types -> returns false
        assert !listTodosCommand.equals(1);

        // null -> returns false
        assert !listTodosCommand.equals(null);
    }
}
