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
import seedu.address.model.person.InterestSimilarPredicate;
import seedu.address.model.person.Person;

/**
 * Shows a list of {@code Person} with at least one similar {@code Interest} with the target user,
 * The list includes the target user so that he/she can activate the {@code AddFriendCommand}.
 * Persons who are already friends with the target user will not be shown.
 */
public class SuggestFriendsByInterestsCommand extends Command {

    public static final String COMMAND_WORD = "suggestFriendsByInterests";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of persons (including target user) with at least one similar interest"
            + " with respect to the target user"
            + " identified by the index number used in the displayed person list"
            + " and are not yet friends.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LIST_SUGGESTED_FRIENDS_SUCCESS = "Listed all suggested friends for user: %1$s";

    private final Index targetIndex;

    public SuggestFriendsByInterestsCommand(Index targetIndex) {
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

        InterestSimilarPredicate predicate = new InterestSimilarPredicate(targetPerson);

        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_LIST_SUGGESTED_FRIENDS_SUCCESS, targetPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestFriendsByInterestsCommand // instanceof handles nulls
                && targetIndex.equals(((SuggestFriendsByInterestsCommand) other).targetIndex)); // state check
    }
}
