package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.DESC_ALICE_AND_BENSON_ADDRESS;
import static seedu.address.testutil.TypicalPersons.DESC_ALICE_AND_BENSON_AND_KENSON_INTEREST;
import static seedu.address.testutil.TypicalPersons.DESC_ALICE_NAME;
import static seedu.address.testutil.TypicalPersons.DESC_BENSON_NAME;
import static seedu.address.testutil.TypicalPersons.DESC_BENSON_PHONE;
import static seedu.address.testutil.TypicalPersons.DESC_BENSON_TAG;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL_DIFFERENT_CASES;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL_NONMATCHING;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL_REPEATED;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL_REVERSED;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL_SUBSTRING;
import static seedu.address.testutil.TypicalPersons.DESC_GEORGE_AND_FIONA_EMAIL_SUPERSTRING;
import static seedu.address.testutil.TypicalPersons.DESC_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.commands.personcommands.FindUserCommand;
import seedu.address.model.Model;

public class FindUserCommandSystemTest extends AddressBookSystemTest {
    private Model expectedModel;

    @Before
    public void setup() {
        expectedModel = getModel();
    }

    @Test
    public void findMultiplePersons() {

        /* Case: find Benson and Danial using only nameKeyword, command with leading spaces and trailing spaces
         */
        String command = "   " + FindUserCommand.COMMAND_WORD + DESC_MATCHING_MEIER + "   ";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find Benson using only phoneKeyword, command with leading spaces and trailing spaces
         */
        command = "   " + FindUserCommand.COMMAND_WORD + DESC_BENSON_PHONE + "   ";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find Benson and Alice using only addressKeyword, command with leading spaces and trailing spaces
         */
        command = "   " + FindUserCommand.COMMAND_WORD + DESC_ALICE_AND_BENSON_ADDRESS + "   ";
        ModelHelper.setFilteredList(expectedModel, BENSON, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find Benson using only tagKeyword, command with leading spaces and trailing spaces
         */
        command = "   " + FindUserCommand.COMMAND_WORD + DESC_BENSON_TAG + "   ";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find Alice, Benson, Carl, Danial, Elle, Fiona, George using only interestKeyword,
         * command with leading spaces and trailing spaces
         */
        command = "   " + FindUserCommand.COMMAND_WORD + DESC_ALICE_AND_BENSON_AND_KENSON_INTEREST + "   ";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find George and Fiona using only emailKeyword, command with leading spaces and trailing spaces
         */
        command = "   " + FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL + "   ";
        ModelHelper.setFilteredList(expectedModel, GEORGE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    @Test
    public void findPersonNotDisplayed() {

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        String command = FindUserCommand.COMMAND_WORD + DESC_ALICE_AND_BENSON_AND_KENSON_INTEREST + "   ";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindUserCommand.COMMAND_WORD + DESC_BENSON_TAG + "   ";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    @Test
    public void findPersonsUsingReversedKeywords() {
        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        String command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL + "   ";
        ModelHelper.setFilteredList(expectedModel, GEORGE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL_REVERSED + "   ";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);
    }


    @Test
    public void find() {
        /* Case: find multiple persons in address book, 2 keyword and 1 repeated keyword -> 2 persons found */
        String command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL_REPEATED + "   ";
        ModelHelper.setFilteredList(expectedModel, GEORGE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords and 1 non-matching keyword -> 2 persons found */
        command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL_NONMATCHING;
        ModelHelper.setFilteredList(expectedModel, GEORGE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 2 person found */
        command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL_DIFFERENT_CASES;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of email -> 0 persons found */
        command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL_SUBSTRING;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindUserCommand.COMMAND_WORD + DESC_GEORGE_AND_FIONA_EMAIL_SUPERSTRING;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindUserCommand.COMMAND_WORD + " n/Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(BENSON.getName().fullName));
        command = FindUserCommand.COMMAND_WORD + DESC_BENSON_NAME;
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        deleteAllPersons();
        command = FindUserCommand.COMMAND_WORD + DESC_ALICE_NAME;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
