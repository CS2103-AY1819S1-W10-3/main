package seedu.address.model.person;
import java.util.function.Predicate;

/**
 * Tests whether this {@code Person} is in the friend list of the target {@code Person}.
 */
public class IsFriendPredicate implements Predicate<Person> {
    private final Person targetPerson;
    public IsFriendPredicate(Person targetPerson) {
        this.targetPerson = targetPerson;
    }

    @Override
    public boolean test(Person testedPerson) {
        return targetPerson.hasFriendInList(testedPerson);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsFriendPredicate // instanceof handles nulls
                && targetPerson.equals(((IsFriendPredicate) other).targetPerson)); // state check
    }
}
