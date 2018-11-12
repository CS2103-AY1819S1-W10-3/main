//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalIndexes;

public class DisplayPollCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_acceptedDisplayPoll() {
        DisplayPollCommand command = new DisplayPollCommand(TypicalIndexes.INDEX_FIRST);
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.withPoll().build();
        model.setSelectedEvent(event);
        String expectedMessage = String.format(DisplayPollCommand.MESSAGE_SUCCESS,
                TypicalIndexes.INDEX_FIRST.getOneBased(), event);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noEventDisplayPoll() {
        DisplayPollCommand command = new DisplayPollCommand(TypicalIndexes.INDEX_FIRST);
        String expectedMessage = Messages.MESSAGE_NO_EVENT_SELECTED;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noPollDisplayPoll() {
        DisplayPollCommand command = new DisplayPollCommand(TypicalIndexes.INDEX_FIRST);
        EventBuilder eventBuilder = new EventBuilder();
        Event event = eventBuilder.build();
        model.setSelectedEvent(event);
        String expectedMessage = Messages.MESSAGE_NO_POLL_AT_INDEX;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
