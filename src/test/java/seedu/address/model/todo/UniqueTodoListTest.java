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

public class UniqueTodoListTest {

    private final UniqueTodoList uniqueTodoList = new UniqueTodoList();

    @Test
    public void contains_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.contains(null));
    }

    @Test
    public void contains_todoNotInList_returnsFalse() {
        assertFalse(uniqueTodoList.contains(REVIEW_PROPOSAL));
    }

    @Test
    public void contains_todoInList_returnsTrue() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        assertTrue(uniqueTodoList.contains(REVIEW_PROPOSAL));
    }

    @Test
    public void add_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.add(null));
    }

    @Test
    public void add_duplicateTodo_allowed() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        uniqueTodoList.add(REVIEW_PROPOSAL); // duplicates allowed
        ObservableList<Todo> view = uniqueTodoList.asUnmodifiableObservableList();
        assertEquals(2, view.size());
        assertEquals(REVIEW_PROPOSAL, view.get(0));
        assertEquals(REVIEW_PROPOSAL, view.get(1));
    }

    @Test
    public void setTodo_nullTargetTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueTodoList.setTodo(null, REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_nullEditedTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueTodoList.setTodo(REVIEW_PROPOSAL, null));
    }

    @Test
    public void setTodo_targetTodoNotInList_throwsTodoNotFoundException() {
        assertThrows(TodoNotFoundException.class, () ->
                uniqueTodoList.setTodo(REVIEW_PROPOSAL, REVIEW_PROPOSAL));
    }

    @Test
    public void setTodo_replaceExisting_success() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        Todo edited = new TodoBuilder(REVIEW_PROPOSAL)
                .withDescription("Re-review proposal with finance inputs")
                .build();

        uniqueTodoList.setTodo(REVIEW_PROPOSAL, edited);

        UniqueTodoList expected = new UniqueTodoList();
        expected.add(edited);
        assertEquals(expected.asUnmodifiableObservableList(), uniqueTodoList.asUnmodifiableObservableList());
    }

    @Test
    public void remove_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.remove(null));
    }

    @Test
    public void remove_todoDoesNotExist_throwsTodoNotFoundException() {
        assertThrows(TodoNotFoundException.class, () -> uniqueTodoList.remove(REVIEW_PROPOSAL));
    }

    @Test
    public void remove_existingTodo_removesTodo() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        uniqueTodoList.remove(REVIEW_PROPOSAL);
        UniqueTodoList expected = new UniqueTodoList();
        assertEquals(expected.asUnmodifiableObservableList(), uniqueTodoList.asUnmodifiableObservableList());
    }

    @Test
    public void setTodos_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTodoList.setTodos((List<Todo>) null));
    }

    @Test
    public void setTodos_list_replacesOwnListWithProvidedList() {
        uniqueTodoList.add(REVIEW_PROPOSAL);
        List<Todo> replacement = Collections.singletonList(SEND_INVITES);
        uniqueTodoList.setTodos(replacement);

        UniqueTodoList expected = new UniqueTodoList();
        expected.add(SEND_INVITES);
        assertEquals(expected.asUnmodifiableObservableList(), uniqueTodoList.asUnmodifiableObservableList());
    }

    @Test
    public void setTodos_listWithDuplicates_allowed() {
        List<Todo> withDuplicates = Arrays.asList(REVIEW_PROPOSAL, REVIEW_PROPOSAL, SEND_INVITES);
        uniqueTodoList.setTodos(withDuplicates);
        assertEquals(3, uniqueTodoList.asUnmodifiableObservableList().size());
        assertEquals(REVIEW_PROPOSAL, uniqueTodoList.asUnmodifiableObservableList().get(0));
        assertEquals(REVIEW_PROPOSAL, uniqueTodoList.asUnmodifiableObservableList().get(1));
        assertEquals(SEND_INVITES, uniqueTodoList.asUnmodifiableObservableList().get(2));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueTodoList.asUnmodifiableObservableList().remove(0));
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

        uniqueTodoList.setTodos(Arrays.asList(linked, notLinked));
        uniqueTodoList.setPerson(from, to);

        // linked todo's contact should now be 'to', other remains unchanged
        assertEquals(to.getName(), uniqueTodoList.asUnmodifiableObservableList().get(0).getContactName());
        assertEquals(notLinked.getContactName(), uniqueTodoList.asUnmodifiableObservableList().get(1).getContactName());
    }

    @Test
    public void setPerson_sameName_noChange() {
        Person p = new PersonBuilder().withName("Same Name").build();
        Todo linked = new TodoBuilder().withTodoName("Arrange transport")
                .withDescription("2 buses")
                .withContactName(p.getName().fullName)
                .build();

        uniqueTodoList.add(linked);
        uniqueTodoList.setPerson(p, p);

        // list unchanged
        assertEquals(1, uniqueTodoList.asUnmodifiableObservableList().size());
        assertEquals(p.getName(), uniqueTodoList.asUnmodifiableObservableList().get(0).getContactName());
    }

    @Test
    public void toStringMethod_matchesBackedListString() {
        assertEquals(uniqueTodoList.asUnmodifiableObservableList().toString(), uniqueTodoList.toString());
    }
}
