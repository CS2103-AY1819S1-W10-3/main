package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class LoginCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void execute_validPersonLogin_loginSuccessful() throws Exception {
        Person validPerson = new PersonBuilder(ALICE).build();
        ModelStubAcceptingPersonLogin modelStub = new ModelStubAcceptingPersonLogin(validPerson);
        CommandResult commandResult = new LoginCommand(validPerson).execute(modelStub, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validPerson.getName()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidPersonLogin_loginUnsuccessful() {
        Person invalidPerson = new PersonBuilder(BOB).build();
        ModelStubAcceptingPersonLogin modelStub = new ModelStubAcceptingPersonLogin(null);
        Assert.assertThrows(CommandException.class, () -> new LoginCommand(invalidPerson)
            .execute(modelStub, commandHistory));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        LoginCommand loginAliceCommand = new LoginCommand(alice);
        LoginCommand loginBobCommand = new LoginCommand(bob);

        // same object -> returns true
        assertTrue(loginAliceCommand.equals(loginAliceCommand));

        // same values -> returns true
        LoginCommand loginAliceCommandCopy = new LoginCommand(alice);
        assertTrue(loginAliceCommand.equals(loginAliceCommandCopy));

        // different types -> returns false
        assertFalse(loginAliceCommand.equals(1));

        // null -> returns false
        assertFalse(loginAliceCommand == null);

        // different person -> returns false
        assertFalse(loginAliceCommand.equals(loginBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPerson(Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Event getEvent(Index targetIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(Event event, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEvent(int index, Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentUser(Person currentUser) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getCurrentUser() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always allows a person to Login.
     */
    private class ModelStubAcceptingPersonLogin extends ModelStub {
        private Person currentUser;
        private final FilteredList<Person> filteredPersons;

        public ModelStubAcceptingPersonLogin(Person currentUser) {
            List<Person> list = new ArrayList<>();
            list.add(currentUser);
            ObservableList<Person> observableList = FXCollections.observableList(list);
            filteredPersons = observableList.filtered(x -> true);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return filteredPersons.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.unmodifiableObservableList(filteredPersons);
        }

        @Override
        public void setCurrentUser(Person currentUser) {
            this.currentUser = currentUser;
        }
    }
}
