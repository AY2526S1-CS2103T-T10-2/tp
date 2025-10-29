package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.TodoBuilder;

/**
 * Tests for {@link AddTodoCommand}.
 */
public class AddTodoCommandTest {

    @Test
    public void constructor_nullTodo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTodoCommand(null));
    }

    @Test
    public void execute_todoAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTodoAdded modelStub = new ModelStubAcceptingTodoAdded(ALICE);

        Todo validTodo = new TodoBuilder().build();
        CommandResult commandResult = new AddTodoCommand(validTodo).execute(modelStub);

        assertEquals(String.format(AddTodoCommand.MESSAGE_SUCCESS, Messages.format(validTodo)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTodo), modelStub.todosAdded);
    }

    @Test
    public void execute_todoWithExistingContact_addSuccessful() throws Exception {
        ModelStubAcceptingTodoAdded modelStub = new ModelStubAcceptingTodoAdded(ALICE);
        Todo validTodoWithContact = new TodoBuilder().withContactName(ALICE.getName().toString()).build();

        CommandResult result = new AddTodoCommand(validTodoWithContact).execute(modelStub);

        assertEquals(String.format(AddTodoCommand.MESSAGE_SUCCESS, Messages.format(validTodoWithContact)),
                result.getFeedbackToUser());
        assertEquals(Arrays.asList(validTodoWithContact), modelStub.todosAdded);
    }

    @Test
    public void execute_contactNameProvidedButNotFound_throwsCommandException() {
        ModelStubAcceptingTodoAdded modelStub = new ModelStubAcceptingTodoAdded();
        Todo todoWithMissingContact = new TodoBuilder().withContactName("N").build();

        assertThrows(CommandException.class,
                "Contact not found: N", () -> new AddTodoCommand(todoWithMissingContact)
                        .execute(modelStub));
    }

    @Test
    public void toStringMethod() {
        Todo todo = new TodoBuilder().build();
        AddTodoCommand cmd = new AddTodoCommand(todo);
        String expected = AddTodoCommand.class.getCanonicalName() + "{toAdd=" + todo + "}";
        assertEquals(expected, cmd.toString());
    }

    @Test
    public void equals() {
        Todo first = new TodoBuilder().withTodoName("Call TSC2025").build();
        Todo second = new TodoBuilder().withTodoName("Draft email").build();

        AddTodoCommand addFirst = new AddTodoCommand(first);
        AddTodoCommand addSecond = new AddTodoCommand(second);

        // same object -> true
        assertTrue(addFirst.equals(addFirst));

        // same values -> true
        AddTodoCommand addFirstCopy = new AddTodoCommand(first);
        assertTrue(addFirst.equals(addFirstCopy));

        // different types -> false
        assertFalse(addFirst.equals(1));

        // null -> false
        assertFalse(addFirst.equals(null));

        // different todo -> false
        assertFalse(addFirst.equals(addSecond));
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
        @Override public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public boolean hasEvent(seedu.address.model.event.Event event) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void addEvent(seedu.address.model.event.Event event) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void deleteEvent(seedu.address.model.event.Event event) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void setEvent(seedu.address.model.event.Event target,
                                       seedu.address.model.event.Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void updateFilteredPersonList(java.util.function.Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public ObservableList<seedu.address.model.event.Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void updateFilteredEventList(Predicate<seedu.address.model.event.Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public boolean hasTodo(Todo todo) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void deleteTodo(Todo todo) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void addTodo(Todo todo) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void setTodo(Todo target, Todo editedTodo) {
            throw new AssertionError("This method should not be called.");
        }
        @Override public ObservableList<Todo> getFilteredTodoList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override public void updateFilteredTodoList(Predicate<Todo> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single todo and reports duplicates.
     */
    private class ModelStubWithTodo extends ModelStub {
        private final Todo todo;

        ModelStubWithTodo(Todo todo) {
            requireNonNull(todo);
            this.todo = todo;
        }

        @Override
        public boolean hasTodo(Todo t) {
            requireNonNull(t);
            return this.todo.equals(t);
        }
    }

    /**
     * A Model stub that accepts todos being added. Optionally seeds the AddressBook with persons
     * to satisfy contact-name checks.
     */
    private class ModelStubAcceptingTodoAdded extends ModelStub {
        final ArrayList<Todo> todosAdded = new ArrayList<>();
        private final AddressBook backingBook;

        ModelStubAcceptingTodoAdded(Person... seededPersons) {
            backingBook = new AddressBook();
            for (Person p : seededPersons) {
                backingBook.addPerson(p);
            }
        }

        @Override
        public boolean hasTodo(Todo todo) {
            requireNonNull(todo);
            return todosAdded.stream().anyMatch(todo::equals);
        }

        @Override
        public void addTodo(Todo todo) {
            requireNonNull(todo);
            todosAdded.add(todo);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return backingBook;
        }
    }
}
