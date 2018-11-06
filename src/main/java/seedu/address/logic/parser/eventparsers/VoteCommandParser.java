//@@author theJrLinguist
package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLL_OPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.VoteCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a vote command object.
 */
public class VoteCommandParser implements Parser<VoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * and returns an VoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_POLL_OPTION);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent() || !argMultimap.getValue(PREFIX_POLL_OPTION).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        String optionName = ParserUtil.parseGenericString(argMultimap.getValue(PREFIX_POLL_OPTION).get());
        return new VoteCommand(index, optionName);
    }
}
