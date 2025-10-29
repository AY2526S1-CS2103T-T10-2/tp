package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed:
 * - Persons: as defined by {@code UniquePersonList}
 * - Events: as defined by {@code UniqueEventList}
 * - Todos: as defined by {@code UniqueTodoList}
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueEventList events;
    private final TodoList todos;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        events = new UniqueEventList();
        todos = new TodoList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Replaces the contents of the todo list with {@code todos}.
     * {@code todos} must not contain duplicate todos.
     */
    public void setTodos(List<Todo> todos) {
        this.todos.setTodos(todos);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
        setTodos(newData.getTodoList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        todos.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event {@code editedEvent} must not be the same as another existing event in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /** Removes event from the address book. */
    public void removeEvent(Event e) {
        events.remove(e);
    }

    /**
     * Returns true if a todo with the same name as {@code todo} exists in the address book.
     */
    public boolean hasTodo(Todo todo) {
        requireNonNull(todo);
        return todos.contains(todo);
    }

    /**
     * Adds a todo to the address book.
     * The todo must not already exist in the address book.
     */
    public void addTodo(Todo todo) {
        todos.add(todo);
    }

    /**
     * Replaces the given todo {@code target} in the list with {@code editedTodo}.
     * {@code target} must exist in the address book.
     * The todo {@code editedTodo} must not be the same as another existing todo in the address book.
     */
    public void setTodo(Todo target, Todo editedTodo) {
        requireNonNull(editedTodo);

        todos.setTodo(target, editedTodo);
    }

    /** Removes todo from the address book. */
    public void removeTodo(Todo todo) {
        todos.remove(todo);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Todo> getTodoList() {
        return todos.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(persons, events, todos);
    }
}
