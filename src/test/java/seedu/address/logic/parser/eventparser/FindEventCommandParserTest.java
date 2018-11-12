//@@theJrLinguist
package seedu.address.logic.parser.eventparser;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.ORGANISER_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommandTest;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.eventparsers.FindEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAttributesPredicate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;

public class FindEventCommandParserTest {
    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + INVALID_ADDRESS_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        String userInput = NAME_DESC_MEETING + ADDRESS_DESC_AMY;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT_NAME, PREFIX_ADDRESS);

        EventAttributesPredicate predicate = new EventAttributesPredicate();
        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            predicate.setName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicate.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        FindEventCommand expectedCommand = new FindEventCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        String userInput = NAME_DESC_MEETING + ADDRESS_DESC_AMY + START_TIME_DESC + DATE_DESC + TAG_DESC_FRIEND
                + ORGANISER_NAME_DESC + PARTICIPANT_NAME_DESC;
        EventAttributesPredicate predicate = FindEventCommandTest.makeEventsAttributesPredicate(userInput);
        FindEventCommand expectedCommand = new FindEventCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        // name
        String userInput = NAME_DESC_MEETING;
        EventAttributesPredicate predicate = new EventAttributesPredicate();
        predicate.setName(ParserUtil.parseEventName(VALID_NAME_MEETING));
        FindEventCommand expectedCommand = new FindEventCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = ADDRESS_DESC_AMY;
        predicate = new EventAttributesPredicate();
        predicate.setAddress(ParserUtil.parseAddress(VALID_ADDRESS_AMY));
        expectedCommand = new FindEventCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws ParseException {
        // name
        String userInput = INVALID_EVENT_NAME_DESC + NAME_DESC_MEETING;
        EventAttributesPredicate predicate = new EventAttributesPredicate();
        predicate.setName(ParserUtil.parseEventName(VALID_NAME_MEETING));
        FindEventCommand expectedCommand = new FindEventCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
