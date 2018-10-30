package seedu.address.model.person;

/**
 *
 * Represents a timeslot in NUSmod, consisting of a day and time.
 * @author adjscent
 */
public class Slot {
    private String day;
    private String time;

    /**
     * @return
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }
}
