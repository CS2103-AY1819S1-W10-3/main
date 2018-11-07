//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventAttributesPredicate;

/**
 * Command to find event which have the specified attributes.
 */
public class FindEventCommand extends Command {
    public static final String COMMAND_WORD = "findEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds events which have the specified attributes.\n"
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_ADDRESS + "LOCATION "
            + PREFIX_DATE + "dd-MM-yyyy "
            + PREFIX_TIME_START + "HH:mm "
            + PREFIX_ORGANISER_NAME + "ORGANISER NAME "
            + PREFIX_PARTICIPANT_NAME + "PARTICIPANT NAME";
    public static final String MESSAGE_SUCCESS = "%1$d event(s) found.";

    private final EventAttributesPredicate predicate;

    /**
     * Creates an FindEventCommand to add a date to the specified {@code Event}
     */
    public FindEventCommand(EventAttributesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}
