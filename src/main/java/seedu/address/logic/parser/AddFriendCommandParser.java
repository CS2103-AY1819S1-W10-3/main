package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.AddFriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddFriendCommand object
 *
 * @author agendazhang
 */
public class AddFriendCommandParser implements Parser<AddFriendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFriendCommand
     * and returns an AddFriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFriendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AddFriendCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFriendCommand.MESSAGE_USAGE), pe);
        }
    }

}
