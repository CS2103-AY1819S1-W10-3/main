//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;

public class AddPollOptionCommandTest {
    private static final String POLLNAME = EventBuilder.DEFAULT_POLL;
    private static final String OPTION_NAME = "Generic option";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_acceptedAddPollOption() {
        Index index = TypicalIndexes.INDEX_FIRST;
        AddPollOptionCommand command = new AddPollOptionCommand(index, OPTION_NAME);
        model.setSelectedEvent(model.getEvent(index));
        model.setCurrentUser(ALICE);
        model.addPoll(POLLNAME);
        String expectedMessage = String.format(AddPollOptionCommand.MESSAGE_SUCCESS, OPTION_NAME, index.getOneBased());
        expectedModel.setSelectedEvent(expectedModel.getEvent(index));
        expectedModel.setCurrentUser(ALICE);
        expectedModel.addPoll(POLLNAME);
        expectedModel.addPollOption(index, OPTION_NAME);
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noEventAddPollOption() {
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        VoteCommand command = new VoteCommand(TypicalIndexes.INDEX_FIRST, OPTION_NAME);
        String expectedMessage = Messages.MESSAGE_NO_EVENT_SELECTED;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noPollAddPollOption() {
        AddPollOptionCommand command = new AddPollOptionCommand(TypicalIndexes.INDEX_FIRST, OPTION_NAME);
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.build();
        Person user = new PersonBuilder().build();
        model.setCurrentUser(user);
        model.setSelectedEvent(event);
        String expectedMessage = Messages.MESSAGE_NO_POLL_AT_INDEX;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
