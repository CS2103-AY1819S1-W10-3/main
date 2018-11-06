package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "94351253";
    public static final String DEFAULT_EMAIL = "alice@example.com";
    public static final String DEFAULT_PASSWORD = "password";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_INTERESTS = "study";
    public static final String DEFAULT_SCHEDULE = "0000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "00000000";


    private Name name;
    private Phone phone;
    private Email email;
    private Password password;
    private Address address;
    private Schedule schedule;
    private Set<Interest> interests;
    private Set<Tag> tags;
    private Set<Friend> friends;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        password = new Password(DEFAULT_PASSWORD);
        address = new Address(DEFAULT_ADDRESS);
        interests = new HashSet<>();
        interests.add(new Interest(DEFAULT_INTERESTS));
        tags = new HashSet<>();
        schedule = new Schedule(DEFAULT_SCHEDULE);
        friends = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        password = personToCopy.getPassword();
        address = personToCopy.getAddress();
        interests = new HashSet<>(personToCopy.getInterests());
        tags = new HashSet<>(personToCopy.getTags());
        schedule = new Schedule(personToCopy.getSchedule().valueToString());
        friends = new HashSet<>(personToCopy.getFriends());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code interests} into a {@code Set<Interest>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withInterests(String... interests) {
        this.interests = SampleDataUtil.getInterestSet(interests);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code friends} into a {@code Set<Friend>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withFriends(String... friends) {
        this.friends = SampleDataUtil.getFriendSet(friends);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Person} that we are building.
     */
    public PersonBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Schedule} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchedule(String schedule) {
        this.schedule = new Schedule(schedule);
        return this;
    }


    /**
     * Builds a person.
     */
    public Person build() {
        return new Person(name, phone, email, password, address, interests, tags, schedule, friends);
    }


    /**
     * @param updateDay
     * @param updateTime
     * @return
     * @throws ParseException
     */
    public PersonBuilder withUpdateSchedule(String updateDay, String updateTime) throws ParseException {
        Schedule updateSchedule = new Schedule();
        updateSchedule.setTimeDay(updateDay, updateTime, true);
        this.schedule.xor(updateSchedule);
        return this;
    }
}
