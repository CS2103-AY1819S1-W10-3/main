package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.DeleteFriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFriendCommand object
 *
 * @author agendazhang
 */
public class DeleteFriendCommandParser implements Parser<DeleteFriendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFriendCommand
     * and returns an DeleteFriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFriendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFriendCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFriendCommand.MESSAGE_USAGE), pe);
        }
    }

}
