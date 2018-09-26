package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.Poll;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = false)
    private String time = "";
    @XmlElement(required = false)
    private String date = "";

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPoll> polls = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPerson> personList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */

    public XmlAdaptedEvent(String name, String address, String date, String time,
                           List<XmlAdaptedTag> tagged, List<XmlAdaptedPoll> polls,
                           List<XmlAdaptedPerson> personList) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (polls != null) {
            this.polls = polls;
        }
        if (personList != null) {
            this.personList = personList;
        }
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        name = source.getName().fullName;
        address = source.getLocation().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        date = source.getDateString();
        time = source.getTimeString();
        polls = source.getPolls().stream()
                .map(XmlAdaptedPoll::new)
                .collect(Collectors.toList());
        personList = source.getPersonList()
                .asUnmodifiableObservableList()
                .stream()
                .map(XmlAdaptedPerson::new)
                .collect(Collectors.toList());
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
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        Event event = new Event(modelName, modelAddress, modelTags);

        if (!date.isEmpty()) {
            LocalDate modelDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            event.setDate(modelDate);
        }

        if (!time.isEmpty()) {
            LocalTime modelTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            event.setTime(modelTime);
        }

        final ArrayList<Poll> modelPolls = new ArrayList<>();
        for (XmlAdaptedPoll poll : polls) {
            modelPolls.add(poll.toModelType());
        }
        event.setPolls(modelPolls);

        final ArrayList<Person> modelPersonList = new ArrayList<>();
        for (XmlAdaptedPerson person : personList) {
            modelPersonList.add(person.toModelType());
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
