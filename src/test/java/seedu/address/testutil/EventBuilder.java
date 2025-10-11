package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.event.EventName;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Taylor Swift Concert 2025";
    public static final String DEFAULT_ALIAS = "TS2025";
    public static final LocalDateTime DEFAULT_START = LocalDateTime.of(2024, 12, 25, 10, 0);
    public static final LocalDateTime DEFAULT_END = LocalDateTime.of(2024, 12, 25, 11, 0);
    public static final String DEFAULT_DESCRIPTION = "Located in Indoor Sports Stadium";

    private EventName name;
    private EventAlias alias;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        alias = new EventAlias(DEFAULT_ALIAS);
        start = DEFAULT_START;
        end = DEFAULT_END;
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getEventName();
        alias = eventToCopy.getEventAlias();
        start = eventToCopy.getStart();
        end = eventToCopy.getEnd();
        description = eventToCopy.getDescription();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
        return this;
    }

    /**
     * Sets the {@code start} of the {@code Event} that we are building.
     */
    public EventBuilder withStart(LocalDateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Sets the {@code end} of the {@code Event} that we are building.
     */
    public EventBuilder withEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Builds the {@code Event} with the current details.
     */
    public Event build() {
        return new Event(name, alias, start, end, description);
    }
}
