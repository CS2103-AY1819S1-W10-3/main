package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.DeleteFriendCommand;

/**
 * Test cases for parsing input arguments and creating a new DeleteFriendCommand object
 */
public class DeleteFriendCommandParserTest {

    private DeleteFriendCommandParser parser = new DeleteFriendCommandParser();

    @Test
    public void parseValidArgsReturnsDeleteFriendCommand() {
        assertParseSuccess(parser, "2",
                new DeleteFriendCommand(Index.fromZeroBased(INDEX_SECOND.getZeroBased())));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFriendCommand.MESSAGE_USAGE));
    }
}
