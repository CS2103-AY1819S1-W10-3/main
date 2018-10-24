package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FORTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AddFriendCommand}.
 */
public class AddFriendCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidIndexUnfilteredListSuccess() throws CommandException {
        Person person1 = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        Person person2 = model.getFilteredPersonList().get(INDEX_THIRD.getZeroBased());
        AddFriendCommand addFriendCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased(),
                INDEX_THIRD.getZeroBased()));
        String expectedMessage = String.format(AddFriendCommand.MESSAGE_ADD_FRIEND_SUCCESS,
                person1.getName(), person2.getName());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person person1copy = new Person(person1);
        person1copy.addFriendInList(person2);
        Person person2copy = new Person(person2);
        person2copy.addFriendInList(person1);
        expectedModel.updatePerson(person1, person1copy, person2, person2copy);
        expectedModel.commitAddressBook();
        String actualMessage = addFriendCommand.execute(model, commandHistory).feedbackToUser;
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Index outOfBoundIndexes = Index.fromOneBased(model.getFilteredPersonList().size() + 1,
                model.getFilteredPersonList().size() + 2);
        AddFriendCommand addFriendCommand = new AddFriendCommand(outOfBoundIndexes);

        assertCommandFailure(addFriendCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddFriendCommand addFriendFirstCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased(),
                INDEX_THIRD.getZeroBased()));
        AddFriendCommand addFriendSecondCommand = new AddFriendCommand(Index.fromZeroBased(INDEX_THIRD.getZeroBased(),
                INDEX_FORTH.getZeroBased()));

        // same object -> returns true
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommand));

        // same values -> returns true
        AddFriendCommand addFriendFirstCommandCopy = new AddFriendCommand(Index.fromZeroBased(
                INDEX_SECOND.getZeroBased(), INDEX_THIRD.getZeroBased()));
        assertTrue(addFriendFirstCommand.equals(addFriendFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFriendFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(addFriendFirstCommand.equals(addFriendSecondCommand));
    }
}
