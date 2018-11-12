package seedu.address.logic.commands.personcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NO_USER_LOGGED_IN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_UPDATE_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_UPDATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.personcommands.EditUserCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditUserCommand.
 */
public class EditUserCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        model.setCurrentUser(ALICE);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = ALICE;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);

        String expectedMessage = String.format(EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(editUserCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        PersonBuilder personInList = new PersonBuilder(ALICE);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);

        String expectedMessage = String.format(EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editUserCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeScheduleFieldsSpecifiedUnfilteredListSuccess() throws ParseException {
        PersonBuilder personInList = new PersonBuilder(ALICE);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withSchedule(VALID_SCHEDULE).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withSchedule(VALID_SCHEDULE).build();
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);

        String expectedMessage = String.format(EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(ALICE, editedPerson);

        assertCommandSuccess(editUserCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void executeUpdateScheduleFieldsSpecifiedUnfilteredListSuccess() throws ParseException {
        PersonBuilder personInList = new PersonBuilder(ALICE);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withSchedule(VALID_SCHEDULE)
                .withUpdateSchedule(VALID_SCHEDULE_UPDATE_DAY, VALID_SCHEDULE_UPDATE_TIME).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withSchedule(VALID_SCHEDULE)
                .withUpdateSchedule(VALID_SCHEDULE_UPDATE_DAY, VALID_SCHEDULE_UPDATE_TIME).build();
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);

        String expectedMessage = String.format(EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(ALICE, editedPerson);

        assertCommandSuccess(editUserCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditUserCommand editUserCommand = new EditUserCommand(new EditPersonDescriptor());
        Person editedPerson = ALICE;
        String expectedMessage = String.format(EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editUserCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Person personInFilteredList = ALICE;
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditUserCommand editUserCommand = new EditUserCommand(
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditUserCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(ALICE, editedPerson);

        assertCommandSuccess(editUserCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        model.setCurrentUser(ALICE);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(secondPerson).build();
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);

        assertCommandFailure(editUserCommand, model, commandHistory, EditUserCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_noUserLoggedIn_failure() {
        model.removeCurrentUser();
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(secondPerson).build();
        EditUserCommand editUserCommand = new EditUserCommand(descriptor);

        assertCommandFailure(editUserCommand, model, commandHistory, MESSAGE_NO_USER_LOGGED_IN);
    }

    @Test
    public void equals() {
        final EditUserCommand standardCommand = new EditUserCommand(DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditUserCommand commandWithSameValues = new EditUserCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearUserCommand()));

        // different index -> returns true
        assertTrue(standardCommand.equals(new EditUserCommand(DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditUserCommand(DESC_BOB)));
    }
}
