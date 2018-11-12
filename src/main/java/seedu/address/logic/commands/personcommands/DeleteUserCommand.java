package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_USER_LOGGED_IN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes the current user.
 */
public class DeleteUserCommand extends Command {

    public static final String COMMAND_WORD = "deleteUser";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the current logged-in person from the displayed person list.\n"
            + "No other parameter should be specified.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (!model.hasSetCurrentUser()) {
            throw new CommandException(MESSAGE_NO_USER_LOGGED_IN);
        }
        Person personToDelete = model.getCurrentUser();
        updateFriendListsDueToDeletedPerson(model, lastShownList, personToDelete);
        model.removeCurrentUser();
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    /**
     * After the current user is deleted, this function will search through
     * the user list, and updates the {@code Friend} attribute of the persons with this
     * current user in their friend list
     */
    private void updateFriendListsDueToDeletedPerson(Model model, List<Person> personList,
                                                     Person personToDelete) throws CommandException {
        for (Person currentPerson : personList) {
            if (currentPerson.hasFriendInList(personToDelete)) {
                Person currentPersonCopy = new Person(currentPerson);
                currentPersonCopy.deleteFriendInList(personToDelete);

                model.updatePerson(currentPerson, currentPersonCopy);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                model.commitAddressBook();
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteUserCommand); // instanceof handles nulls
    }
}
