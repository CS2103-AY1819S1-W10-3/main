//@@author theJrLinguist
package seedu.address.model.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * Predicate to check if event has any of the given attributes.
 */
public class EventAttributesPredicate implements Predicate<Event> {
    private String name;
    private Address address;
    private LocalDate date;
    private LocalTime startTime;
    private Name participant;
    private Name organiser;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setParticipant(Name participant) {
        this.participant = participant;
    }

    public void setOrganiser(Name organiser) {
        this.organiser = organiser;
    }

    @Override
    public boolean test(Event event) {
        return (date != null
                && event.getDate().equals(date))
                || (name != null
                && event.getName().equals(name))
                || (address != null
                && event.getLocation().equals(address))
                || (startTime != null
                && event.getStartTime().equals(startTime))
                || (organiser != null
                && event.getOrganiser().equals(organiser))
                || (participant != null
                && event.containsPerson(participant));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EventAttributesPredicate)) {
            return false;
        }
        EventAttributesPredicate otherPredicate = (EventAttributesPredicate) other;

        return (startTime == null
                || startTime.equals((otherPredicate).startTime))
                && (name == null
                || name.equals((otherPredicate).name))
                && (address == null
                || address.equals((otherPredicate).address))
                && (date == null
                || date.equals(((otherPredicate).date)))
                && (organiser == null
                || organiser.equals((otherPredicate).organiser))
                && (participant == null
                || participant.equals((otherPredicate).participant));
    }
}
