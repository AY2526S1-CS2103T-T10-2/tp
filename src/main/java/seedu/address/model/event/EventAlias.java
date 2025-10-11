package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a short alias or code name for an event
 * Guarantees: immutable; is valid as declared in {@link #isValidAlias(String)}
 */
public class EventAlias {

    public static final String MESSAGE_CONSTRAINTS =
        "Aliases should be 1-20 characters long, alphanumeric, may include hyphens (-) or underscores (_), "
        + "and should not be blank.";

    public static final String VALIDATION_REGEX = "^[A-Za-z0-9_-]{1,20}$";

    public final String value;

    /**
     * Constructs a {@code EventAlias}.
     *
     * @param alias A valid event alias.
     */
    public EventAlias(String alias) {
        requireNonNull(alias);
        String aliasTrimmed = alias.trim();
        checkArgument(isValidAlias(aliasTrimmed), MESSAGE_CONSTRAINTS);
        this.value = aliasTrimmed;
    }

    public static boolean isValidAlias(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EventAlias
                && value.equals(((EventAlias) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
