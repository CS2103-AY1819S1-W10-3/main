package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Allow a person to log out from EventOrganiser.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs out a person to the address book. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Goodbye.";
    public static final String MESSAGE_NO_LOGGED_IN_USER = "No user is logged in";

    /**
     * Creates an LoginCommand to log in the specified {@code CurrentUser}
     */
    public LogoutCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasSetCurrentUser()) {
            throw new CommandException(MESSAGE_NO_LOGGED_IN_USER);
        }

        model.removeCurrentUser();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogoutCommand); // instanceof handles nulls
    }
}
