//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Current user joins an event identified using its displayed index from the address book.
 */
public class JoinEventCommand extends Command {

    public static final String COMMAND_WORD = "joinEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Current user joins the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Joined event: %1$s";

    private final Index targetIndex;

    public JoinEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Event> filteredEventList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event event = model.getEvent(targetIndex);
        history.setSelectedEvent(event);

        try {
            //Person person = history.getSelectedPerson();
            event.addPerson(model.getCurrentUser());
        } catch (DuplicatePersonException e) {
            throw new CommandException(Messages.MESSAGE_ALREADY_JOINED);
        } catch (NoUserLoggedInException e) {
            throw new CommandException(Messages.MESSAGE_NO_USER_LOGGED_IN);
        }

        model.updateEvent(event, event);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex));
        String result = String.format(MESSAGE_SUCCESS, event);
        result += "\n" + "People attending: " + event.getNameList();
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JoinEventCommand // instanceof handles nulls
                && targetIndex.equals(((JoinEventCommand) other).targetIndex)); // state check
    }
}
