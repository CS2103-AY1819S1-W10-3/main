package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Allow a person to login to the address book.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_ALIAS = "l";
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com";

    public static final String MESSAGE_SUCCESS = "Welcome back %1$s!";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "This person does not exist in the address book";

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

        if (!model.hasPerson(toLogin)) {
            throw new CommandException(MESSAGE_PERSON_DOES_NOT_EXIST);
        }

        model.setCurrentUser(toLogin);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}
