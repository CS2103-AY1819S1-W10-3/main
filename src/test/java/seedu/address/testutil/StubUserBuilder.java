package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class StubUserBuilder {

    public static final String VALID_NAME_ALPHA = "Alpha Pauline";
    public static final String VALID_PASSWORD_ALPHA = "password";

    public static final String NAME_DESC_ALPHA = " " + PREFIX_NAME + VALID_NAME_ALPHA;
    public static final String PASSWORD_DESC_ALPHA = " " + PREFIX_PASSWORD + VALID_PASSWORD_ALPHA;

    private Name name;
    private Password password;

    public StubUserBuilder() {
        name = new Name(VALID_NAME_ALPHA);
        password = new Password(VALID_PASSWORD_ALPHA);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public StubUserBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Person} that we are building.
     */
    public StubUserBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Builds a stub user for log in.
     */
    public Person build() {
        return new Person(name, password);
    }

}
