package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

class TimeTest {

    @Test
    void invalid() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));

        Assert.assertThrows(ParseException.class, () -> new Time("faketime"));
        Assert.assertThrows(ParseException.class, () -> new Time("2500"));
        Assert.assertThrows(ParseException.class, () -> new Time("2400"));
        Assert.assertThrows(ParseException.class, () -> new Time("xxx0"));
    }


    @Test
    void valid() throws ParseException {
        String time1 = "0000";
        Time time = new Time(time1);

        assertTrue(time.getStringRepresentation().equals(time1));
        assertTrue(time.getNumberRepresentation() == 0);
        assertTrue(time.getComparsionRepresentation() == 0);


        String time2 = "2330";
        time.setValue(time2);

        assertTrue(time.getStringRepresentation().equals(time2));
        assertTrue(time.getNumberRepresentation() == 47);
        assertTrue(time.getComparsionRepresentation() == 2330);

    }

}
