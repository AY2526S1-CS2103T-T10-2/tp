package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventsCommand extends Command {

    public static final String COMMAND_WORD = "list-events";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        
        List<Event> events = model.getFilteredEventList();
        String eventsJson = events.stream()
                .map(Event::toString)
                .collect(Collectors.joining(", "));
        
        String resultMessage = events.isEmpty() 
            ? "No events found" 
            : "Events: [" + eventsJson + "]";
            
        return new CommandResult(resultMessage);
    }
}
