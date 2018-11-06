//@@author theJrLinguist
package seedu.address.logic.parser.eventparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;

import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAttributesPredicate;

/**
 * Parses inputs arguments and creates a new FindEventCommand object.
 */
public class FindEventCommandParser implements Parser<FindEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditUserCommand
     * and returns an EditUserCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_DATE, PREFIX_TIME_START, PREFIX_ORGANISER_NAME, PREFIX_PARTICIPANT_NAME);

        EventAttributesPredicate predicate = new EventAttributesPredicate();
        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            predicate.setName(ParserUtil.parseGenericString(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicate.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME_START).isPresent()) {
            predicate.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME_START).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            predicate.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ORGANISER_NAME).isPresent()) {
            predicate.setParticipant(ParserUtil.parseName(argMultimap.getValue(PREFIX_ORGANISER_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PARTICIPANT_NAME).isPresent()) {
            predicate.setParticipant(ParserUtil.parseName(argMultimap.getValue(PREFIX_PARTICIPANT_NAME).get()));
        }

        return new FindEventCommand(predicate);
    }
}
