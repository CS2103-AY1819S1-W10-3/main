package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.ListFriendsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListFriendsCommand object
 */
public class ListFriendsCommandParser implements Parser<ListFriendsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListFriendsCommand
     * and returns an ListFriendsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListFriendsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListFriendsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListFriendsCommand.MESSAGE_USAGE));
        }
    }
}
