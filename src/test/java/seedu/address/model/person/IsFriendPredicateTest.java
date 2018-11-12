package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TypicalPersons;

/**
 * Tests cases for {@code IsFriendPredicate}
 */
public class IsFriendPredicateTest {
    @Test
    public void equals() {
        IsFriendPredicate firstPredicate = new IsFriendPredicate(TypicalPersons.ALICE);
        IsFriendPredicate secondPredicate = new IsFriendPredicate(TypicalPersons.BENSON);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IsFriendPredicate firstPredicateCopy = new IsFriendPredicate(TypicalPersons.ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testForHasFriendInList() {
        IsFriendPredicate predicate = new IsFriendPredicate(TypicalPersons.ALICE);

        // person not in friend list -> returns false
        assertFalse(predicate.test(TypicalPersons.BENSON));
    }
}
