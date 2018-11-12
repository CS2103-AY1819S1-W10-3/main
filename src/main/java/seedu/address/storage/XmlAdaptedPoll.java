//@@author theJrLinguist
package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.event.polls.AbstractPoll;
import seedu.address.model.event.polls.Poll;
import seedu.address.model.person.UniquePersonList;

/**
 * JAXB-friendly version of the Poll.
 */
public class XmlAdaptedPoll {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Poll's %s field is missing!";

    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String name;

    @XmlElement(required = false)
    private List<XmlAdaptedPollEntry> options = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPoll() {}

    /**
     * Constructs an {@code XmlAdaptedPoll} with the given poll details.
     */
    public XmlAdaptedPoll(String id, String name, List<XmlAdaptedPollEntry> options) {
        this.id = id;
        this.name = name;
        if (options != null) {
            this.options = new ArrayList<>(options);
        }
    }

    /**
     * Converts a given Poll into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPoll(AbstractPoll source) {
        id = Integer.toString(source.getId());
        name = source.getPollName();
        options = source.getPollData()
                .entrySet()
                .stream()
                .map(e -> new XmlAdaptedPollEntry(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Poll object.
     */
    public Poll toModelType() {
        HashMap<String, UniquePersonList> pollData = new HashMap<>();
        for (XmlAdaptedPollEntry entry : options) {
            pollData.put(entry.getOptionName(), entry.getPersonList());
        }
        Poll poll = new Poll(Integer.valueOf(id), name, pollData);
        return poll;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPoll)) {
            return false;
        }

        XmlAdaptedPoll otherPoll = (XmlAdaptedPoll) other;
        return Objects.equals(name, otherPoll.name)
                && Objects.equals(id, otherPoll.id)
                && options.equals(otherPoll.options);
    }
}
