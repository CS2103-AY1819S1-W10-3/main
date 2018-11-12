//@@author theJrLinguist
package seedu.address.model.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * Predicate to check if event occurs within a certain time frame in a single day
 */
public class EventInTimeFramePredicate implements Predicate<Event> {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;

    public EventInTimeFramePredicate(LocalTime startTime, LocalTime endTime, LocalDate date)
            throws IllegalArgumentException {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException();
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    @Override
    public boolean test(Event event) {
        if (!(event.getDate().isPresent() && event.getStartTime().isPresent()
                && event.getEndTime().isPresent())) {
            return false;
        }
        return event.getDate().get().equals(date)
                && (startTime.isBefore(event.getStartTime().get())
                || startTime.equals(event.getStartTime().get()))
                && ((event.getEndTime().get().isBefore(endTime))
                || event.getEndTime().get().equals(endTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventInTimeFramePredicate // instanceof handles nulls
                && startTime.equals(((EventInTimeFramePredicate) other).startTime)
                && endTime.equals(((EventInTimeFramePredicate) other).endTime)
                && date.equals(((EventInTimeFramePredicate) other).date));
    }
}
