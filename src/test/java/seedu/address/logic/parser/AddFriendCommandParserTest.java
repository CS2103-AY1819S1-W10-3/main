package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.AddFriendCommand;

/**
 * Test cases for parsing input arguments and creating a new AddFriendCommand object
 */
public class AddFriendCommandParserTest {

    private AddFriendCommandParser parser = new AddFriendCommandParser();

    @Test
    public void parseValidArgsReturnsAddFriendCommand() {
        assertParseSuccess(parser, "2", new AddFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased())));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFriendCommand.MESSAGE_USAGE));
    }
}
