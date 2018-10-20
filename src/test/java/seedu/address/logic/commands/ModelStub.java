package seedu.address.logic.commands;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.Poll;
import seedu.address.model.person.Person;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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
    public void updatePerson(Person target1, Person editedPerson1, Person target2, Person editedPerson2) {
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

    @Override
    public void setSelectedEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Event getSelectedEvent() throws NoEventSelectedException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Poll addPoll(String pollName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Poll addPollOption(Index index, String optionName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Poll voteOption(Index pollIndex, String optionName) {
        throw new AssertionError("This method should not be called.");
    }
}