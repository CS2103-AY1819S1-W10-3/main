//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DisplayPollEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.exceptions.NotEventOrganiserException;

/**
 * Command to add a new poll to the pre-selected event.
 */
public class AddPollCommand extends Command {

    public static final String COMMAND_WORD = "addPoll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a poll to the pre-selected event.\n"
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
        try {
            String pollDisplayResult = model.addPoll(pollName);
            model.commitAddressBook();
            EventsCenter.getInstance().post(new DisplayPollEvent(pollDisplayResult));
            return new CommandResult(String.format(MESSAGE_SUCCESS, pollName, model.getSelectedEvent()));
        } catch (NoUserLoggedInException e) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        } catch (NoEventSelectedException e) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        } catch (NotEventOrganiserException e) {
            throw new CommandException(Messages.MESSAGE_NOT_EVENT_ORGANISER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPollCommand // instanceof handles nulls
                && pollName.equals(((AddPollCommand) other).pollName)); // state check
    }
}
