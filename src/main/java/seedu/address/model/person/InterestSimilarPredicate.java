package seedu.address.model.person;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.interest.Interest;

/**
 * Tests whether at least one of this {@code Person}'s {@code Interest}
 * matches target {@code Person}'s {@code Interest}.
 */
public class InterestSimilarPredicate implements Predicate<Person> {
    private final Person targetPerson;
    public InterestSimilarPredicate(Person targetPerson) {
        this.targetPerson = targetPerson;
    }

    @Override
    public boolean test(Person testedPerson) {
        Set<Interest> similarInterests = new HashSet<>(targetPerson.getInterests());

        if (targetPerson.equals(testedPerson)) { // do not show the target person in the displayed list
            return false;
        }
        if (targetPerson.hasFriendInList(testedPerson)) { // do not show persons who are already friends
            return false;
        }

        similarInterests.retainAll(testedPerson.getInterests());
        return similarInterests.size() > 0; // check if they have at least one similar interest
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterestSimilarPredicate // instanceof handles nulls
                && targetPerson.equals(((InterestSimilarPredicate) other).targetPerson)); // state check
    }
}
