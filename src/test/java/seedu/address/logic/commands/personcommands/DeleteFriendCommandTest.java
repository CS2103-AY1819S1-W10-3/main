package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests with the Model and unit tests for {@code DeleteFriendCommand}.
 *
 * @author agendazhang
 */
public class DeleteFriendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidIndexUnfilteredListDeleteNotYetFriendsFailure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_THIRD.getZeroBased()));
        assertCommandFailure(deleteFriendCommand, model, commandHistory,
                String.format(Messages.MESSAGE_NOT_FRIENDS, personToDelete.getName(), personToEdit.getName()));
    }

    @Test
    public void executeValidIndexUnfilteredListDeleteAreFriendsSuccess() throws CommandException {
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased()));
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        addFriendCommand.execute(model, commandHistory);

        Person friendToDelete = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_THIRD.getZeroBased()));
        String expectedMessage = String.format(DeleteFriendCommand.MESSAGE_DELETE_FRIEND_SUCCESS,
                friendToDelete.getName(), personToEdit.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person personToEditCopy = new Person(personToEdit);
        personToEditCopy.addFriendInList(friendToDelete);
        expectedModel.updatePerson(personToEdit, personToEditCopy);
        expectedModel.setCurrentUser(personToEditCopy);
        expectedModel.authenticateUser(personToEditCopy);
        expectedModel.commitAddressBook();

        Person personToEditCopyCopy = new Person(personToEditCopy);
        personToEditCopyCopy.deleteFriendInList(friendToDelete);

        expectedModel.updatePerson(personToEditCopy, personToEditCopyCopy);
        expectedModel.setCurrentUser(personToEditCopyCopy);
        expectedModel.authenticateUser(personToEditCopyCopy);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deleteFriendCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(outOfBoundIndexes);

        assertCommandFailure(deleteFriendCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeSameIndexUnfilteredListThrowsCommandException() {
        Index sameIndexes = Index.fromZeroBased(INDEX_SECOND.getZeroBased());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(sameIndexes);

        assertCommandFailure(deleteFriendCommand, model, commandHistory, Messages.MESSAGE_CANNOT_ADD_FRIEND_OWNSELF);
    }

    @Test
    public void equals() {
        DeleteFriendCommand deleteFriendFirstCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased()));
        DeleteFriendCommand deleteFriendSecondCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_THIRD.getZeroBased()));

        // same object -> returns true
        assertTrue(deleteFriendFirstCommand.equals(deleteFriendFirstCommand));

        // same values -> returns true
        DeleteFriendCommand deleteFriendFirstCommandCopy = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased()));
        assertTrue(deleteFriendFirstCommand.equals(deleteFriendFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFriendFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(deleteFriendFirstCommand.equals(deleteFriendSecondCommand));
    }
}
