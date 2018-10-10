//@@author theJrLinguist
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event TUTORIAL = new EventBuilder().withName("Tutorial")
            .withAddress("NUS UTown")
            .withTags("friends")
            .build();
    public static final Event MEETING = new EventBuilder().withName("Meeting")
            .withAddress("SOC Canteen")
            .withTags("friends")
            .build();
    public static final Event DINNER = new EventBuilder().withName("Dinner")
            .withAddress("Arts Canteen")
            .withTags("friends")
            .build();
    public static final EventBuilder MEETING_BUILDER = new EventBuilder().withName("Meeting")
            .withAddress("SOC Canteen")
            .withTags("friends");
    public static final Event TUTORIAL_WITH_PERSON = new EventBuilder().withName("Tutorial")
            .withAddress("NUS UTown")
            .withTags("friends")
            .withParticipant()
            .build();
    public static final Event MEETING_WITH_PERSON = new EventBuilder().withName("Meeting")
            .withAddress("SOC Canteen")
            .withTags("friends")
            .withParticipant()
            .build();
    public static final Event DINNER_WITH_PERSON = new EventBuilder().withName("Dinner")
            .withAddress("Arts Canteen")
            .withTags("friends")
            .withParticipant()
            .build();

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical events with one participant.
     */
    public static AddressBook getAddressBookWithParticipant() {
        AddressBook ab = new AddressBook();
        for (Event event : getEventsWithParticipants()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(TUTORIAL, MEETING, DINNER));
    }

    public static List<Event> getEventsWithParticipants() {
        return new ArrayList<>(Arrays.asList(TUTORIAL_WITH_PERSON, MEETING_WITH_PERSON, DINNER_WITH_PERSON));
    }
}
