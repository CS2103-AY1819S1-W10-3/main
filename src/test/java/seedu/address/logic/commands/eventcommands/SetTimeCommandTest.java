//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;

public class SetTimeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private LocalTime startTime = LocalTime.of(18, 00);
    private LocalTime endTime = LocalTime.of(19, 30);

    @Test
    public void execute_timeAcceptedSetTime() {
        SetTimeCommand command = new SetTimeCommand(startTime, endTime);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        model.setCurrentUser(ALICE);
        Event event = model.getFilteredEventList().get(0);
        model.setSelectedEvent(event);
        String expectedMessage = String.format(command.MESSAGE_SUCCESS, startTime.format(timeFormat),
                endTime.format(timeFormat), event);
        Event eventEdited = expectedModel.getEvent(TypicalIndexes.INDEX_FIRST);
        eventEdited.setTime(startTime, endTime);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_endTimeBeforeStartTime() {
        SetTimeCommand command = new SetTimeCommand(endTime, startTime);
        model.setCurrentUser(ALICE);
        Event event = model.getFilteredEventList().get(0);
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_END_BEFORE_START_TIME);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noUserSetTime() {
        SetTimeCommand command = new SetTimeCommand(startTime, endTime);
        Event event = new EventBuilder().build();
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NO_USER_LOGGED_IN);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noEventSetTime() {
        SetTimeCommand command = new SetTimeCommand(startTime, endTime);
        model.setCurrentUser(ALICE);
        String expectedMessage = String.format(Messages.MESSAGE_NO_EVENT_SELECTED);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_notEventOrganiserSetTime() {
        SetTimeCommand command = new SetTimeCommand(startTime, endTime);
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        Person anotherUser = new PersonBuilder(user).withName("Bob").build();
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.withOrganiser(anotherUser);
        Event event = eventBuilder.build();
        model.setSelectedEvent(event);
        String expectedMessage = String.format(Messages.MESSAGE_NOT_EVENT_ORGANISER);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        LocalTime timeOne = LocalTime.of(12, 00);
        LocalTime timeTwo = LocalTime.of(13, 30);
        LocalTime timeThree = LocalTime.of(14, 15);
        SetTimeCommand setTimeCommandOne = new SetTimeCommand(timeOne, timeTwo);
        SetTimeCommand setTimeCommandTwo = new SetTimeCommand(timeTwo, timeThree);

        // same object -> returns true
        assertTrue(setTimeCommandOne.equals(setTimeCommandOne));

        // same values -> returns true
        SetTimeCommand setTimeOneCommandCopy = new SetTimeCommand(timeOne, timeTwo);
        assertTrue(setTimeCommandOne.equals(setTimeOneCommandCopy));

        // different types -> returns false
        assertFalse(setTimeCommandOne.equals(1));

        // null -> returns false
        assertFalse(setTimeCommandOne.equals(null));

        // different time -> returns false
        assertFalse(setTimeCommandOne.equals(setTimeCommandTwo));
    }
}
