package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

class DayTest {

    @Test
    void invalid() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));

        Assert.assertThrows(ParseException.class, () -> new Day("wrongday"));
    }

    @Test
    void valid() throws ParseException {
        String dayString1 = "monday";
        Day day = new Day(dayString1);

        assertTrue(day.getStringRepresentation().equals(dayString1));
        assertTrue(day.getNumberRepresentation() == 0);

        String dayString2 = "saturday";
        day.setValue(dayString2);

        assertTrue(day.getStringRepresentation().equals(dayString2));
        assertTrue(day.getNumberRepresentation() == 5);

        String dayString3 = "sunday";
        day.setValue(dayString3);

        assertTrue(day.getStringRepresentation().equals(dayString3));
        assertTrue(day.getNumberRepresentation() == 6);

        String dayString4 = "tuesday";
        day.setValue(dayString4);

        assertTrue(day.getStringRepresentation().equals(dayString4));
        assertTrue(day.getNumberRepresentation() == 1);
    }


}
