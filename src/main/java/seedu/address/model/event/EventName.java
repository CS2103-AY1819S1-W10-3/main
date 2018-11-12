package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's name in the address book.
 */
public class EventName {

    public static final String MESSAGE_EVENT_NAME_CONSTRAINTS =
            "Event name can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVENT_NAME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code EventName}.
     *
     * @param eventName A valid eventName.
     */
    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidEventName(eventName), MESSAGE_EVENT_NAME_CONSTRAINTS);
        value = eventName;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENT_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && value.equals(((EventName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
