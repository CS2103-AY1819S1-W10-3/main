//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;

import java.time.LocalDate;

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
import seedu.address.model.event.polls.TimePoll;

/**
 * Command to add a time poll to the event.
 */
public class AddTimePollCommand extends Command {

    public static final String COMMAND_WORD = "addTimePoll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a time poll to the pre-selected event.\n"
            + "Parameters: "
            + PREFIX_DATE_START + "FIRST_DATE "
            + PREFIX_DATE_START + "LAST_DATE";
    public static final String MESSAGE_SUCCESS = "Time poll created for %1$s";

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public AddTimePollCommand(LocalDate startDate, LocalDate endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (DAYS.between(startDate, endDate) > 30 || endDate.isBefore(startDate)) {
            throw new CommandException(Messages.MESSAGE_INVALID_DATE_RANGE);
        }
        try {
            TimePoll poll = model.addTimePoll(startDate, endDate);
            model.commitAddressBook();
            String pollDisplayResult = poll.displayPoll();
            EventsCenter.getInstance().post(new DisplayPollEvent(pollDisplayResult));
            return new CommandResult(String.format(MESSAGE_SUCCESS, model.getSelectedEvent()));
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
                || (other instanceof AddPollCommand); // state check
    }
}
