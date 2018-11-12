//@@author theJrLinguist
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * JAXB-friendly version of a single poll entry.
 */
public class XmlAdaptedPollEntry {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Poll option's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(XmlAdaptedPollEntry.class);
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
    public XmlAdaptedPollEntry(String name, UniquePersonList voterList) {
        this.name = name;
        if (voterList != null) {
            this.voterList = voterList.asUnmodifiableObservableList()
                    .stream()
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

    /**
     * Returns a UniquePersonList constructed from the voter list stored by XmlPersonIndex.
     */
    public UniquePersonList getPersonList() {
        UniquePersonList persons = new UniquePersonList();
        for (XmlPersonIndex personIndex : voterList) {
            try {
                Person modelPerson = personIndex.toModelType();
                persons.add(modelPerson);
            } catch (PersonNotFoundException e) {
                logger.info("Person cannot be found and not added to voter list.");
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
