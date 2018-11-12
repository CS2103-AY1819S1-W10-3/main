package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.personcommands.DeleteUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteUserCommand object
 */
public class DeleteUserCommandParser implements Parser<DeleteUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteUserCommand
     * and returns an DeleteUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteUserCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteUserCommand.MESSAGE_USAGE));
        }

        return new DeleteUserCommand();
    }
}
