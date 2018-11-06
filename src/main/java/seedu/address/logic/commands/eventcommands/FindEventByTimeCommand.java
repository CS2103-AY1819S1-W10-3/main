//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventInTimeFramePredicate;

/**
 * Command to find events occurring on a specific date and between a given time range.
 */
public class FindEventByTimeCommand extends Command {
    public static final String COMMAND_WORD = "findEventByTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds events which occur on the given date "
            + " and between the specified times"
            + "Parameters: "
            + PREFIX_DATE + "dd-MM-yyyy "
            + PREFIX_TIME_START + "HH:mm "
            + PREFIX_TIME_END + "HH:mm";
    public static final String MESSAGE_SUCCESS = "%1$d events on %2$s between %3$s and %4$s found.";

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Creates an FindEventByTimeCommand to add a date to the specified {@code Event}
     */
    public FindEventByTimeCommand(LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireNonNull(date);
        requireNonNull(startTime);
        requireNonNull(endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            EventInTimeFramePredicate predicate = new EventInTimeFramePredicate(startTime, endTime, date);
            model.updateFilteredEventList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredEventList().size(),
                    date.toString(), startTime.toString(), endTime.toString()));
        } catch (IllegalArgumentException e) {
            throw new CommandException(Messages.MESSAGE_END_BEFORE_START_TIME);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventByTimeCommand // instanceof handles nulls
                && date.equals(((FindEventByTimeCommand) other).date))
                && startTime.equals(((FindEventByTimeCommand) other).startTime)
                && endTime.equals(((FindEventByTimeCommand) other).endTime); // state check
    }
}
