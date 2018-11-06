//@@author theJrLinguist
package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.polls.AbstractPoll;
import seedu.address.model.event.polls.Poll;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_NAME = "Tutorial";
    public static final String DEFAULT_ADDRESS = "NUS UTown";
    public static final String DEFAULT_TAG = "friends";
    public static final String DEFAULT_POLL = "Date";

    private String name;
    private Address address;
    private Person organiser;
    private Set<Tag> tags;
    private ArrayList<AbstractPoll> polls;
    private UniquePersonList personList;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public EventBuilder() {
        name = DEFAULT_NAME;
        address = new Address(DEFAULT_ADDRESS);
        organiser = ALICE;
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
        polls = new ArrayList<>();
        personList = new UniquePersonList();
        date = LocalDate.of(2018, 1, 1);
        startTime = LocalTime.of(12, 00);
        endTime = LocalTime.of(13, 30);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        address = eventToCopy.getLocation();
        organiser = eventToCopy.getOrganiser();
        tags = new HashSet<>(eventToCopy.getTags());
        polls = new ArrayList<>(eventToCopy.getPolls());
        personList = eventToCopy.getPersonList();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
        date = eventToCopy.getDate();
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code organiser} of the {@code Event} that we are building.
     */
    public EventBuilder withOrganiser(Person person) {
        this.organiser = person;
        return this;
    }

    /**
     * Adds a {@code poll} to the {@code Event} that we are building.
     */
    public EventBuilder withPoll() {
        polls.add(new Poll(polls.size(), DEFAULT_POLL));
        return this;
    }

    /**
     * Adds one person as a participant to the event.
     */
    public EventBuilder withParticipant() {
        personList.add(ALICE);
        return this;
    }

    /**
     * Builds an event.
     */
    public Event build() {
        Event event = new Event(name, address, tags);
        event.setDate(date);
        event.setTime(startTime, endTime);
        event.setOrganiser(organiser);
        event.setPolls(polls);
        event.setPersonList(personList);
        return event;
    }
}
