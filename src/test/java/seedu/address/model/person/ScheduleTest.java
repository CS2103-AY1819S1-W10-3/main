package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

class ScheduleTest {

    private String emptyScheduleString = "0000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "00000000";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    public void isValidSchedule() {
        assertTrue(Schedule.isValidSchedule(VALID_SCHEDULE));
    }

    @Test
    public void getTimeDay() throws ParseException {
        Schedule s = new Schedule();
        assertFalse(s.getTimeDay("monday", "0100"));
        assertFalse(s.getTimeDay("tuesday", "0100"));
        assertFalse(s.getTimeDay("wednesday", "0100"));
        assertFalse(s.getTimeDay("thursday", "0100"));
        assertFalse(s.getTimeDay("friday", "0100"));
        assertFalse(s.getTimeDay("saturday", "0100"));
        assertFalse(s.getTimeDay("sunday", "0100"));
        Assert.assertThrows(ParseException.class, () -> s
            .setTimeDay("unknown", "0130", true)
        );
        assertFalse(s.getTimeDay("monday", "0130"));

    }

    @Test
    public void setTimeDay() throws ParseException {
        Schedule s = new Schedule();
        s.setTimeDay("monday", "0100", true);
        s.setTimeDay("monday", "0130", true);
        Assert.assertThrows(ParseException.class, () -> s
            .setTimeDay("unknown", "0130", true)
        );
    }

    @Test
    void valueToString() {
        Schedule s1 = new Schedule();
        assertTrue(s1.valueToString().equals(emptyScheduleString));

        Schedule s2 = new Schedule(emptyScheduleString);
        assertTrue(s2.valueToString().equals(emptyScheduleString));
    }

    @Test
    public void maxSchedule() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.maxSchedule(s2);
        assertTrue(s1.valueToString().equals(s2.valueToString()));
    }


    @Test
    public void xor() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.xor(s2);
        assertTrue(s1.valueToString().equals(emptyScheduleString));
    }

    @Test
    void freeTimeToString() throws ParseException {
        Schedule s1 = new Schedule();
        s1.setTimeDay("monday", "1100", true);
        s1.setTimeDay("monday", "0130", true);

        String ft = s1.freeTimeToString();
        assertTrue(ft.contains("monday"));

        String p = s1.prettyPrint();
        assertTrue(p.contains("mon"));

        //ArrayList<Slot> slots = s1.getFreeSlotsByDay(1);
        //assertTrue(slots.size() == 24 * 2 - 2);
    }

    @Test
    void getFreeSlots() {
    }

    @Test
    void getFreeSlotsByDay() {
    }

    @Test
    void prettyPrint() {
    }


}
