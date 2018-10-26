package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.personcommands.FindUserByPhoneCommand;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindUserByPhoneCommand object
 */
public class FindUserByPhoneCommandParserTest {

    private FindUserByPhoneCommandParser parser = new FindUserByPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindUserByPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByPhoneCommand() {
        // no leading and trailing whitespaces
        FindUserByPhoneCommand expectedFindUserByPhoneCommand =
                new FindUserByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "98761111")));
        assertParseSuccess(parser, "98765432 98761111", expectedFindUserByPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 98765432  \t 98761111 \n \t", expectedFindUserByPhoneCommand);
    }
}
