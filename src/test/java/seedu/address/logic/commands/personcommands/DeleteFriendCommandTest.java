package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FORTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;

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
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased(), INDEX_THIRD.getZeroBased()));
        assertCommandFailure(deleteFriendCommand, model, commandHistory, Messages.MESSAGE_NOT_FRIENDS);
    }

    @Test
    public void executeValidIndexUnfilteredListDeleteAreFriendsSuccess() throws CommandException {
        Person person1 = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        Person person2 = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());

        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased(),
                INDEX_THIRD.getZeroBased()));
        addFriendCommand.execute(model, commandHistory);
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased(), INDEX_THIRD.getZeroBased()));
        String expectedMessage = String.format(DeleteFriendCommand.MESSAGE_DELETE_FRIEND_SUCCESS,
                person1.getName(), person2.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person person1Copy = new Person(person1);
        Person person2Copy = new Person(person2);
        AddFriendCommand.addFriendEachOther(person1Copy, person2Copy);
        expectedModel.updatePerson(person1, person1Copy);
        expectedModel.updatePerson(person2, person2Copy);
        expectedModel.commitAddressBook();

        Person person1CopyCopy = new Person(person1Copy);
        Person person2CopyCopy = new Person(person2Copy);
        DeleteFriendCommand.deleteFriendEachOther(person1Copy, person2Copy);
        expectedModel.updatePerson(person1Copy, person1CopyCopy);
        expectedModel.updatePerson(person2Copy, person2CopyCopy);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteFriendCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1,
                model.getFilteredPersonList().size() + 2);
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(outOfBoundIndexes);

        assertCommandFailure(deleteFriendCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeSameIndexUnfilteredListThrowsCommandException() {
        Index sameIndexes = Index.fromOneBased(INDEX_SECOND.getZeroBased(), INDEX_SECOND.getZeroBased());
        DeleteFriendCommand deleteFriendCommand = new DeleteFriendCommand(sameIndexes);

        assertCommandFailure(deleteFriendCommand, model, commandHistory, Messages.MESSAGE_CANNOT_ADD_FRIEND_OWNSELF);
    }

    @Test
    public void equals() {
        DeleteFriendCommand deleteFriendFirstCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased(), INDEX_THIRD.getZeroBased()));
        DeleteFriendCommand deleteFriendSecondCommand = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_THIRD.getZeroBased(), INDEX_FORTH.getZeroBased()));

        // same object -> returns true
        assertTrue(deleteFriendFirstCommand.equals(deleteFriendFirstCommand));

        // same values -> returns true
        DeleteFriendCommand deleteFriendFirstCommandCopy = new DeleteFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased(), INDEX_THIRD.getZeroBased()));
        assertTrue(deleteFriendFirstCommand.equals(deleteFriendFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFriendFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(deleteFriendFirstCommand.equals(deleteFriendSecondCommand));
    }
}
