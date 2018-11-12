package seedu.address.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    boolean hasEvent(Event event);

    /**
     * Returns the number of events
     */
    int getNumEvents();

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    void updateEvent(Event target, Event editedEvent);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * adds an event to the address book.
     *
     * @param toAdd the event to be added.
     */
    void addEvent(Event toAdd);

    /**
     * deletes an event from the address book.
     *
     * @param target the event to be deleted
     */
    void deleteEvent(Event target);

    /**
     * Edits the name, location and tags of the event.
     */
    void editEvent(Optional<EventName> name, Optional<Address> location, Optional<Set<Tag>> tags);

    /**
     * Gets the event in the address book.
     *
     * @param targetIndex Index of the event.
     */
    Event getEvent(Index targetIndex);

    /**
     * Adds a poll to the pre-selected event with the given name.
     *
     * @param pollName the poll name.
     */
    String addPoll(String pollName);

    /**
     * Creates a time poll to the pre-selected event with the given name.
     */
    String addTimePoll(LocalDate startDate, LocalDate endDate);

    /**
     * Adds a poll option to the poll at the given index of the pre-selected event.
     *
     * @param index      the index of the poll in the list of polls.
     * @param optionName the name of the option.
     */
    String addPollOption(Index index, String optionName);

    /**
     * Adds the current user as a voter for a given option.
     *
     * @param pollIndex  the index of the poll in the list of polls.
     * @param optionName the name of the option.
     */
    String voteOption(Index pollIndex, String optionName);

    /**
     * Adds a person to the event at the given index.
     *
     * @param index the index of the event to join.
     */
    void joinEvent(Index index);

    /**
     * Sets the current user of the address book.
     *
     * @param currentUser
     */
    void setCurrentUser(Person currentUser);

    /**
     * Gets the current user of the address book.
     */
    Person getCurrentUser();

    /**
     * Gets the person in the address book
     *
     * @param targetIndex Index of the person.
     */
    Person getPerson(Index targetIndex);

    /**
     * Sets the selected event.
     */
    void setSelectedEvent(Event currentEvent);

    /**
     * Gets the selected event.
     */
    Event getSelectedEvent();

    /**
     * Enable clear command, only for testing purposes.
     */
    void setClearEnabled();

    /**
     * Get whether clear is enabled.
     */
    boolean getClearIsEnabled();

    /**
     * Sets the date of the pre-selected event.
     */
    void setDate(LocalDate date);

    /**
     * Sets the start and end time of the pre-selected event.
     */
    void setTime(LocalTime startTime, LocalTime endTime);

    /**
     * Checks if a person exists within the model.
     * @param person the Person to be authenticated.
     */
    Person authenticateUser(Person person);

    /**
     * Logs out user.
     */
    void removeCurrentUser();

    /**
     * Checks if currentUser has been set.
     */
    boolean hasSetCurrentUser();
}
