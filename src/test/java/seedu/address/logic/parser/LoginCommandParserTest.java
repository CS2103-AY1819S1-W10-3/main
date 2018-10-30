package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.StubUserBuilder.NAME_DESC_ALPHA;
import static seedu.address.testutil.StubUserBuilder.PASSWORD_DESC_ALPHA;
import static seedu.address.testutil.StubUserBuilder.VALID_NAME_ALPHA;
import static seedu.address.testutil.StubUserBuilder.VALID_PASSWORD_ALPHA;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.testutil.StubUserBuilder;

public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();
    private Person expectedPerson = new StubUserBuilder().build();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ALPHA + PASSWORD_DESC_ALPHA,
                new LoginCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_ALPHA + PASSWORD_DESC_ALPHA,
                new LoginCommand(expectedPerson));


        // multiple passwords - last password accepted
        assertParseSuccess(parser, NAME_DESC_ALPHA + PASSWORD_DESC_AMY + PASSWORD_DESC_ALPHA,
                new LoginCommand(expectedPerson));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        assertParseSuccess(parser, NAME_DESC_ALPHA + PASSWORD_DESC_ALPHA, new LoginCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ALPHA + PASSWORD_DESC_ALPHA, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_ALPHA + VALID_PASSWORD_ALPHA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PASSWORD_DESC_ALPHA, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, NAME_DESC_ALPHA + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_PASSWORD_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ALPHA + PASSWORD_DESC_ALPHA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }
}
