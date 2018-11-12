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
import seedu.address.model.person.IsFriendPredicate;
import seedu.address.model.person.Person;

/**
 * Shows a list of {@code Person} who are friends with the target user,
 */
public class ListFriendsCommand extends Command {

    public static final String COMMAND_WORD = "listFriends";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of persons who are friends with"
            + " the target user identified by the index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LIST_FRIENDS_SUCCESS = "Listed all friends of %1$s";

    private final Index targetIndex;

    public ListFriendsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());

        IsFriendPredicate predicate = new IsFriendPredicate(targetPerson);

        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_LIST_FRIENDS_SUCCESS,
                targetPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListFriendsCommand // instanceof handles nulls
                && targetIndex.equals(((ListFriendsCommand) other).targetIndex)); // state check
    }
}
