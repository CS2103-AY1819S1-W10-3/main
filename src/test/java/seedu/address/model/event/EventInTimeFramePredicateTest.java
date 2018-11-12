//@@author theJrLinguist
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.testutil.EventBuilder;

public class EventInTimeFramePredicateTest {

    @Test
    public void equals() {
        LocalDate firstPredicateDate = LocalDate.of(2018, 1, 1);
        LocalDate secondPredicateDate = LocalDate.of(2018, 2, 2);
        LocalTime firstPredicateStartTime = LocalTime.of(12, 30);
        LocalTime secondPredicateStartTime = LocalTime.of(18, 00);
        LocalTime firstPredicateEndTime = LocalTime.of(15, 30);
        LocalTime secondPredicateEndTime = LocalTime.of(21, 00);

        EventInTimeFramePredicate firstPredicate = new EventInTimeFramePredicate(firstPredicateStartTime,
                firstPredicateEndTime, firstPredicateDate);
        EventInTimeFramePredicate secondPredicate = new EventInTimeFramePredicate(secondPredicateStartTime,
                secondPredicateEndTime, secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventInTimeFramePredicate firstPredicateCopy = new EventInTimeFramePredicate(firstPredicateStartTime,
                firstPredicateEndTime, firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventInTimeFrame_returnsTrue() {
        //time range larger
        LocalDate date = LocalDate.of(2018, 1, 1);
        LocalTime startTime = LocalTime.of(11, 30);
        LocalTime endTime = LocalTime.of(15, 30);
        EventInTimeFramePredicate predicate = new EventInTimeFramePredicate(startTime, endTime, date);
        assertTrue(predicate.test(new EventBuilder().build()));

        //time range exact
        Event event = new EventBuilder().build();
        date = event.getDate().get();
        startTime = event.getStartTime().get();
        endTime = event.getEndTime().get();
        predicate = new EventInTimeFramePredicate(startTime, endTime, date);
        assertTrue(predicate.test(event));
    }

    @Test
    public void test_eventNotInTimeFrame_returnsFalse() {
        LocalDate date = LocalDate.of(2018, 1, 1);
        LocalTime startTime = LocalTime.of(18, 30);
        LocalTime endTime = LocalTime.of(21, 30);
        EventInTimeFramePredicate predicate = new EventInTimeFramePredicate(startTime, endTime, date);
        assertFalse(predicate.test(new EventBuilder().build()));
    }

    @Test
     public void test_invalidTimeRange() {
        LocalDate date = LocalDate.of(2018, 1, 1);
        LocalTime startTime = LocalTime.of(18, 30);
        LocalTime earlierEndTime = LocalTime.of(14, 30);
        assertThrows(IllegalArgumentException.class, () -> {
            EventInTimeFramePredicate predicate = new EventInTimeFramePredicate(startTime, earlierEndTime, date);
        });
    }
}
