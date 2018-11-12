package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TypicalPersons;



/**
 * Tests cases for {@code InterestSimilarPredicate}
 */
public class InterestSimilarPredicateTest {
    @Test
    public void equals() {
        InterestSimilarPredicate firstPredicate = new InterestSimilarPredicate(TypicalPersons.ALICE);
        InterestSimilarPredicate secondPredicate = new InterestSimilarPredicate(TypicalPersons.BENSON);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InterestSimilarPredicate firstPredicateCopy = new InterestSimilarPredicate(TypicalPersons.ALICE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testForSimilarInterests() {
        InterestSimilarPredicate predicate = new InterestSimilarPredicate(TypicalPersons.ALICE);

        // has similar interests -> returns true
        assertTrue(predicate.test(TypicalPersons.BENSON));

        // same person -> returns false
        assertFalse(predicate.test(TypicalPersons.ALICE));

        // no similar interests -> returns false
        assertFalse(predicate.test(TypicalPersons.CARL));
    }
}
