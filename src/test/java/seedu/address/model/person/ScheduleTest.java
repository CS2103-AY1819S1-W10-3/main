package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
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

        Slot slot1 = new Slot("monday", "0100");
        assertFalse(s.getTimeDay(slot1));

        slot1.setDay("tuesday");
        assertFalse(s.getTimeDay(slot1));

        slot1.setDay("wednesday");
        assertFalse(s.getTimeDay(slot1));

        slot1.setDay("thursday");
        assertFalse(s.getTimeDay(slot1));

        slot1.setDay("friday");
        assertFalse(s.getTimeDay(slot1));

        slot1.setDay("saturday");
        assertFalse(s.getTimeDay(slot1));

        slot1.setDay("sunday");
        assertFalse(s.getTimeDay(slot1));

        slot1.setTime("0130");
        assertFalse(s.getTimeDay(slot1));

    }

    @Test
    public void setTimeDay() throws ParseException {
        Schedule s = new Schedule();
        Slot slot1 = new Slot("monday", "0100");
        Slot slot2 = new Slot("monday", "0130");
        s.setTimeDay(slot1, true);
        s.setTimeDay(slot2, true);
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
        Slot slot1 = new Slot("monday", "1100");
        Slot slot2 = new Slot("monday", "0130");

        s1.setTimeDay(slot1, true);
        s1.setTimeDay(slot2, true);

        String ft = s1.freeTimeToString();
        assertTrue(ft.contains("monday"));

        String p = s1.prettyPrint();
        assertTrue(p.contains("mon"));

        //ArrayList<Slot> slots = s1.getFreeSlotsByDay(1);
        //assertTrue(slots.size() == 24 * 2 - 2);
    }

    @Test
    void valid() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        assertEquals(s1.getFreeSlotsByDay(1).size(), 48);
        assertTrue(s1.equals(s2));
    }


}
