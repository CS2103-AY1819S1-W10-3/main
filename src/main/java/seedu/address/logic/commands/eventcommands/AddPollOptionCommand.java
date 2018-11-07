//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLL_OPTION;

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

/**
 * Command to adds an option to the specified poll
 */
public class AddPollOptionCommand extends Command {

    public static final String COMMAND_WORD = "addOption";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Event organiser adds option to the specified poll.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX OF POLL "
            + PREFIX_POLL_OPTION + "OPTION_NAME";
    public static final String MESSAGE_SUCCESS = "Poll option %1$s added to poll %2$d";

    private final String pollOption;
    private final Index targetIndex;

    /**
     * Creates an AddPollOptionCommand to add the specified {@code Event}
     */
    public AddPollOptionCommand(Index index, String pollOption) {
        requireNonNull(pollOption);
        this.targetIndex = index;
        this.pollOption = pollOption;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            String pollDisplayResult = model.addPollOption(targetIndex, pollOption);
            model.commitAddressBook();
            String result = String.format(MESSAGE_SUCCESS, pollOption, targetIndex.getOneBased());
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
                || (other instanceof AddPollOptionCommand // instanceof handles nulls
                && pollOption.equals(((AddPollOptionCommand) other).pollOption)
                && targetIndex.equals(((AddPollOptionCommand) other).targetIndex));
    }
}
