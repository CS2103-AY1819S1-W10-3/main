//@@author theJrLinguist
package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.eventcommands.FindEventByTimeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses inputs arguments and creates a new FindEventByTimeCommand object.
 */
public class FindEventByTimeCommandParser implements Parser<FindEventByTimeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindEventByTimeCommand
     * and returns an FindEventByTimeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventByTimeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME_START, PREFIX_TIME_END);

        if (!argMultimap.getValue(PREFIX_DATE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindEventByTimeCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME_START).get());
        LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME_END).get());

        return new FindEventByTimeCommand(date, startTime, endTime);
    }
}
