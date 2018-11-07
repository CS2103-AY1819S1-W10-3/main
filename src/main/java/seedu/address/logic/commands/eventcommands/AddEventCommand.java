package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event to the event organiser.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the event organiser.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_TAG + "TAG ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event organiser";

    private Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            if (model.hasEvent(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_EVENT);
            }
            model.addEvent(toAdd);
            model.commitAddressBook();
            model.setSelectedEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (NoUserLoggedInException e) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
