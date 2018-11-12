package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a schedule of a person, reflected as a 24h x 2-30min x 7 days - weekly schedule.
 *
 * @author adjscent
 */
public class Schedule {
    /**
     * Example
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     */

    public static final String MESSAGE_SCHEDULE_CONSTRAINTS =
        "Schedule should be in 0 or 1s and in correct length";
    public static final String INVALID_MESSAGE_SCHEDULE = "Invalid Schedule Input";
    private static final int DAY = 7;
    private static final int HOUR = 48;
    private static final int TOTAL = DAY * HOUR;

    // 7 days 24 hours - 48 30mins
    private final int[][] value;

    public Schedule() {
        value = new int[DAY][HOUR];
        assert (value != null);
    }

    public Schedule(String schedule) {
        requireNonNull(schedule);

        value = new int[DAY][HOUR];
        assert (value != null);
        assert (isValidSchedule(schedule));

        for (int i = 0, counter = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                value[i][j] = Integer.parseInt(schedule.charAt(counter) + "");
                counter += 1;
            }
        }
    }

    public static boolean isValidSchedule(String trimmedSchedule) {
        return trimmedSchedule.length() == TOTAL;
    }

    /**
     * Accesses the internal schedule and check if specified day and time is occupied
     *
     * @param slot
     * @return isoccupied
     */
    public boolean getTimeDay(Slot slot) {
        // day Monday
        // time 0800
        assert (slot != null);
        return value[slot.getDay().getNumberRepresentation()]
            [slot.getTime().getNumberRepresentation()] == 1 ? true : false;
    }

    /**
     * Check the internal schedule and set the vacancy of a specific day and time
     *
     * @param slot
     * @param isoccupied
     */
    public void setTimeDay(Slot slot, boolean isoccupied) throws ParseException {
        // day Monday
        // time 0800
        assert (slot != null);
        value[slot.getDay().getNumberRepresentation()]
            [slot.getTime().getNumberRepresentation()] = isoccupied ? 1 : 0;
    }

    /**
     * Allow schedule array to be stored as a string
     *
     * @return
     */
    public String valueToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                sb.append(value[i][j]);
            }
        }
        return sb.toString();
    }

    /**
     * ORs the Schedules
     *
     * @param schedule
     */
    public void union(Schedule schedule) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] = (this.value[i][j] | schedule.value[i][j]);
            }
        }
    }

    /**
     * Use the updateschedule as a bit flipper
     */
    public void xor(Schedule updateSchedule) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] ^= (updateSchedule.value[i][j]);
            }
        }
    }

    /**
     * Maxs all possible schedules supplied as parameter
     *
     * @param schedules
     * @return
     */
    public static Schedule maxSchedule(Schedule... schedules) {
        Schedule newSchedule = new Schedule();

        // using for each loop to display contents of a
        for (Schedule s : schedules) {
            newSchedule.union(s);
        }

        return newSchedule;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Schedule // instanceof handles nulls
            && Arrays.deepEquals(this.value, ((Schedule) other).value)); // state check
    }

    /**
     * For poll methods. Return an arraylist of slots of free time
     *
     * @return
     * @throws ParseException
     */
    public ArrayList<Slot> getFreeSlots() {
        ArrayList<Slot> slots = new ArrayList<Slot>();

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                if (this.value[i][j] == 0) {
                    Slot slot = new Slot(i, j);
                    slots.add(slot);
                }
            }
        }

        return slots;
    }

    /**
     * For poll methods. Return an arraylist of slots of free time by day
     *
     * @return
     * @throws ParseException
     */
    public ArrayList<Slot> getFreeSlotsByDay(int day) {
        ArrayList<Slot> slots = getFreeSlots();
        ArrayList<Slot> filteredSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getDay().getStringRepresentation()
                .equalsIgnoreCase(Day.VALIDDAYS[(day - 1)])) {
                filteredSlots.add(slot);
            }
        }

        return filteredSlots;
    }

    /**
     * For poll methods. Return an arraylist of slots of free time by time
     *
     * @return
     * @throws ParseException
     */
    public ArrayList<Slot> getFreeSlotsByTime(Time startTime, Time endTime) {
        ArrayList<Slot> slots = getFreeSlots();
        ArrayList<Slot> filteredSlots = new ArrayList<>();

        for (Slot slot : slots) {
            int time = slot.getTime().getComparsionRepresentation();
            if (time >= startTime.getComparsionRepresentation()
                && time <= endTime.getComparsionRepresentation()) {
                filteredSlots.add(slot);
            }
        }
        return filteredSlots;
    }

    /**
     * Pretty Print free time by time to string
     *
     * @return
     * @throws ParseException
     */
    public String freeTimeToStringByTime(Time startTime, Time endTime) {
        StringBuilder sb = new StringBuilder();
        getFreeSlotsByTime(startTime, endTime)
            .forEach((slot) -> sb.append(slot.getDay()
                .getStringRepresentation() + "," + slot.getTime()
                .getStringRepresentation() + "; "));
        return sb.toString().trim();
    }

    /**
     * Pretty Print free time to strig
     *
     * @return
     * @throws ParseException
     */
    public String freeTimeToString() {
        StringBuilder sb = new StringBuilder();
        getFreeSlots().forEach((slot) -> sb.append(slot.getDay()
            .getStringRepresentation() + "," + slot.getTime()
            .getStringRepresentation() + "; "));
        return sb.toString().trim();
    }

    /**
     * Pretty printing into table format
     *
     * @return
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<th></th>");
        for (int i = 0; i < 7; i++) {
            sb.append("<th>" + Day.VALIDDAYS[i].substring(0, 3) + "</th>");
        }
        sb.append("</tr>");
        int oddatinator = 0;
        int hourcounter = 0;
        for (int i = 0; i < value[0].length; i++) {
            sb.append("<tr>");
            if (oddatinator % 2 == 0) {
                sb.append("<td>" + String.format("%02d", hourcounter) + "00</td>");
                oddatinator++;
            } else {
                sb.append("<td>" + String.format("%02d", hourcounter) + "30</td>");
                hourcounter++;
                oddatinator++;
            }


            for (int j = 0; j < value.length; j++) {

                if (this.value[j][i] == 1) {
                    sb.append("<td class='table-danger'> </td>");
                } else {
                    sb.append("<td> </td>");
                }
            }
            sb.append("</tr>");
        }
        return sb.toString();
    }
}
