//@@theJrLinguist
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly index which refers to a person in the organiser.
 */
public class XmlPersonIndex {

    private static ObservableList<Person> personList;

    @XmlValue
    private String index;

    /**
     * Constructs an XmlPersonIndex.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlPersonIndex() {}

    /**
     * Constructs a {@code XmlPersonIndex} with the given {@code index}.
     */
    public XmlPersonIndex(String index) {
        this.index = index;
    }

    /**
     * Provides reference to the person list of the event organiser.
     */
    public static void setPersonList(ObservableList<Person> organiserPersonList) {
        personList = organiserPersonList;
    }

    /**
     * Returns a model Person.
     */
    public Person toModelType() throws IllegalValueException {
        try {
            return personList.get(Integer.valueOf(index));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException("No such person exists in the event organiser");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlPersonIndex)) {
            return false;
        }

        return index.equals(((XmlPersonIndex) other).index);
    }
}
