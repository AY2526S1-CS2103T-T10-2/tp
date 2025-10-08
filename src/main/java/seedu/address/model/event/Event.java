package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

/**
 * Represents an Event in the address book.
 *
 */
public class Event {
    private final EventName name;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String description;

    /**
     * Constructs an Event with the specified details.
     * @param name The name of the event. Must not be null and must be a valid event name.
     * @param start The start date and time of the event. Must not be null.
     * @param end The end date and time of the event. Must not be null.
     * @param description The description of the event. Must not be null.
     * @throws NullPointerException if any parameter is null.
     * @throws IllegalArgumentException if start time is after end time.
     */
    public Event(EventName name, LocalDateTime start, LocalDateTime end, String description) {
        requireAllNonNull(name, start, end, description);
        this.name = name;
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.start = start;
        this.end = end;
        this.description = description;
    }

    /**
     * Returns the name of the event as a string.
     */
    public String getName() {
        return this.name.toString();
    }

    /**
     * Returns the description of the event.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the start date and time of the event.
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Returns the end date and time of the event.
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     *
     * @param other The object to compare with this event.
     * @return true if the other object is an Event with the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return name.equals(otherEvent.name)
                && start.equals(otherEvent.start)
                && end.equals(otherEvent.end)
                && description.equals(otherEvent.description);
    }

    /**
     * Returns true if both events have the same name, start and end.
     * This defines a weaker notion of equality between two events.
     * @param otherEvent The event to compare with this event.
     * @return true if the other event has the same name, start, and end times.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getStart().equals(getStart())
                && otherEvent.getEnd().equals(getEnd());
    }

    /**
     * Returns a string representation of this event in JSON-like format.
     * The string format is: {@code Event{name='...', start=..., end=..., description='...'}}
     */
    @Override
    public String toString() {
        return "Event{"
                + "name='" + name + '\''
                + ", start=" + start
                + ", end=" + end
                + ", description='" + description + '\''
                + '}';
    }
}
