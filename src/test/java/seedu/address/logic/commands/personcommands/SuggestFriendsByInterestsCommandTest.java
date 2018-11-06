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
import seedu.address.model.person.InterestSimilarPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SuggestFriendsByInterestsCommand}.
 */
public class SuggestFriendsByInterestsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        SuggestFriendsByInterestsCommand suggestFriendsByInterestsCommand = new
                SuggestFriendsByInterestsCommand(INDEX_FIRST);
        String expectedMessage = String.format(SuggestFriendsByInterestsCommand.MESSAGE_LIST_SUGGESTED_FRIENDS_SUCCESS,
                targetPerson.getName());

        InterestSimilarPredicate predicate = new InterestSimilarPredicate(targetPerson);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(suggestFriendsByInterestsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SuggestFriendsByInterestsCommand suggestFriendsByInterestsCommand = new
                SuggestFriendsByInterestsCommand(outOfBoundIndex);

        assertCommandFailure(suggestFriendsByInterestsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SuggestFriendsByInterestsCommand suggestFriendsByInterestsCommand1 = new
                SuggestFriendsByInterestsCommand(INDEX_FIRST);
        SuggestFriendsByInterestsCommand suggestFriendsByInterestsCommand2 = new
                SuggestFriendsByInterestsCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(suggestFriendsByInterestsCommand1.equals(suggestFriendsByInterestsCommand1));

        // same values -> returns true
        SuggestFriendsByInterestsCommand suggestFriendsByInterestsCommand1Copy = new
                SuggestFriendsByInterestsCommand(INDEX_FIRST);
        assertTrue(suggestFriendsByInterestsCommand1.equals(suggestFriendsByInterestsCommand1Copy));

        // different types -> returns false
        assertFalse(suggestFriendsByInterestsCommand1.equals(1));

        // null -> returns false
        assertFalse(suggestFriendsByInterestsCommand1 == null);

        // different person -> returns false
        assertFalse(suggestFriendsByInterestsCommand1.equals(suggestFriendsByInterestsCommand2));
    }
}
