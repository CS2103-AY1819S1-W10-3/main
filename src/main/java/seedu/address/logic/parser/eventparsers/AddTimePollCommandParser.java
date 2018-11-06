//@@author theJrLinguist
package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;

import java.time.LocalDate;

import seedu.address.logic.commands.eventcommands.AddTimePollCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses and returns an AddTimePollCommand.
 */
public class AddTimePollCommandParser implements Parser<AddTimePollCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTimePollCommand
     * and returns an AddTimePollCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTimePollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE_START, PREFIX_DATE_END);

        if (!argMultimap.getValue(PREFIX_DATE_START).isPresent()
                || !argMultimap.getValue(PREFIX_DATE_END).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTimePollCommand.MESSAGE_USAGE));
        }

        LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_START).get());
        LocalDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_END).get());

        return new AddTimePollCommand(startDate, endDate);
    }
}
