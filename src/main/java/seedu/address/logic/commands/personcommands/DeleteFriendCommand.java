package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a {@code Person} specified by the index as a friend from the current user's friend list
 *
 * @author agendazhang
 */
public class DeleteFriendCommand extends Command {

    public static final String COMMAND_WORD = "deleteFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To delete a person as friend identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer and different from the logged-in user)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_DELETE_FRIEND_SUCCESS = "%1$s is deleted from %2$s friend list";

    private final Index index;

    public DeleteFriendCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (!model.hasSetCurrentUser()) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        }
        Person personToEdit = model.getCurrentUser();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person friendToDelete = lastShownList.get(index.getZeroBased());
        if (personToEdit.equals(friendToDelete)) {
            throw new CommandException(Messages.MESSAGE_CANNOT_ADD_FRIEND_OWNSELF);
        }
        if (!personToEdit.hasFriendInList(friendToDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_NOT_FRIENDS,
                    friendToDelete.getName(), personToEdit.getName()));
        }

        model.removeCurrentUser();

        Person personToEditCopy = new Person(personToEdit);
        personToEditCopy.deleteFriendInList(friendToDelete);

        model.updatePerson(personToEdit, personToEditCopy);
        model.setCurrentUser(personToEditCopy);
        model.authenticateUser(personToEditCopy);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_FRIEND_SUCCESS, friendToDelete.getName(),
                personToEdit.getName()));
    }

    @Override
    public String toString() {
        return COMMAND_WORD + " " + index.getZeroBased();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFriendCommand // instanceof handles nulls
                && index.equals(((DeleteFriendCommand) other).index)); // state check;
    }
}
