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
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.Poll;

/**
 * Command to display a poll of a pre-selected event given an index.
 */
public class DisplayPollCommand extends Command {

    public static final String COMMAND_WORD = "displayPoll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the poll with the provided index";
    public static final String MESSAGE_SUCCESS = "Poll %1$s displayed.";

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
        Event event = history.getSelectedEvent();
        if (event == null) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        }
        try {
            Poll poll = event.getPoll(targetIndex);
            String result = String.format(MESSAGE_SUCCESS, targetIndex.getOneBased());
            result += '\n' + poll.displayPoll();
            EventsCenter.getInstance().post(new DisplayPollEvent(result));
            return new CommandResult(result);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("No poll exists at this index.");
        }
    }
}
