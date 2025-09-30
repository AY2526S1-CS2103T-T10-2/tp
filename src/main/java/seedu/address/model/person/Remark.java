package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {

    public final String value;

    public static final String VALIDATION_REGEX = "\\*";

    public static final String MESSAGE_CONSTRAINTS = "Something went wrong";

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
