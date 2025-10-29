package seedu.address.model.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTodos.REVIEW_PROPOSAL;
import static seedu.address.testutil.TypicalTodos.SEND_INVITES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.todo.exceptions.TodoNotFoundException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TodoBuilder;

public class TodoListTest {

    private final TodoList todoList = new TodoList();

    @Test
    public void contains_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> todoList.contains(null));
    }

    @Test
    public void contains_todoNotInList_returnsFalse() {
        assertFalse(todoList.contains(REVIEW_PROPOSAL));
    }

    @Test
    public void contains_todoInList_returnsTrue() {
        todoList.add(REVIEW_PROPOSAL);
        assertTrue(todoList.contains(REVIEW_PROPOSAL));
    }

    @Test
    public void add_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> todoList.add(null));
    }

    @Test
    public void add_duplicateTodo_allowed() {
        todoList.add(REVIEW_PROPOSAL);
        todoList.add(REVIEW_PROPOSAL); // duplicates allowed
        ObservableList<Todo> view = todoList.asUnmodifiableObservableList();
        assertEquals(2, view.size());
        assertEquals(REVIEW_PROPOSAL, view.get(0));
        assertEquals(REVIEW_PROPOSAL, view.get(1));
    }

    @Test
    public void setTodo_nullTargetTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                todoList.setTodo(null, REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_nullEditedTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                todoList.setTodo(REVIEW_PROPOSAL, null));
    }

    @Test
    public void setTodo_targetTodoNotInList_throwsTodoNotFoundException() {
        assertThrows(TodoNotFoundException.class, () ->
                todoList.setTodo(REVIEW_PROPOSAL, REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_replaceExisting_success() {
        todoList.add(REVIEW_PROPOSAL);
        Todo edited = new TodoBuilder(REVIEW_PROPOSAL)
                .withDescription("Re-review proposal with finance inputs")
                .build();

        todoList.setTodo(REVIEW_PROPOSAL, edited);

        TodoList expected = new TodoList();
        expected.add(edited);
        assertEquals(expected.asUnmodifiableObservableList(), todoList.asUnmodifiableObservableList());
    }

    @Test
    public void remove_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> todoList.remove(null));
    }

    @Test
    public void remove_todoDoesNotExist_throwsTodoNotFoundException() {
        assertThrows(TodoNotFoundException.class, () -> todoList.remove(REVIEW_PROPOSAL));
    }

    @Test
    public void remove_existingTodo_removesTodo() {
        todoList.add(REVIEW_PROPOSAL);
        todoList.remove(REVIEW_PROPOSAL);
        TodoList expected = new TodoList();
        assertEquals(expected.asUnmodifiableObservableList(), todoList.asUnmodifiableObservableList());
    }

    @Test
    public void setTodos_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> todoList.setTodos((List<Todo>) null));
    }

    @Test
    public void setTodos_list_replacesOwnListWithProvidedList() {
        todoList.add(REVIEW_PROPOSAL);
        List<Todo> replacement = Collections.singletonList(SEND_INVITES);
        todoList.setTodos(replacement);

        TodoList expected = new TodoList();
        expected.add(SEND_INVITES);
        assertEquals(expected.asUnmodifiableObservableList(), todoList.asUnmodifiableObservableList());
    }

    @Test
    public void setTodos_listWithDuplicates_allowed() {
        List<Todo> withDuplicates = Arrays.asList(REVIEW_PROPOSAL, REVIEW_PROPOSAL, SEND_INVITES);
        todoList.setTodos(withDuplicates);
        assertEquals(3, todoList.asUnmodifiableObservableList().size());
        assertEquals(REVIEW_PROPOSAL, todoList.asUnmodifiableObservableList().get(0));
        assertEquals(REVIEW_PROPOSAL, todoList.asUnmodifiableObservableList().get(1));
        assertEquals(SEND_INVITES, todoList.asUnmodifiableObservableList().get(2));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                todoList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void setPerson_linkedTodosUpdated_success() {
        Person from = new PersonBuilder().withName("Alice Pauline").build();
        Person to = new PersonBuilder().withName("Benson Meier").build();

        Todo linked = new TodoBuilder().withTodoName("Coordinate ushers")
                .withDescription("Confirm ushers and briefing time")
                .withContactName(from.getName().fullName)
                .build();
        Todo notLinked = new TodoBuilder().withTodoName("Book venue")
                .withDescription("Court A, 3–6pm")
                .withoutContactName()
                .build();

        todoList.setTodos(Arrays.asList(linked, notLinked));
        todoList.setPerson(from, to);

        // linked todo's contact should now be 'to', other remains unchanged
        assertEquals(to.getName(), todoList.asUnmodifiableObservableList().get(0).getContactName());
        assertEquals(notLinked.getContactName(), todoList.asUnmodifiableObservableList().get(1).getContactName());
    }

    @Test
    public void setPerson_sameName_noChange() {
        Person p = new PersonBuilder().withName("Same Name").build();
        Todo linked = new TodoBuilder().withTodoName("Arrange transport")
                .withDescription("2 buses")
                .withContactName(p.getName().fullName)
                .build();

        todoList.add(linked);
        todoList.setPerson(p, p);

        // list unchanged
        assertEquals(1, todoList.asUnmodifiableObservableList().size());
        assertEquals(p.getName(), todoList.asUnmodifiableObservableList().get(0).getContactName());
    }

    @Test
    public void toStringMethod_matchesBackedListString() {
        assertEquals(todoList.asUnmodifiableObservableList().toString(), todoList.toString());
    }
}
