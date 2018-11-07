package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.UserLoginStatusChangedEvent;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.NotEventOrganiserException;
import seedu.address.model.event.exceptions.UserNotJoinedEventException;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents the in-memory model of the event organiser data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> filteredEvents;
    private Person currentUser;
    private Event currentEvent;
    private boolean clearEnabled;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredEvents = new FilteredList<>(versionedAddressBook.getEventList());
        currentUser = null;
        clearEnabled = false;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
        indicateUserLoginStatusChanged();
    }

    /**
     * Checks if a person exists within the model.
     * @param person the Person to be authenticated.
     */
    public Person authenticateUser(Person person) {
        for (Person p: filteredPersons) {
            if (person.isSameUser(p)) {
                p.login();
                return p;
            }
        }
        return person;
    }

    /**
     * Logs out user.
     */
    public void removeCurrentUser() {
        currentUser.logout();
        currentUser = null;
        indicateUserLoginStatusChanged();
    }

    public boolean hasSetCurrentUser() {
        return (currentUser != null);
    }

    public Person getCurrentUser() throws NoUserLoggedInException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        return currentUser;
    }

    /**
     * Enable clear command, only for testing purposes.
     */
    public void setClearEnabled() {
        clearEnabled = true;
    }

    public boolean getClearEnabled() {
        return clearEnabled;
    }

    public void setSelectedEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Event getSelectedEvent() throws NoEventSelectedException {
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        return currentEvent;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate that a user has logged in */
    private void indicateUserLoginStatusChanged() {
        raise(new UserLoginStatusChangedEvent(null));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        if (target.equals(currentUser)) {
            currentUser = null;
        }
        ArrayList<Event> deletedEvents = new ArrayList<>();
        for (Event event : versionedAddressBook.getEventList()) {
            if (event.getOrganiser().equals(target)) {
                deletedEvents.add(event);
            }
            event.deletePerson(target);
        }
        for (Event event : deletedEvents) {
            versionedAddressBook.removeEvent(event);
        }
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@theJrLinguist
    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedAddressBook.updatePerson(target, editedPerson);
        for (Event event : versionedAddressBook.getEventList()) {
            boolean changed = event.updatePerson(target, editedPerson);
            if (changed) {
                versionedAddressBook.updateEvent(event, event);
            }
        }
        indicateAddressBookChanged();
    }

    public Person getPerson(Index targetIndex) {
        return filteredPersons.get(targetIndex.getZeroBased());
    }

    //===========Events ======================================================================================
    @Override
    public void addEvent(Event event) throws NoUserLoggedInException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        event.setOrganiser(currentUser);
        event.addPerson(currentUser);
        versionedAddressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteEvent(Event target) throws NotEventOrganiserException {
        if (target.equals(currentEvent)) {
            currentEvent = null;
        }
        if (!target.getOrganiser().equals(currentUser)) {
            throw new NotEventOrganiserException();
        }
        versionedAddressBook.removeEvent(target);
        indicateAddressBookChanged();
    }

    /**
     * Check if an event already exists in a versionedAddressBook
     * @param event
     * @return true if versionAddressBook already has this event.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedAddressBook.hasEvent(event);
    }

    public Event getEvent(Index targetIndex) {
        return filteredEvents.get(targetIndex.getZeroBased());
    }

    @Override
    public String addPoll(String pollName) throws NoUserLoggedInException, NoEventSelectedException,
            NotEventOrganiserException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        int index = versionedAddressBook.getEventList().indexOf(currentEvent);
        String pollDetails = currentEvent.addPoll(pollName);
        updateEvent(index, currentEvent);
        return pollDetails;
    }

    @Override
    public String addTimePoll(LocalDate startDate, LocalDate endDate) throws NoUserLoggedInException,
            NoEventSelectedException, NotEventOrganiserException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        String pollDetails = currentEvent.addTimePoll(startDate, endDate);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public String addPollOption(Index index, String option) throws NoEventSelectedException {
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        String pollDetails = currentEvent.addOptionToPoll(index, option);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public String voteOption(Index index, String optionName) throws NoEventSelectedException,
            NoUserLoggedInException, UserNotJoinedEventException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        String pollDetails = currentEvent.addVoteToPoll(index, currentUser, optionName);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public void setDate(LocalDate date) throws NoEventSelectedException, NoUserLoggedInException,
            NotEventOrganiserException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        currentEvent.setDate(date);
        updateEvent(currentEvent, currentEvent);
    }

    @Override
    public void setTime(LocalTime startTime, LocalTime endTime) throws NoEventSelectedException,
            NoUserLoggedInException, NotEventOrganiserException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        currentEvent.setTime(startTime, endTime);
        updateEvent(currentEvent, currentEvent);
    }


    @Override
    public void joinEvent(Index index) throws NoUserLoggedInException, DuplicatePersonException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        Event event = getEvent(index);
        event.addPerson(currentUser);
        updateEvent(event, event);
    }

    @Override
    public void updateEvent(Event target, Event editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updateEvent(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateEvent(int index, Event editedPerson) {
        requireAllNonNull(index, editedPerson);

        versionedAddressBook.updateEvent(index, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredEvents.equals(other.filteredEvents);
    }

}
