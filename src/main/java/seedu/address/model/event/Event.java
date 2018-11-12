//@@author theJrLinguist
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
import java.util.Optional;
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

    private EventName name;
    private Address location;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private Person organiser;

    private Set<Tag> tags = new HashSet<>();
    private final ArrayList<AbstractPoll> polls = new ArrayList<>();
    private final UniquePersonList participantList = new UniquePersonList();

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name, Address address, Set<Tag> tags) {
        requireAllNonNull(name, address, tags);
        this.name = name;
        this.location = address;
        this.tags.addAll(tags);
    }

    public Event(EventName name, Address location, Set<Tag> tags, LocalDate date, LocalTime startTime,
                 LocalTime endTime, Person organiser) {
        requireAllNonNull(name, location, tags, date, startTime, endTime, organiser);
        this.name = name;
        this.location = location;
        this.tags.addAll(tags);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organiser = organiser;
        this.participantList.add(organiser);
    }

    public EventName getName() {
        return name;
    }

    public void setName(EventName name) {
        this.name = name;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
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

    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    /**
     * Returns an optional wrapper around startTime.
     */
    public Optional<LocalTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }
    /**
     * Returns an optional wrapper around endTime.
     */
    public Optional<LocalTime> getEndTime() {
        return Optional.ofNullable(endTime);
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

    /**
     * Adds a new person to the event.
     */
    public void addParticipant(Person person) throws DuplicatePersonException {
        participantList.add(person);
    }

    public UniquePersonList getParticipantList() {
        return participantList;
    }

    /**
     * Sets the participant list of the event.
     */
    public void setParticipantList(UniquePersonList participantList) {
        this.participantList.setPersons(participantList);
    }

    /**
     * Adds list of persons into the participant list.
     */
    public void setParticipantList(ArrayList<Person> personList) {
        this.participantList.setPersons(personList);
    }

    /**
     * Returns the name list of the people attending as a string.
     */
    public String getNameList() {
        return participantList.getNameList();
    }

    /**
     * Adds a new poll to the event and returns the poll display details as a string.
     */
    public String addPoll(String pollName) {
        int id = polls.size() + 1;
        Poll poll = new Poll(id, pollName);
        polls.add(poll);
        return poll.displayPoll();
    }

    /**
     * Adds a new TimePoll object to based on the given start and end dates.
     */
    public String addTimePoll(LocalDate startDate, LocalDate endDate) {
        int id = polls.size() + 1;
        TimePoll poll = new TimePoll(id, participantList, startDate, endDate);
        polls.add(poll);
        return poll.displayPoll();
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
        this.polls.addAll(polls);
    }

    /**
     * Adds an option to the poll at the given index and returns the poll display as a string.
     */
    public String addOptionToPoll(Index index, String option) throws TimePollAddOptionException {
        AbstractPoll poll = polls.get(index.getZeroBased());
        if (poll instanceof TimePoll) {
            throw new TimePollAddOptionException();
        } else {
            Poll normalPoll = (Poll) poll;
            normalPoll.addOption(option);
            return normalPoll.displayPoll();
        }
    }

    /**
     * Adds a person to an option of the poll at the specified index, only if person has joined the event.
     * Returns the poll display as a string.
     */
    public String addVoteToPoll(Index pollIndex, Person person, String option)
            throws UserNotJoinedEventException, DuplicatePersonException {
        if (!participantList.contains(person)) {
            throw new UserNotJoinedEventException();
        }
        int index = pollIndex.getZeroBased();
        AbstractPoll poll = polls.get(index);
        poll.addVote(option, person);
        return poll.displayPoll();
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
        if (participantList.contains(target)) {
            participantList.setPerson(target, editedPerson);
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
        if (participantList.contains(target)) {
            participantList.remove(target);
        }
        for (AbstractPoll poll : polls) {
            poll.deletePerson(target);
        }
    }

    /**
     * Returns true if the target person is the event organiser or is an event participant.
     */
    public boolean containsPerson(Name personName) {
        boolean contains = false;
        if (organiser.getName().equals(personName)) {
            contains = true;
        }
        for (Person person : participantList) {
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

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Returns true if both events of the same name share their location, organiser and tags.
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
                && otherEvent.getTags().equals(getTags());
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

        boolean datesAreEqual = (otherEvent.getDate().isPresent()
                && getDate().isPresent()
                && otherEvent.getDate().get().equals(getDate().get()))
                || (!otherEvent.getDate().isPresent()
                && !getDate().isPresent());

        boolean startTimesAreEquals = (otherEvent.getStartTime().isPresent()
                && getStartTime().isPresent()
                && otherEvent.getStartTime().get().equals(getStartTime().get()))
                || (!otherEvent.getStartTime().isPresent()
                && !getStartTime().isPresent());

        boolean endTimesAreEquals = (otherEvent.getEndTime().isPresent()
                && getEndTime().isPresent()
                && otherEvent.getEndTime().get().equals(getEndTime().get()))
                || (!otherEvent.getEndTime().isPresent()
                && !getEndTime().isPresent());

        return otherEvent.getName().equals(getName())
                && otherEvent.getLocation().equals(getLocation())
                && otherEvent.getTags().equals(getTags())
                && datesAreEqual
                && startTimesAreEquals
                && endTimesAreEquals
                && otherEvent.getParticipantList().equals(getParticipantList())
                && otherEvent.getOrganiser().equals(getOrganiser())
                && otherEvent.getPolls().equals(getPolls());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, tags);
    }

    /**
     * Returns a deep copy of the event object.
     */
    public Event getCopy() {
        Event copy = new Event(name, location, tags);
        copy.organiser = organiser;
        copy.date = date;
        copy.startTime = startTime;
        copy.endTime = endTime;
        copy.setParticipantList(participantList);
        copy.polls.addAll(copyPollList());
        return copy;
    }

    /**
     * Returns a deep copy of the poll list.
     */
    private ArrayList<AbstractPoll> copyPollList() {
        ArrayList<AbstractPoll> pollListCopy = new ArrayList<>();
        for (AbstractPoll poll : polls) {
            if (poll instanceof Poll) {
                Poll genericPoll = (Poll) poll;
                pollListCopy.add(genericPoll.copy());
            } else if (poll instanceof TimePoll) {
                TimePoll timePoll = (TimePoll) poll;
                pollListCopy.add(timePoll.copy());
            }
        }
        return pollListCopy;
    }

    public String getInfo() {
        final StringBuilder builder = new StringBuilder();
        List<String> pollList = polls.stream()
                .map(p -> p.getPollName())
                .collect(Collectors.toList());
        String personNameList = participantList.getNameList();
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
