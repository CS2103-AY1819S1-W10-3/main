package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.personcommands.ListFriendsCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ListFriendsCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ListFriendsCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ListFriendsCommandParserTest {

    private ListFriendsCommandParser parser = new ListFriendsCommandParser();

    @Test
    public void parseValidArgsReturnsListFriendsCommand() {
        assertParseSuccess(parser, "1", new ListFriendsCommand(INDEX_FIRST));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListFriendsCommand.MESSAGE_USAGE));
    }
}
