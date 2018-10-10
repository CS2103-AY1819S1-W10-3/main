//@@author theJrLinguist
package seedu.address.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of a single poll entry.
 */
public class XmlAdaptedPollEntry {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Poll option's %s field is missing!";
    private static ObservableList<Person> personList;

    @XmlElement(required = true)
    private String name;

    @XmlElement(required = false)
    private List<XmlPersonIndex> voterList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPollEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPollEntry() {}

    /**
     * Constructs an {@code XmlAdaptedPollEntry} with the given poll entry details.
     */
    public XmlAdaptedPollEntry(String name, LinkedList<Person> voterList) {
        this.name = name;
        if (voterList != null) {
            this.voterList = voterList.stream()
                    .map(person -> String.valueOf(personList.indexOf(person)))
                    .map(XmlPersonIndex::new)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Provides reference to the person list of the event organiser.
     */
    public static void setPersonList(ObservableList<Person> organiserPersonList) {
        personList = organiserPersonList;
    }

    public String getOptionName() {
        return name;
    }

    public LinkedList<Person> getPersonList(ObservableList<Person> personList) throws IllegalValueException {
        LinkedList<Person> persons = new LinkedList<>();
        for (XmlPersonIndex personIndex : voterList) {
            try {
                Person modelPerson = personIndex.toModelType();
                persons.add(modelPerson);
            } catch (IllegalValueException e) {
                throw e;
            }
        }
        return persons;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPollEntry)) {
            return false;
        }

        XmlAdaptedPollEntry otherPollEntry = (XmlAdaptedPollEntry) other;
        return Objects.equals(name, otherPollEntry.name)
                && voterList.equals(otherPollEntry.voterList);
    }
}
