package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.personcommands.DeleteUserCommand;

/**
 * Test cases for parsing input arguments and creating a new DeleteUserCommand object
 */
public class DeleteUserCommandParserTest {

    private DeleteUserCommandParser parser = new DeleteUserCommandParser();

    @Test
    public void parseValidArgsReturnsDeleteUserCommand() {
        assertParseSuccess(parser, "", new DeleteUserCommand());
    }

    @Test
    public void parseValidArgsWithEmptySpaceReturnsDeleteUserCommand() {
        assertParseSuccess(parser, " ", new DeleteUserCommand());
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteUserCommand.MESSAGE_USAGE));
    }
}
