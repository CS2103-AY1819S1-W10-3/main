package seedu.address.model.interest;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Interest in the address book.
 * Guarantees: immutable; interest is valid as declared in {@link #isValidInterestName(String)}
 *
 * @author agendazhang
 *
 */
public class Interest {

    public static final String MESSAGE_INTEREST_CONSTRAINTS = "Interest names should be alphanumeric";
    public static final String INTEREST_VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String VALID_INTEREST_STUDY = "study";

    public final String interestName;

    /**
     * Constructs a {@code Interest}.
     *
     * @param interestName A valid Interest name.
     */
    public Interest(String interestName) {
        requireNonNull(interestName);
        checkArgument(isValidInterestName(interestName), MESSAGE_INTEREST_CONSTRAINTS);
        this.interestName = interestName;
    }

    public Interest() {
        this.interestName = VALID_INTEREST_STUDY;
    }

    /**
     * Returns true if a given string is a valid interest name.
     */
    public static boolean isValidInterestName(String test) {
        return test.matches(INTEREST_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interest // instanceof handles nulls
                && interestName.equals(((Interest) other).interestName)); // state check
    }

    @Override
    public int hashCode() {
        return interestName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + interestName + ']';
    }

}
