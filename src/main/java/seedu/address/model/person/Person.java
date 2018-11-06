package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.interest.Interest;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private Name name;
    private Phone phone;
    private Email email;

    // Data fields
    private Password password;
    private Address address;
    private Schedule schedule;
    private Set<Interest> interests = new HashSet<>();
    private Set<Tag> tags = new HashSet<>();
    private Set<Friend> friends = new HashSet<>();

    private boolean isLoggedIn = false;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Password password, Address address, Set<Interest> interests,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, password, address, interests, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.interests.addAll(interests);
        this.tags.addAll(tags);
        this.schedule = new Schedule();
        this.friends = new HashSet<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Password password, Address address, Set<Interest> interests,
                  Set<Tag> tags, Schedule schedule) {
        requireAllNonNull(name, phone, email, password, address, interests, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.interests.addAll(interests);
        this.tags.addAll(tags);
        this.schedule = schedule;
        this.friends = new HashSet<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Password password, Address address, Set<Interest> interests,
                  Set<Tag> tags, Schedule schedule, Set<Friend> friends) {
        requireAllNonNull(name, phone, email, password, address, interests, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.interests.addAll(interests);
        this.tags.addAll(tags);
        this.schedule = schedule;
        this.friends = friends;
    }

    /**
     * Make a duplicate of a person
     */
    public Person(Person other) {
        this.name = other.name;
        this.phone = other.phone;
        this.email = other.email;
        this.password = other.password;
        this.address = other.address;
        this.interests = new HashSet<>(other.interests);
        this.tags = new HashSet<>(other.tags);
        this.schedule = other.schedule;
        this.friends = new HashSet<>(other.friends);
    }

    /**
     * Stub user used in user login process.
     */
    public Person(Name name, Password password) {
        requireAllNonNull(name, password);
        this.name = name;
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public Schedule getSchedule() {
        return schedule;
    }


    /**
     * Returns an immutable interest set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Interest> getInterests() {
        return Collections.unmodifiableSet(interests);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an Set of friends, which represent the friends of the current person.
     */
    public Set<Friend> getFriends() {
        return friends;
    }

    /**
     * Populates all attributes with that of the new person.
     */
    public void editPerson(Person newPerson) {
        name = newPerson.getName();
        address = newPerson.getAddress();
        phone = newPerson.getPhone();
        password = newPerson.getPassword();
        tags = newPerson.getTags();
        email = newPerson.getEmail();
        interests = newPerson.getInterests();
        schedule = newPerson.getSchedule();
        friends = newPerson.getFriends();
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both users of the same name and password.
     * This defines a weaker notion of similarity of two persons.
     */
    public boolean isSameUser(Person otherPerson) {
        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPassword().equals(getPassword()));
    }

    public boolean isUserWithOnlyNameAndPassword() {
        return phone == null;
    }

    /**
     * Returns true if this person has the other person in the friends list.
     */
    public boolean hasFriendInList(Person otherPerson) {
        return friends.contains(new Friend(otherPerson));
    }

    /**
     * Adds a new person into the friends list.
     * throws {@code CommandException} if the new person is already in the list
     */
    public void addFriendInList(Person otherPerson) throws CommandException {
        if (hasFriendInList(otherPerson)) {
            throw new CommandException(Messages.MESSAGE_ALREADY_FRIENDS);
        } else {
            friends.add(new Friend(otherPerson));
        }
    }

    /**
     * Removes a person from the friends list if it is present.
     * throws {@code CommandException} if the person is not yet in the list
     */
    public void deleteFriendInList(Person otherPerson) throws CommandException {
        if (hasFriendInList(otherPerson)) {
            friends.remove(new Friend(otherPerson));
        } else {
            throw new CommandException(Messages.MESSAGE_NOT_FRIENDS);
        }
    }

    /**
     *  Logs in a user
     */
    public void login() {
        this.isLoggedIn = true;
    }

    /**
     *  Logs out a user
     */
    public void logout() {
        this.isLoggedIn = false;
    }

    /**
     *  Gets the login status of a user
     */
    public boolean getLoginStatus() {
        return isLoggedIn;
    }

    /**
     * Returns true if both persons have the same primary attributes
     * that consist of name, phone, email, address
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && (this.phone == null || otherPerson.getPhone().equals(getPhone()))
                && (this.email == null || otherPerson.getEmail().equals(getEmail()))
                && otherPerson.getPassword().equals(getPassword())
                && (this.address == null || otherPerson.getAddress().equals(getAddress()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, password, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Password: ")
                .append(getPassword())
                .append(" Address: ")
                .append(getAddress())
                .append(" Schedule: ")
                .append(getSchedule().valueToString())
                .append(" Interests: ");
        getInterests().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Friends: ");
        getFriends().forEach(builder::append);
        return builder.toString();
    }
}
