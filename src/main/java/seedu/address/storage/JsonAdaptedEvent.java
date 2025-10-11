package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAlias;
import seedu.address.model.event.EventName;


/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String alias;
    private final String start;
    private final String end;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
                            @JsonProperty("alias") String alias,
                            @JsonProperty("start") String start,
                            @JsonProperty("end") String end,
                            @JsonProperty("description") String description) {
        this.name = name;
        this.alias = alias;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.name = source.getName();
        this.alias = source.getAlias();
        this.start = source.getStart().toString();
        this.end = source.getEnd().toString();
        this.description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (!EventName.isValidEventName(name)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }

        EventName modelName = new EventName(name);

        if (alias == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "alias"));
        }
        if (!EventAlias.isValidAlias(alias)) {
            throw new IllegalValueException(EventAlias.MESSAGE_CONSTRAINTS);
        }

        EventAlias modelAlias = new EventAlias(alias);

        LocalDateTime modelStart = LocalDateTime.parse(start);
        LocalDateTime modelEnd = LocalDateTime.parse(end);

        return new Event(modelName, modelAlias, modelStart, modelEnd, description);
    }
}
