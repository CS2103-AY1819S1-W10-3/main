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
 * Adds a {@code Person} specified by the index as a friend in the current user's friend list
 *
 * @author agendazhang
 */
public class AddFriendCommand extends Command {

    public static final String COMMAND_WORD = "addFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": To add a person as friend identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer and different from the logged-in user)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_ADD_FRIEND_SUCCESS = "%1$s is added to %2$s friend list";

    private final Index index;

    public AddFriendCommand(Index index) {
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
        assert personToEdit != null;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person friendToAdd = lastShownList.get(index.getZeroBased());
        assert friendToAdd != null;
        if (personToEdit.equals(friendToAdd)) {
            throw new CommandException(Messages.MESSAGE_CANNOT_ADD_FRIEND_OWNSELF);
        }
        if (personToEdit.hasFriendInList(friendToAdd)) {
            throw new CommandException(String.format(Messages.MESSAGE_ALREADY_FRIENDS,
                    friendToAdd.getName(), personToEdit.getName()));
        }

        model.removeCurrentUser();

        Person personToEditCopy = new Person(personToEdit);
        personToEditCopy.addFriendInList(friendToAdd);

        model.updatePerson(personToEdit, personToEditCopy);
        model.setCurrentUser(personToEditCopy);
        model.authenticateUser(personToEditCopy);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_FRIEND_SUCCESS, friendToAdd.getName(),
                personToEdit.getName()));
    }

    @Override
    public String toString() {
        return COMMAND_WORD + " " + index.getZeroBased();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFriendCommand // instanceof handles nulls
                && index.equals(((AddFriendCommand) other).index)); // state check;
    }
}
