package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IsFriendPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ListFriendsCommand}.
 */
public class ListFriendsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidIndexUnfilteredListSuccess() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        ListFriendsCommand listFriendsCommand = new
                ListFriendsCommand(INDEX_FIRST);
        String expectedMessage = String.format(
                ListFriendsCommand.MESSAGE_LIST_FRIENDS_SUCCESS,
                targetPerson.getName());

        IsFriendPredicate predicate = new IsFriendPredicate(targetPerson);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(listFriendsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ListFriendsCommand listFriendsCommand = new
                ListFriendsCommand(outOfBoundIndex);

        assertCommandFailure(listFriendsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListFriendsCommand listFriendsCommand1 = new
                ListFriendsCommand(INDEX_FIRST);
        ListFriendsCommand listFriendsCommand2 = new
                ListFriendsCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(listFriendsCommand1.equals(listFriendsCommand1));

        // same values -> returns true
        ListFriendsCommand listFriendsCommand1Copy = new
                ListFriendsCommand(INDEX_FIRST);
        assertTrue(listFriendsCommand1.equals(listFriendsCommand1Copy));

        // different types -> returns false
        assertFalse(listFriendsCommand1.equals(1));

        // null -> returns false
        assertFalse(listFriendsCommand1 == null);

        // different person -> returns false
        assertFalse(listFriendsCommand1.equals(listFriendsCommand2));
    }
}
