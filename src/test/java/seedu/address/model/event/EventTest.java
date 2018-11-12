package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.model.event.exceptions.UserNotJoinedEventException;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalIndexes;

public class EventTest {

    @Test
    public void eventCopy_success() throws UserNotJoinedEventException {
        Event event = TypicalEvents.MEETING;
        event.addParticipant(ALICE);
        event.addPoll("Generic poll");
        event.addOptionToPoll(TypicalIndexes.INDEX_FIRST, "Generic option");
        event.addVoteToPoll(TypicalIndexes.INDEX_FIRST, ALICE, "Generic option");
        Event eventCopy = event.getCopy();
        assertEquals(event, eventCopy);
    }
}
