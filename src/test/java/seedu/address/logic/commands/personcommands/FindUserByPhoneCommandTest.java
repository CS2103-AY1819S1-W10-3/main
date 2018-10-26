package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindUserByPhoneCommand}.
 */
public class FindUserByPhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noPhoneGiven_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindUserByPhoneCommand command = new FindUserByPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PhoneContainsKeywordsPredicate predicate = preparePredicate("94351253");
        FindUserByPhoneCommand command = new FindUserByPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98765432"));
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98761111"));

        FindUserByPhoneCommand findFirstCommand = new FindUserByPhoneCommand(firstPredicate);
        FindUserByPhoneCommand findSecondCommand = new FindUserByPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindUserByPhoneCommand findFirstCommandCopy = new FindUserByPhoneCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand == null);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
