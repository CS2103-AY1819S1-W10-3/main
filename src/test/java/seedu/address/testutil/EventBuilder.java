//@@author theJrLinguist
package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
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
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2018, 1, 1);
    public static final LocalTime DEFAULT_START_TIME = LocalTime.of(12, 00);
    public static final LocalTime DEFAULT_END_TIME = LocalTime.of(13, 30);


    private EventName name;
    private Address address;
    private Person organiser;
    private Set<Tag> tags;
    private ArrayList<AbstractPoll> polls;
    private UniquePersonList participantList;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        organiser = ALICE;
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
        polls = new ArrayList<>();
        participantList = new UniquePersonList();
        date = DEFAULT_DATE;
        startTime = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
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
        participantList = eventToCopy.getParticipantList();
        if (eventToCopy.getStartTime().isPresent()) {
            startTime = eventToCopy.getStartTime().get();
        }
        if (eventToCopy.getEndTime().isPresent()) {
            endTime = eventToCopy.getEndTime().get();
        }
        if (eventToCopy.getDate().isPresent()) {
            date = eventToCopy.getDate().get();
        }
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
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
        this.participantList.add(person);
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
        participantList.add(ALICE);
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
        event.setParticipantList(participantList);
        return event;
    }
}
