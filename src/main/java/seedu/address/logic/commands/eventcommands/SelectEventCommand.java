//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayPollEvent;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Selects a event identified using its displayed index from the address book.
 */
public class SelectEventCommand extends Command {

    public static final String COMMAND_WORD = "selectEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_EVENT_SUCCESS = "Selected Event: %1$s";

    private final Index targetIndex;

    public SelectEventCommand(Index targetIndex) {
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
        model.setSelectedEvent(event);

        EventsCenter.getInstance().post(new DisplayPollEvent(event.getInfo()));
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_EVENT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectEventCommand // instanceof handles nulls
                && targetIndex.equals(((SelectEventCommand) other).targetIndex)); // state check
    }
}
