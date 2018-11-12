package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class SlotTest {

    @Test
    void testMethods() throws ParseException {
        Slot slot = new Slot("tuesday", "0800");
        assertTrue(slot.getDay().getStringRepresentation().equals("tuesday"));
        assertTrue(slot.getTime().getStringRepresentation().equals("0800"));
    }
}

