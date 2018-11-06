package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.event.exceptions.TimePollAddOptionException;
import seedu.address.model.event.exceptions.UserNotJoinedEventException;
import seedu.address.model.event.polls.AbstractPoll;
import seedu.address.model.event.polls.Poll;
import seedu.address.model.event.polls.TimePoll;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Event in the event organiser.
 */
public class Event {

    // Identity fields
    private final String name;

    // Data fields
    private final Address location;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private Person organiser;

    private final Set<Tag> tags = new HashSet<>();
    private final ArrayList<AbstractPoll> polls = new ArrayList<>();
    private final UniquePersonList personList = new UniquePersonList();

    /**
     * Every field must be present and not null.
     */
    public Event(String name, Address address, Set<Tag> tags) {
        requireAllNonNull(name, address, tags);
        this.name = name;
        this.location = address;
        this.tags.addAll(tags);
    }

    public Event(String name, Address location, Set<Tag> tags, LocalDate date, LocalTime startTime, LocalTime endTime,
                 Person organiser) {
        requireAllNonNull(name, location, tags, date, startTime, endTime, organiser);
        this.name = name;
        this.location = location;
        this.tags.addAll(tags);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organiser = organiser;
    }

    public String getName() {
        return name;
    }

    public Address getLocation() {
        return location;
    }

    public Person getOrganiser() {
        return organiser;
    }

    public String getOrganiserAsString() {
        if (organiser == null) {
            return "";
        }
        return organiser.getName().toString();
    }

    public void setOrganiser(Person person) {
        organiser = person;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns true if the date has been set.
     */
    public boolean isDateSet() {
        return (date != null);
    }

    /**
     * Returns true if the start and end times are set.
     */
    public boolean isTimeSet() {
        return (startTime != null && endTime != null);
    }

    /**
     * Returns the date as a string.
     */
    public String getDateString() {
        if (date != null) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return date.format(dateFormat);
        } else {
            return "";
        }
    }

    /**
     * Returns the start and end time as a string.
     */
    public String getTimeString() {
        String result = "";
        if (startTime != null) {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            result += startTime.format(timeFormat) + " - ";
        }
        if (endTime != null) {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            result += endTime.format(timeFormat);
        }
        return result;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start and end time.
     * Throws an IllegalArgumentException if the start time is not after the end time.
     */
    public void setTime(LocalTime startTime, LocalTime endTime) throws IllegalArgumentException {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException();
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Adds a new person to the event.
     */
    public void addPerson(Person person) throws DuplicatePersonException {
        personList.add(person);
    }

    public UniquePersonList getPersonList() {
        return personList;
    }

    /**
     * Sets the person list of the event.
     */
    public void setPersonList(UniquePersonList personList) {
        this.personList.setPersons(personList);
    }

    /**
     * Adds list of persons into the person list.
     */
    public void setPersonList(ArrayList<Person> personList) {
        this.personList.setPersons(personList);
    }

    /**
     * Returns the name list of the people attending as a string.
     */
    public String getNameList() {
        return personList.getNameList();
    }

    /**
     * Adds a new poll to the event and returns the poll.
     */
    public Poll addPoll(String pollName) {
        int id = polls.size() + 1;
        Poll poll = new Poll(id, pollName);
        polls.add(poll);
        return poll;
    }

    /**
     * Adds a new TimePoll object to based on the given start and end dates.
     */
    public TimePoll addTimePoll(LocalDate startDate, LocalDate endDate) {
        int id = polls.size() + 1;
        TimePoll poll = new TimePoll(id, personList, startDate, endDate);
        polls.add(poll);
        return poll;
    }

    /**
     * Gets a poll at the specified index
     */
    public AbstractPoll getPoll(Index index) throws IndexOutOfBoundsException {
        try {
            return polls.get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    public ArrayList<AbstractPoll> getPolls() {
        return polls;
    }

    /**
     * Adds polls into the poll list.
     */
    public void setPolls(ArrayList<AbstractPoll> polls) {
        for (AbstractPoll poll : polls) {
            this.polls.add(poll);
        }
    }

    /**
     * Adds an option to the poll at the given index.
     */
    public Poll addOptionToPoll(Index index, String option) throws TimePollAddOptionException {
        AbstractPoll poll = polls.get(index.getZeroBased());
        if (poll instanceof TimePoll) {
            throw new TimePollAddOptionException();
        } else {
            Poll normalPoll = (Poll) poll;
            normalPoll.addOption(option);
            return normalPoll;
        }
    }

    /**
     * Adds a person to an option of the poll at the specified index, only if person has joined the event.
     */
    public AbstractPoll addVoteToPoll(Index pollIndex, Person person, String option)
            throws UserNotJoinedEventException, DuplicatePersonException {
        if (!personList.contains(person)) {
            throw new UserNotJoinedEventException();
        }
        int index = pollIndex.getZeroBased();
        AbstractPoll poll = polls.get(index);
        poll.addVote(option, person);
        return poll;
    }

    /**
     * Updates the person in the event participant list, organiser, and polls.
     */
    public boolean updatePerson(Person target, Person editedPerson) {
        boolean changed = false;
        if (organiser.equals(target)) {
            setOrganiser(editedPerson);
            changed = true;
        }
        if (personList.contains(target)) {
            personList.setPerson(target, editedPerson);
            changed = true;
        }
        for (AbstractPoll poll : polls) {
            poll.updatePerson(target, editedPerson);
        }
        return changed;
    }

    /**
     * Deletes a person from the event participant list and polls.
     */
    public void deletePerson(Person target) {
        if (personList.contains(target)) {
            personList.remove(target);
        }
        for (AbstractPoll poll : polls) {
            poll.deletePerson(target);
        }
    }

    /**
     * Returns true if the target person is the event organiser is or is an event participant.
     */
    public boolean containsPerson(Name personName) {
        boolean contains = false;
        if (organiser.getName().equals(personName)) {
            contains = true;
        }
        for (Person person : personList) {
            if (person.getName().equals(personName)) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both events of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getLocation().equals(getLocation())
                && otherEvent.getOrganiser().equals(getOrganiser())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndTime().equals(getEndTime());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getLocation().equals(getLocation())
                && otherEvent.getTags().equals(getTags())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndTime().equals(getEndTime())
                && otherEvent.getPersonList().equals(getPersonList())
                && otherEvent.getOrganiser().equals(getOrganiser())
                && otherEvent.getPolls().equals(getPolls());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, tags);
    }

    public Event getCopy() {
        Event copy = new Event(name, location, tags);
        copy.organiser = this.organiser;
        copy.date = this.date;
        copy.startTime = this.startTime;
        copy.endTime = this.endTime;
        this.personList.forEach(person -> copy.personList.add(person));
        for (AbstractPoll poll : polls) {
            if (poll instanceof Poll) {
                Poll genericPoll = (Poll) poll;
                copy.polls.add(genericPoll.copy());
            } else if (poll instanceof TimePoll) {
                TimePoll timePoll = (TimePoll) poll;
                copy.polls.add(timePoll.copy());
            }
        }
        return copy;
    }

    public String getInfo() {
        final StringBuilder builder = new StringBuilder();
        List<String> pollList = polls.stream()
                .map(p -> p.getPollName())
                .collect(Collectors.toList());
        String personNameList = personList.getNameList();
        builder.append("People attending: " + '\n')
                .append(personNameList + '\n')
                .append("Polls: " + '\n');
        Integer index = 1;
        for (String poll : pollList) {
            builder.append(index.toString() + ": " + poll + '\n');
            index += 1;
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
