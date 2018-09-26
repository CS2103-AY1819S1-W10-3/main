package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Sets the time of an event.
 */
public class SetTimeCommand extends Command {

    public static final String COMMAND_WORD = "setTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the time for the pre-selected event.";
    public static final String MESSAGE_SUCCESS = "Time %1$s set for %2$s";

    private final LocalTime time;
    private Event event;

    /**
     * Creates an AddCommand to add the specified {@code Event}
     */
    public SetTimeCommand(LocalTime time) {
        requireNonNull(time);
        this.time = time;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        event = history.getSelectedEvent();
        if (event == null) {
            throw new CommandException(Messages.MESSAGE_NO_EVENT_SELECTED);
        }
        event.setTime(time);
        model.commitAddressBook();
        model.updateEvent(event, event);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        return new CommandResult(String.format(MESSAGE_SUCCESS, time.format(timeFormat), event));
    }
}
