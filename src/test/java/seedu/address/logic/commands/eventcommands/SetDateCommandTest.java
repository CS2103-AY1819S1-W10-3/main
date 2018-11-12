//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDate;
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

public class SetDateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private LocalDate date = LocalDate.of(2018, 2, 1);

    @Test
    public void execute_dateAcceptedSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        model.setCurrentUser(ALICE);
        Event event = model.getEvent(TypicalIndexes.INDEX_FIRST);
        model.setSelectedEvent(event);
        String expectedMessage = String.format(SetDateCommand.MESSAGE_SUCCESS, date.format(dateFormat), event);
        Event eventEdited = expectedModel.getEvent(TypicalIndexes.INDEX_FIRST);
        eventEdited.setDate(date);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noUserSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        Event event = new EventBuilder().build();
        model.setSelectedEvent(event);
        String expectedMessage = Messages.MESSAGE_NO_USER_LOGGED_IN;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noEventSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        model.setCurrentUser(ALICE);
        String expectedMessage = Messages.MESSAGE_NO_EVENT_SELECTED;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_notEventOrganiserSetDate() {
        SetDateCommand command = new SetDateCommand(date);
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        Person anotherUser = new PersonBuilder(user).withName("Bob").build();
        Event event = new EventBuilder().withOrganiser(anotherUser).build();
        model.setSelectedEvent(event);
        String expectedMessage = Messages.MESSAGE_NOT_EVENT_ORGANISER;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        LocalDate dateOne = LocalDate.of(2018, 1, 1);
        LocalDate dateTwo = LocalDate.of(2018, 2, 2);
        SetDateCommand setDateOneCommand = new SetDateCommand(dateOne);
        SetDateCommand setDateTwoCommand = new SetDateCommand(dateTwo);

        // same object -> returns true
        assertEquals(setDateOneCommand, setDateOneCommand);

        // same values -> returns true
        SetDateCommand setDateOneCommandCopy = new SetDateCommand(dateOne);
        assertEquals(setDateOneCommand, setDateOneCommandCopy);

        // different types -> returns false
        assertNotEquals(setDateOneCommand, 1);

        // null -> returns false
        assertNotEquals(setDateOneCommand, null);

        // different date -> returns false
        assertNotEquals(setDateOneCommand, setDateTwoCommand);
    }
}
