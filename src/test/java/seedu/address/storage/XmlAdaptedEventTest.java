//@@author theJrLinguist
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.MEETING_BUILDER;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalPersons;

public class XmlAdaptedEventTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";
    //add illegal values

    private static final String VALID_NAME = MEETING.getName().toString();
    private static final String VALID_ADDRESS = MEETING.getLocation().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = MEETING.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_DATE = MEETING.getDate().toString();
    private static final String VALID_START_TIME = MEETING.getStartTime().toString();
    private static final String VALID_END_TIME = MEETING.getEndTime().toString();
    private static final String VALID_ORGANISER = "0";
    private static final List<XmlPersonIndex> VALID_PERSON_LIST = new ArrayList<>(0);
    private static final List<XmlAdaptedPoll> VALID_POLL_LIST = MEETING.getPolls().stream()
            .map(XmlAdaptedPoll::new)
            .collect(Collectors.toList());

    @Before
    public void initialise() {
        ObservableList<Person> personList = TypicalPersons.getTypicalAddressBook().getPersonList();
        XmlAdaptedEvent.setPersonList(personList);
    }

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        Event event = MEETING_BUILDER.withOrganiser(ALICE).build();
        XmlAdaptedEvent xmlEvent = new XmlAdaptedEvent(event);
        assertEquals(event, xmlEvent.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_ADDRESS, VALID_ORGANISER, VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, VALID_TAGS, VALID_POLL_LIST, VALID_PERSON_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, INVALID_ADDRESS, VALID_ORGANISER, VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, VALID_TAGS, VALID_POLL_LIST, VALID_PERSON_LIST);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_ORGANISER, VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, VALID_TAGS, VALID_POLL_LIST, VALID_PERSON_LIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_ADDRESS, VALID_ORGANISER, VALID_DATE,
                VALID_START_TIME, VALID_END_TIME, invalidTags, VALID_POLL_LIST, VALID_PERSON_LIST);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }
}
