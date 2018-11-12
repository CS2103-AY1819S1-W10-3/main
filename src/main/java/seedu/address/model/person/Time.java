package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.Schedule.INVALID_MESSAGE_SCHEDULE;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class to hold time values to be used by Schedule
 */
public class Time {
    private int intValue;
    private String stringValue;
    private int forComparsionValue;

    public Time(String value) throws ParseException {
        setValue(value);
    }

    /**
     * Sets the time value and generates comparsionValue, intValue
     *
     * @param value
     * @throws ParseException
     */
    public void setValue(String value) throws ParseException {
        requireNonNull(value);

        if (value.length() != 4) {
            throw new ParseException(INVALID_MESSAGE_SCHEDULE);
        }

        int timeNum;
        int minNum;

        try {
            timeNum = Integer.parseInt(value.substring(0, 2));
            minNum = Integer.parseInt(value.substring(2, 4));
        } catch (NumberFormatException e) {
            throw new ParseException(INVALID_MESSAGE_SCHEDULE);
        }

        if (timeNum > 23) {
            throw new ParseException(INVALID_MESSAGE_SCHEDULE);
        }

        forComparsionValue = Integer.valueOf(value);
        intValue = (minNum >= 30) ? timeNum * 2 + 1 : timeNum * 2;
        stringValue = value;
    }

    /**
     * Get the comparsion value which is useful for comparing time
     *
     * @return
     */
    public int getComparsionRepresentation() {
        return forComparsionValue;
    }

    /**
     * Get the schedule array representation
     *
     * @return
     */
    public int getNumberRepresentation() {
        return intValue;
    }

    /**
     * Get the string value of the time
     *
     * @return
     */
    public String getStringRepresentation() {
        return stringValue;
    }
}
