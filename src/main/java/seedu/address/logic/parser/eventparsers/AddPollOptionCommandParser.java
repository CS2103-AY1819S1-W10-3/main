//@@author theJrLinguist
package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLL_OPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.AddPollOptionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses inputs arguments and creates a new AddPollOptionCommand object.
 */
public class AddPollOptionCommandParser implements Parser<AddPollOptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPollOptionCommand
     * and returns an AddPollOptionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPollOptionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_POLL_OPTION);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent() || !argMultimap.getValue(PREFIX_POLL_OPTION).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollOptionCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        String name = ParserUtil.parseGenericString(argMultimap.getValue(PREFIX_POLL_OPTION).get());
        return new AddPollOptionCommand(index, name);
    }
}
