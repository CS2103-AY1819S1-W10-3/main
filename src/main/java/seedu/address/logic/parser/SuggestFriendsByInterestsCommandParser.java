package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.SuggestFriendsByInterestsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SuggestFriendsByInterestsCommand object
 */
public class SuggestFriendsByInterestsCommandParser implements Parser<SuggestFriendsByInterestsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestFriendsByInterestsCommand
     * and returns an SuggestFriendsByInterestsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SuggestFriendsByInterestsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SuggestFriendsByInterestsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestFriendsByInterestsCommand.MESSAGE_USAGE));
        }
    }

}
