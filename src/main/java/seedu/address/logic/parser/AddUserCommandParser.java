package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.util.TimeTableUtil;
import seedu.address.logic.commands.personcommands.AddUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TimeTable;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddUserCommand object
 */
public class AddUserCommandParser implements Parser<AddUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddUserCommand
     * and returns an AddUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_PASSWORD,
                        PREFIX_ADDRESS, PREFIX_INTEREST, PREFIX_TAG, PREFIX_TIMETABLE);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PASSWORD, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddUserCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Interest> interestList = ParserUtil.parseInterests(argMultimap.getAllValues(PREFIX_INTEREST));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Schedule schedule = null;
        Set<Friend> friendList = new HashSet<>();
        if (argMultimap.getValue(PREFIX_TIMETABLE).isPresent()) {
            String link = argMultimap.getValue(PREFIX_TIMETABLE).get();
            TimeTable tt = TimeTableUtil.parseUrl(link);
            schedule = ParserUtil.parseSchedule(tt.convertToSchedule().valueToString());
        } else {
            schedule = new Schedule();
        }
        Person person = new Person(name, phone, email, password, address, interestList, tagList, schedule, friendList);
        return new AddUserCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
