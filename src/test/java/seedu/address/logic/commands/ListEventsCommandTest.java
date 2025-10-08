package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEventsCommand.
 */
public class ListEventsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listEventsIsNotFiltered_showsSameList() {
        // Add some events to the model for testing
        List<Event> typicalEvents = getTypicalEvents();
        for (Event event : typicalEvents) {
            model.addEvent(event);
            expectedModel.addEvent(event);
        }

        String expectedMessage = "Events: ["
            + typicalEvents.stream()
                .map(Event::toString)
                .reduce((a, b) -> a + ", " + b)
                .orElse("") + "]";

        assertCommandSuccess(new ListEventsCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listEventsIsFiltered_showsEverything() {
        // Add some events to the model for testing
        List<Event> typicalEvents = getTypicalEvents();
        for (Event event : typicalEvents) {
            model.addEvent(event);
            expectedModel.addEvent(event);
        }

        // Filter events (simulate some filtering)
        model.updateFilteredEventList(event -> event.getName().contains("Concert"));

        String expectedMessage = "Events: ["
            + typicalEvents.stream()
                .map(Event::toString)
                .reduce((a, b) -> a + ", " + b)
                .orElse("") + "]";

        assertCommandSuccess(new ListEventsCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noEvents_showsNoEventsMessage() {
        String expectedMessage = "No events found";
        assertCommandSuccess(new ListEventsCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_singleEvent_showsSingleEvent() {
        Event singleEvent = getTypicalEvents().get(0);
        model.addEvent(singleEvent);
        expectedModel.addEvent(singleEvent);

        String expectedMessage = "Events: [" + singleEvent.toString() + "]";
        assertCommandSuccess(new ListEventsCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ListEventsCommand listEventsCommand = new ListEventsCommand();

        // same object -> returns true
        assertTrue(listEventsCommand.equals(listEventsCommand));

        // same type -> returns true
        assertTrue(listEventsCommand.equals(new ListEventsCommand()));

        // different types -> returns false
        assertFalse(listEventsCommand.equals(new ListCommand()));

        // null -> returns false
        assertFalse(listEventsCommand.equals(null));
    }
}
