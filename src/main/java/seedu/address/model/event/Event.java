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
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Event in the event organiser.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private static int currID = 0;
    private final Name name;
    private final int id;

    // Data fields
    private final Address location;

    private LocalDate date;
    private LocalTime time;

    private final Set<Tag> tags = new HashSet<>();

    private final ArrayList<Poll> polls;
  
    private final UniquePersonList personList;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Address address, Set<Tag> tags) {
        requireAllNonNull(name, address, tags);
        this.id = currID;
        currID++;
        this.name = name;
        this.location = address;
        this.tags.addAll(tags);
        polls = new ArrayList<>();
        personList = new UniquePersonList();
    }

    public Name getName() {
        return name;
    }

    public Address getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
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
     * Returns the time as a string.
     */
    public String getTimeString() {
        if (time != null) {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            return time.format(timeFormat);
        } else {
            return "";
        }
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Adds a new person to the event.
     */
    public void addPerson(Person person) throws DuplicatePersonException {
        try {
            personList.add(person);
        } catch (DuplicatePersonException e){
            throw e;
        }
    }

    public UniquePersonList getPersonList() {
        return personList;
    }

    /**
     * Adds list of persons into the person list.
     */
    public void setPersonList(ArrayList<Person> personList) {
        for (Person person : personList) {
            this.personList.add(person);
        }
    }

    /**
     * Returns the name list of the people attending as a string.
     */
    public String getNameList() {
        return personList.getNameList();
    }

    /**
     * Adds a new poll to the event.
     */
    public void addPoll(String pollName) {
        int id = polls.size() + 1;
        Poll poll = new Poll(id, pollName);
        polls.add(poll);
    }

    /**
     * Gets a poll at the specified index
     */
    public Poll getPoll(Index index) throws IndexOutOfBoundsException {
        try {
            return polls.get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    public ArrayList<Poll> getPolls() {
        return polls;
    }

    /**
     * Adds polls into the poll list.
     */
    public void setPolls(ArrayList<Poll> polls) {
        for (Poll poll : polls) {
            this.polls.add(poll);
        }
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
                && otherEvent.getName().equals(getName());
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

        if (!(other instanceof seedu.address.model.event.Event)) {
            return false;
        }

        Event otherEvent = (seedu.address.model.event.Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getLocation().equals(getLocation())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, location, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getLocation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
