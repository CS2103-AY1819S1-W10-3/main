package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Command to add a new poll to the pre-selected event.
 */
public class AddPollCommand extends Command {

    public static final String COMMAND_WORD = "addPoll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a poll to the pre-selected event. "
            + "Parameters: "
            + PREFIX_NAME + "NAME ";
    public static final String MESSAGE_SUCCESS = "Poll %1$s created for %2$s";

    private final String pollName;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public AddPollCommand(String pollName) {
        requireNonNull(pollName);
        this.pollName = pollName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Event event = history.getSelectedEvent();
        if (event == null) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        }
        event.addPoll(pollName);
        model.commitAddressBook();
        model.updateEvent(event, event);
        return new CommandResult(String.format(MESSAGE_SUCCESS, pollName, event));
    }
}
