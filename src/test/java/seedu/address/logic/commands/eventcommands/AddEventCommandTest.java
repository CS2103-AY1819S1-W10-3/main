//@@author theJrLinguist
package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddEventCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        AddEventCommandTest.ModelStubAcceptingEventAdded modelStub = new
                AddEventCommandTest.ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();
        modelStub.setCurrentUser(new PersonBuilder().build());

        CommandResult commandResult = new AddEventCommand(validEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        Event validEvent = new EventBuilder().build();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent);
        AddEventCommandTest.ModelStub modelStub = new AddEventCommandTest.ModelStubWithEvent(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);
        addEventCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_noUser_throwsCommandException() throws Exception {
        AddEventCommandTest.ModelStubAcceptingEventAdded modelStub = new
                AddEventCommandTest.ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_USER_LOGGED_IN);

        new AddEventCommand(validEvent).execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Event tutorial = new EventBuilder().withName("Tutorial").build();
        Event meeting = new EventBuilder().withName("Meeting").build();
        AddEventCommand addTutorialCommand = new AddEventCommand(tutorial);
        AddEventCommand addMeetingCommand = new AddEventCommand(meeting);

        // same object -> returns true
        assertTrue(addTutorialCommand.equals(addTutorialCommand));

        // same values -> returns true
        AddEventCommand addTutorialCommandCopy = new AddEventCommand(tutorial);
        assertTrue(addTutorialCommand.equals(addTutorialCommandCopy));

        // different types -> returns false
        assertFalse(addTutorialCommand.equals(1));

        // null -> returns false
        assertFalse(addTutorialCommand.equals(null));

        // different person -> returns false
        assertFalse(addTutorialCommand.equals(addMeetingCommand));
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
        public void updateEvent(int index, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentUser(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getCurrentUser() throws NoUserLoggedInException {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends AddEventCommandTest.ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends AddEventCommandTest.ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();
        private Person currentUser = null;

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void setCurrentUser(Person person) {
            currentUser = person;
        }

        @Override
        public Person getCurrentUser() throws NoUserLoggedInException {
            if (currentUser == null) {
                throw new NoUserLoggedInException();
            }
            return currentUser;
        }

    }

}
