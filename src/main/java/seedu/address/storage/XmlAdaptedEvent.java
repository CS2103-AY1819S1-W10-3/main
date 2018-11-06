package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.polls.AbstractPoll;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(XmlAdaptedEvent.class);
    private static ObservableList<Person> personList;

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String organiser;
    @XmlElement(required = false)
    private String startTime = "";
    @XmlElement(required = false)
    private String endTime = "";
    @XmlElement(required = false)
    private String date = "";

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPoll> polls = new ArrayList<>();
    @XmlElement
    private List<XmlPersonIndex> participants = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */

    public XmlAdaptedEvent(String name, String address, String organiser, String date,
                           String startTime, String endTime, List<XmlAdaptedTag> tagged,
                           List<XmlAdaptedPoll> polls, List<XmlPersonIndex> personList) {
        this.name = name;
        this.address = address;
        this.organiser = organiser;
        if (date != null) {
            this.date = date;
        }
        if (startTime != null) {
            this.startTime = startTime;
        }
        if (endTime != null) {
            this.endTime = endTime;
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (polls != null) {
            this.polls = polls;
        }
        if (personList != null) {
            this.participants = personList;
        }
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        name = source.getName();
        address = source.getLocation().value;
        organiser = String.valueOf(personList.indexOf(source.getOrganiser()));
        //organiser = new XmlAdaptedPerson(source.getOrganiser());
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        date = source.getDateString();
        LocalTime start = source.getStartTime();
        if (start != null) {
            this.startTime = start.toString();
        }
        LocalTime end = source.getEndTime();
        if (end != null) {
            endTime = source.getEndTime().toString();
        }
        polls = source.getPolls().stream()
                .map(XmlAdaptedPoll::new)
                .collect(Collectors.toList());
        participants = source.getPersonList()
                .asUnmodifiableObservableList()
                .stream()
                .map(person -> String.valueOf(personList.indexOf(person)))
                .map(XmlPersonIndex::new)
                //.map(XmlAdaptedPerson::new)
                .collect(Collectors.toList());
    }

    /**
     * Provides reference to the person list of the event organiser.
     */
    public static void setPersonList(ObservableList<Person> organiserPersonList) {
        personList = organiserPersonList;
        XmlAdaptedPollEntry.setPersonList(personList);
        XmlPersonIndex.setPersonList(personList);
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> eventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(eventTags);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final String modelName = name;

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        Event event = new Event(modelName, modelAddress, modelTags);

        int organiserIndex = Integer.valueOf(organiser);
        if (organiserIndex != -1) {
            final Person modelOrganiser = personList.get(Integer.valueOf(organiser));
            event.setOrganiser(modelOrganiser);
        }

        if (!date.isEmpty()) {
            LocalDate modelDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            event.setDate(modelDate);
        }

        if (!startTime.isEmpty() && !endTime.isEmpty()) {
            LocalTime modelStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime modelEndTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
            event.setTime(modelStartTime, modelEndTime);
        }

        final ArrayList<AbstractPoll> modelPolls = new ArrayList<>();
        for (XmlAdaptedPoll poll : polls) {
            modelPolls.add(poll.toModelType(personList));
        }
        event.setPolls(modelPolls);

        final ArrayList<Person> modelPersonList = new ArrayList<>();

        //need to catch exceptions
        for (XmlPersonIndex personIndex : participants) {
            try {
                Person modelPerson = personIndex.toModelType();
                modelPersonList.add(modelPerson);
            } catch (PersonNotFoundException e) {
                logger.info("Person not added to participants list.");
            }
        }
        event.setPersonList(modelPersonList);
        return event;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(name, otherEvent.name)
                && Objects.equals(address, otherEvent.address)
                && tagged.equals(otherEvent.tagged);
    }
}
