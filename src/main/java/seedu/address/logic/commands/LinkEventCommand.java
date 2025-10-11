package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ALIAS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.person.Person;

/**
 * Links a Person Contact to an Event
 */
public class LinkEventCommand extends Command {
    public static final String COMMAND_WORD = "link-event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a person to an event using its alias. "
            + "Parameters: INDEX " + PREFIX_EVENT_ALIAS + "EVENT_ALIAS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_ALIAS + "TSC2025";

    public static final String MESSAGE_SUCCESS = "Linked %1$s to event: %2$s";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found.";

    private final Index index;
    private final EventAlias eventAlias;

    /**
     * Creates a LinkEventCommand to link a person to an event
     */
    public LinkEventCommand(Index index, EventAlias eventAlias) {
        requireNonNull(index);
        requireNonNull(eventAlias);
        this.index = index;
        this.eventAlias = eventAlias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Find the event by alias (this is case insensitive)
        Event event = model.getAddressBook().getEventList().stream()
                .filter(e -> e.getAlias().equalsIgnoreCase(eventAlias.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_EVENT_NOT_FOUND));

        // Create a new LINKED person
        Person linkedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                event
        );

        model.setPerson(personToEdit, linkedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName(), event.getName()));
    }
}
