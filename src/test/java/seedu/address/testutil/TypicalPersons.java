package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withPassword("password")
            .withInterests("study")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withPassword("password")
            .withInterests("study")
            .withTags("owesMoney", "friends")
            .build();
    public static final Person KENSON = new PersonBuilder()
            .withName("Kenson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("kenson@example.com")
            .withPhone("98765432")
            .withInterests("study")
            .withTags("owesMoney", "friends")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    public static final PersonBuilder ALICE_BUILDER = new PersonBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withInterests("study")
            .withTags("friends");
    public static final PersonBuilder BENSON_BUILDER = new PersonBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withInterests("study")
            .withTags("owesMoney", "friends");
    public static final PersonBuilder CARL_BUILDER = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street");
    public static final PersonBuilder DANIEL_BUILDER = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends");
    public static final PersonBuilder ELLE_BUILDER = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave");
    public static final PersonBuilder FIONA_BUILDER = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo");
    public static final PersonBuilder GEORGE_BUILDER = new PersonBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street");

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withPassword(VALID_PASSWORD_AMY)
            .withAddress(VALID_ADDRESS_AMY).withInterests(VALID_INTEREST_STUDY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withPassword(VALID_PASSWORD_BOB)
            .withAddress(VALID_ADDRESS_BOB).withInterests(VALID_INTEREST_STUDY)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    public static final Person CHARLIE = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String DESC_MATCHING_MEIER = " n/Meier";
    public static final String DESC_ALICE_NAME = " n/Alice";
    public static final String DESC_BENSON_NAME = " n/Benson";
    public static final String DESC_BENSON_PHONE = " p/98765432";
    public static final String DESC_ALICE_AND_BENSON_ADDRESS = " a/Jurong Clementi";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL = " e/anna@example.com lydia@example.com";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL_REVERSED = " e/lydia@example.com anna@example.com";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL_REPEATED =
            " e/lydia@example.com lydia@example.com anna@example.com";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL_NONMATCHING = " e/lydia@example.com anna@example.com x";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL_DIFFERENT_CASES = " e/LYdia@ExampLE.com anNA@example.COM";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL_SUBSTRING = " e/lydi anna";
    public static final String DESC_GEORGE_AND_FIONA_EMAIL_SUPERSTRING = " e/lydia@example.com.sg anna@example.com.sg";
    public static final String DESC_ALICE_AND_BENSON_AND_KENSON_INTEREST = " i/study";
    public static final String DESC_BENSON_TAG = " t/owesMoney";

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE_BUILDER.build(), BENSON_BUILDER.build(), CARL_BUILDER.build(),
                DANIEL_BUILDER.build(), ELLE_BUILDER.build(), FIONA_BUILDER.build(), GEORGE_BUILDER.build()));
    }
}
