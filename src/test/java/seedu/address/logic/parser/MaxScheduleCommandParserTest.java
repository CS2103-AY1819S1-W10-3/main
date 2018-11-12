package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MaxScheduleCommand;


public class MaxScheduleCommandParserTest {

    private MaxScheduleCommandParser parser = new MaxScheduleCommandParser();

    @Test
    public void parseEmptyArgthrowsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValid() {
        Index[] index = {INDEX_FIRST, INDEX_SECOND};
        MaxScheduleCommand expectedMaxScheduleCommand =
            new MaxScheduleCommand(index, "null");

        assertParseSuccess(parser, "1 2",
            expectedMaxScheduleCommand);
    }

    @Test
    public void parseInvalid() {

        // does not have -
        assertParseFailure(parser, "1 2 sl/00  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));

        // too many -
        assertParseFailure(parser, "1 2 sl/0000-0000-0000  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));

        // wrong number of digits
        assertParseFailure(parser, "1 2 sl/00000-0100",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 sl/0000-10000  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 sl/00xx-00xx",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 sl/1300-1000",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
    }
}
