package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Allow a person to login to EventOrganiser.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in a person to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PASSWORD + "password ";

    public static final String MESSAGE_SUCCESS = "Welcome back %1$s!";
    public static final String MESSAGE_INCORRECT_LOG_IN_DETAILS = "The login details are incorrect.";
    public static final String MESSAGE_USER_IS_CURRENTLY_LOGGED_IN = "A user is currently logged in."
            + " Please logout if you wish to login to another user.";

    private Person toLogin;

    /**
     * Creates an LoginCommand to log in the specified {@code CurrentUser}
     */
    public LoginCommand(Person person) {
        requireNonNull(person);
        toLogin = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasSetCurrentUser()) {
            throw new CommandException(MESSAGE_USER_IS_CURRENTLY_LOGGED_IN);
        }

        toLogin = model.authenticateUser(toLogin);

        if (toLogin.isUserWithOnlyNameAndPassword()) {
            throw new CommandException(MESSAGE_INCORRECT_LOG_IN_DETAILS);
        }

        model.setCurrentUser(toLogin);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin.getName()));
    }

    @Override
    public String toString() {
        return COMMAND_WORD + " " + toLogin.toString();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}
