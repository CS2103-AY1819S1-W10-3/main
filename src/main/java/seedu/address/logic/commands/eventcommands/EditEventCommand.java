//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.NotEventOrganiserException;
import seedu.address.model.person.Address;
import seedu.address.model.tag.Tag;

/**
 * Command to edit event's name, location and tags.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the name, address and tags of the event.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "LOCATION "
            + "[" + PREFIX_TAG + "TAG]...\n";
    public static final String MESSAGE_SUCCESS = "Event %1$s edited";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event organiser.";

    private final Optional<EventName> name;
    private final Optional<Address> location;
    private final Optional<Set<Tag>> tags;

    /**
     * Creates an EditEventCommand to edit the specified {@code Event}.
     */
    public EditEventCommand(Optional<EventName> name, Optional<Address> location, Optional<Set<Tag>> tags) {
        requireAllNonNull(name, location, tags);
        this.name = name;
        this.location = location;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            if (!(name.isPresent() || location.isPresent() || tags.isPresent())) {
                throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
            }
            model.editEvent(name, location, tags);
            Event event = model.getSelectedEvent();
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, event));
        } catch (NoUserLoggedInException e) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        } catch (NoEventSelectedException e) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        } catch (NotEventOrganiserException e) {
            throw new CommandException(Messages.MESSAGE_NOT_EVENT_ORGANISER);
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditEventCommand // instanceof handles nulls
                && name.equals(((EditEventCommand) other).name)
                && location.equals(((EditEventCommand) other).location)
                && tags.equals(((EditEventCommand) other).tags)); // state check
    }
}
