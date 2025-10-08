package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    private final EventName name;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final String description;

    /**
     * Every field must be present and not null.
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

    public String getName() {
        return this.name.toString();
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns true if both events have the same name, start and end.
     */
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

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", description='" + description + '\'' +
                '}';
    }
}
