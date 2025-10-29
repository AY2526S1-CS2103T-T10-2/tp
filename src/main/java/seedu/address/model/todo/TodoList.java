package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.todo.exceptions.TodoNotFoundException;

/**
 * A list of todos.
 */
public class TodoList implements Iterable<Todo> {

    private final ObservableList<Todo> internalList = FXCollections.observableArrayList();
    private final ObservableList<Todo> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent todo as the given argument.
     */
    public boolean contains(Todo toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a todo to the list.
     */
    public void add(Todo toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setTodos(List<Todo> todos) {
        requireAllNonNull(todos);
        internalList.setAll(todos);
    }

    /**
     * Removes the equivalent todo from the list.
     */
    public void remove(Todo toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TodoNotFoundException();
        }
    }

    /**
     * Replaces the todo {@code target} in the list with {@code editedTodo}.
     * {@code target} must exist in the list.
     */
    public void setTodo(Todo target, Todo editedTodo) {
        requireAllNonNull(target, editedTodo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TodoNotFoundException();
        }
        internalList.set(index, editedTodo);
    }

    /**
     * Replaces each todo in the list that is associated with person {@code target} with a new todo associated with
     * person {@code editedPerson}.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        Name targetName = requireNonNull(target.getName());
        if (targetName.equals(editedPerson.getName())) {
            return;
        }

        for (Todo todo : this) {
            if (targetName.equals(todo.getContactName())) {
                setTodo(todo, todo.withLinkedContactName(editedPerson.getName()));
            }
        }
    }


    /** Returns an unmodifiable view of the internal list. */
    public ObservableList<Todo> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Todo> iterator() {
        return internalList.iterator();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
