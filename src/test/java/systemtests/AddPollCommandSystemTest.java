//@@author theJrLinguist
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLL_OPTION;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.eventcommands.AddPollCommand;
import seedu.address.logic.commands.eventcommands.AddPollOptionCommand;
import seedu.address.logic.commands.eventcommands.SelectEventCommand;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.NotEventOrganiserException;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalIndexes;

public class AddPollCommandSystemTest extends AddressBookSystemTest {

    private static final String POLLNAME = "Activity poll";
    private static final String POLL_OPTION = "Chitchat";

    @Test
    public void addPoll() throws NoUserLoggedInException, NoEventSelectedException, NotEventOrganiserException {
        Model model = getModel();

        // ------------------------ Perform add operations on the shown unfiltered list -----------------------------

        String pollName = POLLNAME;
        String command = "   " + AddPollCommand.COMMAND_WORD + "  " + PREFIX_NAME + POLLNAME;

        // Case: no user logged in -> NoUserLoggedInException
        assertCommandFailure(command, Messages.MESSAGE_NO_USER_LOGGED_IN);

        // Case: no event selected -> NoEventSelectedException
        executeCommand(LoginCommand.COMMAND_WORD + " n/Benson Meier pass/password");
        assertCommandFailure(command, Messages.MESSAGE_NO_EVENT_SELECTED);

        // Case: user is not event organiser -> NotEventOrganiserException
        executeCommand(LogoutCommand.COMMAND_WORD);
        executeCommand(SelectEventCommand.COMMAND_WORD + " 2");
        executeCommand(LoginCommand.COMMAND_WORD + " n/Benson Meier pass/password");
        assertCommandFailure(command, Messages.MESSAGE_NOT_EVENT_ORGANISER);

        // Case: add a poll with generic poll name
        //  -> poll added
        executeCommand(LogoutCommand.COMMAND_WORD);
        executeCommand(LoginCommand.COMMAND_WORD + " n/Alice Pauline pass/password");
        assertAddPollCommandSuccess(command, POLLNAME);

        // ----------------------------------- Perform add poll option operations -----------------------------------

        // Case: invalid command format missing index -> rejected
        String addOptionCommand = "  " + AddPollOptionCommand.COMMAND_WORD + "   " + PREFIX_POLL_OPTION + POLL_OPTION;
        assertCommandFailure(addOptionCommand, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddPollOptionCommand.MESSAGE_USAGE));

        // Case: no poll at the given index -> rejected
        addOptionCommand = "  " + AddPollOptionCommand.COMMAND_WORD + "   " + PREFIX_INDEX + "8 "
                + PREFIX_POLL_OPTION + POLL_OPTION;
        assertCommandFailure(addOptionCommand, Messages.MESSAGE_NO_POLL_AT_INDEX);

        // Case: add poll option into poll -> NotEventOrganiserException
        addOptionCommand = "  " + AddPollOptionCommand.COMMAND_WORD + "   " + PREFIX_INDEX + "1 "
                + PREFIX_POLL_OPTION + POLL_OPTION;
        assertAddOptionCommandSuccess(addOptionCommand, TypicalIndexes.INDEX_FIRST, POLL_OPTION);

        // ----------------------------------- Perform invalid add poll operations ----------------------------------

        // Case: missing name -> rejected
        command = AddPollCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));

        // Case: invalid keyword -> rejected
        command = "addPolls " + pollName;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        // Case: invalid name -> rejected
        command = AddPollCommand.COMMAND_WORD + INVALID_EVENT_NAME_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddUserCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertAddPollCommandSuccess(String command, String pollName) throws NoEventSelectedException,
            NoUserLoggedInException, NotEventOrganiserException {

        Model expectedModel = getModel();
        expectedModel.setCurrentUser(ALICE);
        Event event = expectedModel.getEvent(TypicalIndexes.INDEX_SECOND);
        expectedModel.setSelectedEvent(event);
        expectedModel.addPoll(pollName);
        String expectedResultMessage = String.format(AddPollCommand.MESSAGE_SUCCESS, pollName, event);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddUserCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertAddOptionCommandSuccess(String command, Index index, String option) {

        Model expectedModel = getModel();
        String expectedResultMessage = String.format(AddPollOptionCommand.MESSAGE_SUCCESS, option, index.getOneBased());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddUserCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
