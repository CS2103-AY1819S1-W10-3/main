package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a schedule of a person, reflected as a 24h x 2-30min x 7 days - weekly schedule.
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
    }

    public Schedule(String schedule) {
        requireNonNull(schedule);

        value = new int[DAY][HOUR];

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
     * Accesses the internal schedule and check if specified day and time is free
     * @param day
     * @param time
     * @return
     */
    public boolean getTimeDay(String day, String time) throws ParseException {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day);


        int hourNum = Integer.parseInt(time.substring(0, 2));

        int minNum = Integer.parseInt(time.substring(2, 4));

        int timeNum = hourNum * 2;

        if (minNum >= 30) {
            return value[dayNum][timeNum + 1] == 1 ? true : false;
        } else {
            return value[dayNum][timeNum] == 1 ? true : false;
        }


    }

    /**
     * Check the internal schedule and set the vacancy of a specific day and time
     * @param day
     * @param time
     * @param occupied
     */
    public void setTimeDay(String day, String time, boolean occupied) throws ParseException {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day);

        int timeNum = -1;

        timeNum = Integer.parseInt(time.substring(0, 2)) * 2;

        int minNum = Integer.parseInt(time.substring(2, 4));

        if (minNum >= 30) {
            value[dayNum][timeNum + 1] = occupied ? 1 : 0;
        } else {
            value[dayNum][timeNum] = occupied ? 1 : 0;
        }

    }

    private int getDayNum(String day) throws ParseException {
        int dayNum = -1;
        switch (day.toLowerCase()) {
        case "monday":
            dayNum = 0;
            break;
        case "tuesday":
            dayNum = 1;
            break;
        case "wednesday":
            dayNum = 2;
            break;
        case "thursday":
            dayNum = 3;
            break;
        case "friday":
            dayNum = 4;
            break;
        case "saturday":
            dayNum = 5;
            break;
        case "sunday":
            dayNum = 6;
            break;
        default:
            throw new ParseException(INVALID_MESSAGE_SCHEDULE);
        }
        return dayNum;
    }

    private String getNumDay(int dayNum) throws ParseException {
        String day;
        switch (dayNum) {
        case 0:
            day = "monday";
            break;
        case 1:
            day = "tuesday";
            break;
        case 2:
            day = "wednesday";
            break;
        case 3:
            day = "thursday";
            break;
        case 4:
            day = "friday";
            break;
        case 5:
            day = "saturday";
            break;
        case 6:
            day = "sunday";
            break;
        default:
            throw new ParseException(INVALID_MESSAGE_SCHEDULE);
        }
        return day;
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

    /**
     * ORs the Schedules
     *
     * @param schedule
     */
    private void union(Schedule schedule) {
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
     * For poll methods. Return an arraylist of slots of free time
     * @return
     * @throws ParseException
     */
    public ArrayList<Slot> getFreeSlots() {
        ArrayList<Slot> slots = new ArrayList<Slot>();

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                if (this.value[i][j] == 0) {
                    Slot slot = new Slot();
                    try {
                        slot.setDay(getNumDay(i));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (j / 2 > 9) {
                        slot.setTime("" + j / 2 + ((j % 2 == 1) ? "30" : "00"));
                    } else {
                        slot.setTime("0" + j / 2 + ((j % 2 == 1) ? "30" : "00"));
                    }
                    slots.add(slot);
                }
            }
        }

        return slots;
    }

    /**
     * For poll methods. Return an arraylist of slots of free time by day
     * @return
     * @throws ParseException
     */
    public ArrayList<Slot> getFreeSlotsByDay(int day) {
        ArrayList<Slot> slots = getFreeSlots();
        ArrayList<Slot> filteredSlots = new ArrayList<>();
        for (Slot slot : slots) {
            try {
                if (slot.getDay().equalsIgnoreCase(getNumDay(day - 1))) {
                    filteredSlots.add(slot);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return slots;
    }

    /**
     * @return
     * @throws ParseException
     */
    public String freeTimeToString() {
        StringBuilder sb = new StringBuilder();
        getFreeSlots().forEach((slot) -> sb.append(slot.getDay() + "," + slot.getTime() + ";"));
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
            try {
                sb.append("<th>" + getNumDay(i).substring(0, 3) + "</th>");
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && Arrays.equals(this.value, ((Schedule) other).value)); // state check
    }
}
