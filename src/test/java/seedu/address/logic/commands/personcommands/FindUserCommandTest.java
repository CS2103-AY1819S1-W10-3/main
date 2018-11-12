package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.UserContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindUserCommand}.
 */
public class FindUserCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Collections.singletonList("first_name");
        List<String> secondPredicateNameKeywordList = Arrays.asList("first_name", "second_name");

        List<String> firstPredicatePhoneKeywordList = Collections.singletonList("first_phone");
        List<String> secondPredicatePhoneKeywordList = Arrays.asList("first_phone", "second_phone");

        List<String> firstPredicateAddressKeywordList = Collections.singletonList("first_address");
        List<String> secondPredicateAddressKeywordList = Arrays.asList("first_address", "second_address");

        List<String> firstPredicateEmailKeywordList = Collections.singletonList("first_address");
        List<String> secondPredicateEmailKeywordList = Arrays.asList("first_email", "second_email");

        List<String> firstPredicateInterestsKeywordList = Collections.singletonList("first_interest");
        List<String> secondPredicateInterestsKeywordList = Arrays.asList("first_interest", "second_interest");

        List<String> firstPredicateTagsKeywordList = Collections.singletonList("first_tag");
        List<String> secondPredicateTagsKeywordList = Arrays.asList("first_tag", "second_tag");

        UserContainsKeywordsPredicate firstPredicate =
                new UserContainsKeywordsPredicate(firstPredicateNameKeywordList,
                        firstPredicatePhoneKeywordList,
                        firstPredicateAddressKeywordList,
                        firstPredicateEmailKeywordList,
                        firstPredicateInterestsKeywordList,
                        firstPredicateTagsKeywordList);
        UserContainsKeywordsPredicate secondPredicate =
                new UserContainsKeywordsPredicate(secondPredicateNameKeywordList,
                        secondPredicatePhoneKeywordList,
                        secondPredicateAddressKeywordList,
                        secondPredicateEmailKeywordList,
                        secondPredicateInterestsKeywordList,
                        secondPredicateTagsKeywordList);


        FindUserCommand findFirstCommand = new FindUserCommand(firstPredicate);
        FindUserCommand findSecondCommand = new FindUserCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindUserCommand findFirstCommandCopy = new FindUserCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand == null);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_noPersonFound() {
        List<String> nameKeywordList = Arrays.asList("x");
        List<String> phoneKeywordList = Collections.singletonList("x");
        List<String> addressKeywordList = Arrays.asList("x");
        List<String> emailKeywordList = Collections.singletonList("x");
        List<String> interestsKeywordList = Collections.singletonList("x");
        List<String> tagsKeywordList = Collections.singletonList("x");
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(nameKeywordList,
                        phoneKeywordList,
                        addressKeywordList,
                        emailKeywordList,
                        interestsKeywordList,
                        tagsKeywordList);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindUserCommand command = new FindUserCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_onePersonFound() {
        List<String> nameKeywordList = Arrays.asList(VALID_NAME_ALICE.split(" "));
        List<String> phoneKeywordList = Collections.singletonList(VALID_PHONE_ALICE);
        List<String> addressKeywordList = Arrays.asList(VALID_ADDRESS_ALICE.split(" "));
        List<String> emailKeywordList = Collections.singletonList(VALID_EMAIL_ALICE);
        UserContainsKeywordsPredicate predicate = new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList,
                addressKeywordList, emailKeywordList, null, null);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindUserCommand command = new FindUserCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }
}
