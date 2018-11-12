package seedu.address.model.person;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a time slot, consisting of a day and time.
 *
 * @author adjscent
 */
public class Slot {
    private Day day;
    private Time time;

    /**
     * Create a slot with day and time input
     *
     * @param day
     * @param time
     * @throws ParseException
     */
    public Slot(String day, String time) throws ParseException {
        this.day = new Day(day);
        this.time = new Time(time);
    }


    /**
     * Internal use only
     * Create a slot with day and time input
     *
     * @param day
     * @param time
     */
    Slot(int day, int time) {
        try {
            this.day = new Day(Day.VALIDDAYS[day]);

            if (time / 2 > 9) {
                this.time = new Time("" + time / 2 + ((time % 2 == 1) ? "30" : "00"));
            } else {
                this.time = new Time("0" + time / 2 + ((time % 2 == 1) ? "30" : "00"));
            }

        } catch (ParseException e) {
            // Note this is only used for internal ops.
            // No errors should occur
            e.printStackTrace();
        }
    }

    /**
     * Returns the day
     *
     * @return
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets the day
     *
     * @param day
     */
    public void setDay(String day) throws ParseException {
        this.day = new Day(day);
    }

    /**
     * Returns time
     *
     * @return
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets time
     *
     * @param time
     */
    public void setTime(String time) throws ParseException {
        this.time = new Time(time);
    }
}

