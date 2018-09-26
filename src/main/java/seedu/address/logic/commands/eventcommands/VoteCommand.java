package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.Poll;
import seedu.address.model.person.Person;

/**
 * Command adds a vote to the specified poll and option.
 */
public class VoteCommand extends Command {

    public static final String COMMAND_WORD = "voteOption";
  
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": User adds vote to the option of the specified poll"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX"
            + PREFIX_NAME + "NAME ";

    public static final String MESSAGE_SUCCESS = "Voted for option '%1$s' of poll %2$s.";

    private final Index pollIndex;
    private final String optionName;

    /**
     * Creates an VoteCommand to add the specified {@code Event}
     */
    public VoteCommand(Index pollIndex, String optionName) {
        requireNonNull(pollIndex, optionName);
        this.pollIndex = pollIndex;
        this.optionName = optionName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Event event = history.getSelectedEvent();
        if (event == null) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        }

        try {
            Poll poll = event.getPoll(pollIndex);
            Person person = history.getSelectedPerson();
            poll.addVote(optionName, person);
            model.commitAddressBook();
            model.updateEvent(event, event);
            String result = String.format(MESSAGE_SUCCESS, optionName, pollIndex.getOneBased());
            result += '\n' + poll.displayPoll();
            return new CommandResult(result);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("No poll exists at this index.");
        } catch (IllegalArgumentException e) {
            throw new CommandException("No such option exists in this poll.");
        }
    }

}
