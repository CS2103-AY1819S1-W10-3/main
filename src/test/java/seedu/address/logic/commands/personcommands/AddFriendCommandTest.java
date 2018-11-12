package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertFriendInList;
import static seedu.address.logic.commands.CommandTestUtil.assertFriendNotInList;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.BOB;
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
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains integration tests with the Model and unit tests for {@code AddFriendCommand}.
 *
 * @author agendazhang
 */
public class AddFriendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidIndexUnfilteredListAddNotYetFriendsSuccess() throws CommandException {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        Person friendToAdd = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased()));
        String expectedMessage = String.format(AddFriendCommand.MESSAGE_ADD_FRIEND_SUCCESS,
                friendToAdd.getName(), personToEdit.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person personToEditCopy = new Person(personToEdit);
        personToEditCopy.addFriendInList(friendToAdd);

        expectedModel.updatePerson(personToEdit, personToEditCopy);
        expectedModel.setCurrentUser(personToEditCopy);
        expectedModel.authenticateUser(personToEditCopy);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addFriendCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddFriendCommand addFriendCommand = new AddFriendCommand(outOfBoundIndexes);

        assertCommandFailure(addFriendCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeSameIndexUnfilteredListThrowsCommandException() {
        Index sameIndexes = Index.fromZeroBased(INDEX_SECOND.getZeroBased());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        AddFriendCommand addFriendCommand = new AddFriendCommand(sameIndexes);

        assertCommandFailure(addFriendCommand, model, commandHistory, Messages.MESSAGE_CANNOT_ADD_FRIEND_OWNSELF);
    }

    @Test
    public void checkUpdatedFriendListsDueToEditedPerson() throws CommandException {
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased()));
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        addFriendCommand.execute(model, commandHistory);

        Person editedPerson = BOB;
        EditUserCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        Person friendAdded = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        model.setCurrentUser(friendAdded);
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);
        editUserCommand.execute(model, commandHistory);

        Person personToCheck = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        assertFriendInList(personToCheck, BOB);
    }

    @Test
    public void checkUpdatedFriendListsDueToDeletedPerson() throws CommandException {
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased()));
        Person personToEdit = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(personToEdit);
        addFriendCommand.execute(model, commandHistory);

        Person friendAdded = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        model.setCurrentUser(friendAdded);
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand();
        deleteUserCommand.execute(model, commandHistory);
        Person personToCheck = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        assertFriendNotInList(personToCheck, BOB);
    }

    @Test
    public void equals() {
        AddFriendCommand addFriendFirstCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased()));
        AddFriendCommand addFriendSecondCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased()));

        // same object -> returns true
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommand));

        // same values -> returns true
        AddFriendCommand addFriendFirstCommandCopy = new AddFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased()));
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFriendFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(addFriendFirstCommand.equals(addFriendSecondCommand));
    }
}
