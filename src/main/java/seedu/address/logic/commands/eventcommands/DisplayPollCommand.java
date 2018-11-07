//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayPollEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.polls.AbstractPoll;

/**
 * Command to display a poll of a pre-selected event given an index.
 */
public class DisplayPollCommand extends Command {

    public static final String COMMAND_WORD = "displayPoll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the poll with the provided index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Poll %1$s of %2$s displayed.";

    private final Index targetIndex;

    /**
     * Creates an DisplayPollCommand to add the specified {@code Event}
     */
    public DisplayPollCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            Event event = model.getSelectedEvent();
            AbstractPoll poll = event.getPoll(targetIndex);
            String result = String.format(MESSAGE_SUCCESS, targetIndex.getOneBased(), event);
            String pollDisplayResult = poll.displayPoll();
            EventsCenter.getInstance().post(new DisplayPollEvent(pollDisplayResult));
            return new CommandResult(result);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_NO_POLL_AT_INDEX);
        } catch (NoEventSelectedException e) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPollCommand // instanceof handles nulls
                && targetIndex.equals(((DisplayPollCommand) other).targetIndex)); // state check
    }
}
