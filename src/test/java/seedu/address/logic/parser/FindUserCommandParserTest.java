package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_STUDY_AND_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND_AND_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_FIRST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_LAST_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.personcommands.FindUserCommand;
import seedu.address.model.person.UserContainsKeywordsPredicate;

public class FindUserCommandParserTest {

    private FindUserCommandParser parser = new FindUserCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindUserCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs() {
        List<String> nameKeywordList = Arrays.asList(VALID_NAME_BOB_FIRST_NAME, VALID_NAME_BOB_LAST_NAME);
        List<String> phoneKeywordList = Collections.singletonList(VALID_PHONE_BOB);
        List<String> addressKeywordList = Arrays.asList(VALID_ADDRESS_BOB.split(" "));
        List<String> emailKeywordList = Collections.singletonList(VALID_EMAIL_BOB);
        List<String> interestsKeywordList = Collections.singletonList(VALID_INTEREST_STUDY);
        List<String> tagsKeywordList = Collections.singletonList(VALID_TAG_FRIEND);
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, addressKeywordList,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);

        // whitespace only preamble
        FindUserCommand expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, expectedFindUserCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, expectedFindUserCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PREAMBLE_WHITESPACE + PHONE_DESC_BOB
                        + PREAMBLE_WHITESPACE + EMAIL_DESC_BOB + PREAMBLE_WHITESPACE + ADDRESS_DESC_BOB
                        + PREAMBLE_WHITESPACE + INTEREST_DESC_STUDY + PREAMBLE_WHITESPACE + TAG_DESC_FRIEND,
                expectedFindUserCommand);

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, new FindUserCommand(predicate));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, new FindUserCommand(predicate));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, new FindUserCommand(predicate));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, new FindUserCommand(predicate));

        // multiple interests - last interest accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INTEREST_DESC_PLAY + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, new FindUserCommand(predicate));

        // multiple tags - last tag accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INTEREST_DESC_STUDY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new FindUserCommand(predicate));

        interestsKeywordList = Arrays.asList(VALID_INTEREST_STUDY, VALID_INTEREST_PLAY);
        predicate = new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, addressKeywordList,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INTEREST_DESC_STUDY_AND_PLAY + TAG_DESC_FRIEND, new FindUserCommand(predicate));
        tagsKeywordList = Arrays.asList(VALID_TAG_HUSBAND, VALID_TAG_FRIEND);
        predicate = new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, addressKeywordList,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INTEREST_DESC_STUDY_AND_PLAY + TAG_DESC_HUSBAND_AND_FRIEND, new FindUserCommand(predicate));
    }

    @Test
    public void parseFieldMissing_returnsFindUserCommand() {
        List<String> nameKeywordList = Arrays.asList(VALID_NAME_BOB_FIRST_NAME, VALID_NAME_BOB_LAST_NAME);
        List<String> phoneKeywordList = Collections.singletonList(VALID_PHONE_BOB);
        List<String> addressKeywordList = Arrays.asList(VALID_ADDRESS_BOB.split(" "));
        List<String> emailKeywordList = Collections.singletonList(VALID_EMAIL_BOB);
        List<String> interestsKeywordList = Collections.singletonList(VALID_INTEREST_STUDY);
        List<String> tagsKeywordList = Collections.singletonList(VALID_TAG_FRIEND);

        // missing name
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(null, phoneKeywordList, addressKeywordList,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);
        FindUserCommand expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND, expectedFindUserCommand);

        // missing phone
        predicate = new UserContainsKeywordsPredicate(nameKeywordList, null, addressKeywordList,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);
        expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY
                + TAG_DESC_FRIEND, expectedFindUserCommand);

        // missing address
        predicate = new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, null,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);
        expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INTEREST_DESC_STUDY
                + TAG_DESC_FRIEND, expectedFindUserCommand);

        // missing email
        predicate = new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, addressKeywordList,
                null, interestsKeywordList, tagsKeywordList);
        expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY
                + TAG_DESC_FRIEND, expectedFindUserCommand);

        // missing interest
        predicate = new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, addressKeywordList,
                emailKeywordList, null, tagsKeywordList);
        expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, expectedFindUserCommand);

        // missing tags
        predicate = new UserContainsKeywordsPredicate(null, phoneKeywordList, addressKeywordList,
                emailKeywordList, interestsKeywordList, null);
        expectedFindUserCommand = new FindUserCommand(predicate);
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY, expectedFindUserCommand);
    }
}
