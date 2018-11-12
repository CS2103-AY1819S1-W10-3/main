package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.personcommands.SuggestFriendsByInterestsCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SuggestFriendsByInterestsCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the SuggestFriendsByInterestsCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SuggestFriendsByInterestsCommandParserTest {

    private SuggestFriendsByInterestsCommandParser parser = new SuggestFriendsByInterestsCommandParser();

    @Test
    public void parseValidArgsReturnsSuggestFriendsByInterestsCommand() {
        assertParseSuccess(parser, "1", new SuggestFriendsByInterestsCommand(INDEX_FIRST));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SuggestFriendsByInterestsCommand.MESSAGE_USAGE));
    }
}
