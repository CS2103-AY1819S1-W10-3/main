package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the event organiser.
 */
public class ClearUserCommand extends Command {

    public static final String COMMAND_WORD = "clearUser";
    public static final String MESSAGE_SUCCESS = "Event Organiser has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.getClearIsEnabled()) {
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
        }

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setEvents(model.getFilteredEventList());
        model.resetData(newAddressBook);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
