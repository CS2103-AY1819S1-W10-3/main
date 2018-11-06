//@@author theJrLinguist
package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.eventcommands.AddPollCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses inputs arguments and creates a new AddPollCommand object.
 */
public class AddPollCommandParser implements Parser<AddPollCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPollCommand
     * and returns an AddPollCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getValue(PREFIX_NAME).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));
        }

        String pollName = ParserUtil.parseGenericString(argMultimap.getValue(PREFIX_NAME).get());

        return new AddPollCommand(pollName);
    }
}
