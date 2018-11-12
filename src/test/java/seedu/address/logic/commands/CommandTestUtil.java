package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.personcommands.EditUserCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_ALICE_FIRST_NAME = "Alice";
    public static final String VALID_NAME_ALICE_LAST_NAME = "Pauline";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_ZOEY = "Zoey Tan";
    public static final String VALID_NAME_MEETING = "Meeting";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_ALICE = "94351253";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_ZOEY = "99999999";
    public static final String VALID_EMAIL_ALICE = "alice@example.com";
    public static final String VALID_EMAIL_ALICE_IN_MIXED_CASES = "aLiCe@ExamPLE.coM";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_ZOEY = "zoey@example.com";
    public static final String VALID_PASSWORD_AMY = "password";
    public static final String VALID_PASSWORD_BOB = "password";
    public static final String VALID_PASSWORD_PASSWORD2 = "password2";
    public static final String VALID_ADDRESS_ALICE = "123, Jurong West Ave 6, #08-111";
    public static final String PART_OF_VALID_ADDRESS_ALICE = "Jurong";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String PART_OF_VALID_ADDRESS_BOB = "Bobby";
    public static final String VALID_INTEREST_STUDY = "study";
    public static final String VALID_INTEREST_STUDY_IN_MIXED_CASES = "sTudY";
    public static final String VALID_INTEREST_SWIM = "swim";
    public static final String VALID_INTEREST_SWIM_IN_MIXED_CASES = "sWIm";
    public static final String VALID_INTEREST_PLAY = "play";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_ALICE = "friends";
    public static final String VALID_TAG_STUDENT = "student";
    public static final String VALID_TAG_ALICE_IN_MIXED_CASES = "fRieNDS";
    public static final String VALID_TAG_HUSBAND_IN_MIXED_CASES = "hUsBAnd";
    public static final String VALID_TIMETABLE = "http://modsn.us/H4v8s";
    public static final String VALID_SCHEDULE_UPDATE_DAY = "monday";
    public static final String VALID_SCHEDULE_UPDATE_TIME = "0100";
    public static final String VALID_TIME = "12:30";
    public static final String VALID_DATE = "12-12-2018";
    public static final String VALID_SCHEDULE = "00000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000"
        +
        "0000000000000000000000000000000000000000000000000000000000000000";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_MEETING = " " + PREFIX_EVENT_NAME + VALID_NAME_MEETING;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String PASSWORD_DESC_AMY = " " + PREFIX_PASSWORD + VALID_PASSWORD_AMY;
    public static final String PASSWORD_DESC_BOB = " " + PREFIX_PASSWORD + VALID_PASSWORD_BOB;
    public static final String INTEREST_DESC_STUDY_AND_PLAY = " " + PREFIX_INTEREST + VALID_INTEREST_STUDY
            + " " + VALID_INTEREST_PLAY;
    public static final String TAG_DESC_HUSBAND_AND_FRIEND = " " + PREFIX_TAG + VALID_TAG_HUSBAND
            + " " + VALID_TAG_FRIEND;
    public static final String TIMETABLE_AMY = "http://modsn.us/H4v8s";
    public static final String SCHEDULE_AMY = "100000000000000011110000000011110000000000"
        +
        "0000000000000000000000000000001111111100000000000000000000000000000000000011"
        +
        "000000111100000000000000000000000000000000000000001111000011110000000000000000"
        +
        "0000000000000011110000111111111100000000000000000000000000000000000000000000000"
        +
        "0000000000000000000000000000000000000000000000000000000000000";
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String INTEREST_DESC_STUDY = " " + PREFIX_INTEREST + VALID_INTEREST_STUDY;
    public static final String INTEREST_DESC_PLAY = " " + PREFIX_INTEREST + VALID_INTEREST_PLAY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String DATE_DESC = " " + PREFIX_DATE + VALID_DATE;
    public static final String START_TIME_DESC = " " + PREFIX_TIME_START + VALID_TIME;
    public static final String ORGANISER_NAME_DESC = " " + PREFIX_ORGANISER_NAME + VALID_NAME_AMY;
    public static final String PARTICIPANT_NAME_DESC = " " + PREFIX_PARTICIPANT_NAME + VALID_NAME_AMY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD; // empty string not allowed for password
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_INTEREST_DESC = " " + PREFIX_INTEREST + "study*"; // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + "";
    public static final String INVALID_TIMETABLE = " " + PREFIX_TIMETABLE + "http://modsn.us/H";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditUserCommand.EditPersonDescriptor DESC_AMY;
    public static final EditUserCommand.EditPersonDescriptor DESC_BOB;
    public static final EditUserCommand.EditPersonDescriptor DESC_ZOEY;

    public static final String VALID_NAME_BOB_FIRST_NAME = "Bob";
    public static final String VALID_NAME_BOB_LAST_NAME = "Choo";

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_ZOEY = new EditPersonDescriptorBuilder().withName(VALID_NAME_ZOEY)
                .withPhone(VALID_PHONE_ZOEY).withEmail(VALID_EMAIL_ZOEY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        try {
            CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().value.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Checkes whether the target person can be found in the friend list of an user
     */
    public static void assertFriendInList(Person personToCheck, Person targetPerson) {
        assertTrue(personToCheck.hasFriendInList(targetPerson));
    }

    /**
     * Checkes whether the target person is not found in the friend list of an user
     */
    public static void assertFriendNotInList(Person personToCheck, Person targetPerson) {
        assertFalse(personToCheck.hasFriendInList(targetPerson));
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().value.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }
}
