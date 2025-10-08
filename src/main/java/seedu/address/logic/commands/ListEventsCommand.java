package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Lists all events in the address book to the user.
 * This command displays all events in JSON format in the console since no UI exists for events yet.
 *
 */
public class ListEventsCommand extends Command {

    /** The command word used to invoke this command. */
    public static final String COMMAND_WORD = "list-events";

    /** Success message displayed when events are listed. */
    public static final String MESSAGE_SUCCESS = "Listed all events";

    /**
     * Executes the list-events command.
     *
     * @param model The model containing the address book data and event management functionality.
     *              Must not be null.
     * @return CommandResult containing either the formatted list of events or a "No events found" message.
     * @throws NullPointerException if model is null.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Update the filtered event list to show all events
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        // Get the current filtered event list
        List<Event> events = model.getFilteredEventList();
        // Convert events to JSON-like string format and join with commas
        String eventsJson = events.stream()
            .map(Event::toString)
            .collect(Collectors.joining(", "));
        // Return appropriate message based on whether events exist
        String resultMessage = events.isEmpty()
            ? "No events found"
            : "Events: [" + eventsJson + "]";
        return new CommandResult(resultMessage);
    }
}
