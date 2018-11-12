package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.Schedule.INVALID_MESSAGE_SCHEDULE;

import java.util.Arrays;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class to hold day values to be used by Schedule
 */
public class Day {
    public static final String[] VALIDDAYS =
        {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    private String value;

    public Day(String value) throws ParseException {
        setValue(value);
    }

    /**
     * Sets the day value and checks if it is correct.
     *
     * @param value
     * @throws ParseException
     */
    public void setValue(String value) throws ParseException {
        requireNonNull(value);
        String lcValue = value.toLowerCase();
        if (Arrays.stream(VALIDDAYS).anyMatch(lcValue::equals)) {
            this.value = lcValue;
        } else {
            throw new ParseException(INVALID_MESSAGE_SCHEDULE);
        }
    }

    /**
     * Returns the int for schedule array
     *
     * @return
     */
    public int getNumberRepresentation() {
        for (int i = 0; i < VALIDDAYS.length; i++) {
            if (VALIDDAYS[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        // This part should never be reached.
        return -1;
    }

    /**
     * Returns the string value
     *
     * @return
     */
    public String getStringRepresentation() {
        return value;
    }
}
