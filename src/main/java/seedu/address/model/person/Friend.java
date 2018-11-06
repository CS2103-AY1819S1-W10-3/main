package seedu.address.model.person;

import java.util.StringTokenizer;

/**
 * Represents a Person that is a friend of the current person using a String
 * that consists of his/her name, phone, email, address as the primary key.
 *
 * @author agendazhang
 *
 */
public class Friend {

    public final String friendAttributes;
    public final String verticalBar = "|";

    /**
     * Constructs a {@code Friend} given a {@code Person}
     */
    public Friend(Person person) {
        friendAttributes = person.getName() + verticalBar + person.getPhone() + verticalBar
                + person.getEmail() + verticalBar + person.getAddress();
    }

    /**
     * Constructs a {@code Friend} given a String
     */
    public Friend(String friendAttributes) {
        this.friendAttributes = friendAttributes;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Friend // instanceof handles nulls
                && friendAttributes.equals(((Friend) other).friendAttributes)); // state check
    }

    @Override
    public int hashCode() {
        return friendAttributes.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        StringTokenizer st = new StringTokenizer(friendAttributes, verticalBar);
        return '[' + st.nextToken() + ']';
    }

}
