package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
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

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String schedule;

    @XmlElement
    private List<XmlAdaptedInterest> interests = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedFriend> friends = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String password, String address,
                            List<XmlAdaptedInterest> interests, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        if (interests != null) {
            this.interests = new ArrayList<>(interests);
        } else {
            Interest interest = new Interest();
            this.interests.add(new XmlAdaptedInterest(interest));
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String password, String address,
                            List<XmlAdaptedInterest> interests, List<XmlAdaptedTag> tagged,
                            List<XmlAdaptedFriend> friends) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        if (interests != null) {
            this.interests = new ArrayList<>(interests);
        } else {
            Interest interest = new Interest();
            this.interests.add(new XmlAdaptedInterest(interest));
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (friends != null) {
            this.friends = new ArrayList<>(friends);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        password = source.getPassword().value;
        address = source.getAddress().value;
        schedule = source.getSchedule().valueToString();
        interests = source.getInterests().stream()
                .map(XmlAdaptedInterest::new)
                .collect(Collectors.toList());
        tagged = source.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
        friends = source.getFriends().stream()
               .map(XmlAdaptedFriend::new)
               .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Interest> personInterests = new ArrayList<>();
        final List<Tag> personTags = new ArrayList<>();
        final List<Friend> personFriends = new ArrayList<>();
        for (XmlAdaptedInterest interest : interests) {
            personInterests.add(interest.toModelType());
        }
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        for (XmlAdaptedFriend friend : friends) {
            personFriends.add(friend.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Schedule modelSchedule;
        if (schedule == null) {
            modelSchedule = new Schedule();
        } else {
            modelSchedule = new Schedule(schedule);
        }

        final Set<Interest> modelInterests;
        if (interests == null) {
            modelInterests = new HashSet<>();
            modelInterests.add(new Interest());
        } else {
            modelInterests = new HashSet<>(personInterests);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Set<Friend> modelFriends = new HashSet<>(personFriends);

        return new Person(modelName, modelPhone, modelEmail, modelPassword, modelAddress, modelInterests, modelTags,
                modelSchedule, modelFriends);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<XmlAdaptedInterest> getInterests() {
        return interests;
    }

    public List<XmlAdaptedTag> getTagged() {
        return tagged;
    }

    public List<XmlAdaptedFriend> getFriends() {
        return friends;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
            && Objects.equals(phone, otherPerson.phone)
            && Objects.equals(email, otherPerson.email)
            && Objects.equals(password, otherPerson.password)
            && Objects.equals(address, otherPerson.address)
            && interests.equals(otherPerson.interests)
            && tagged.equals(otherPerson.tagged)
            && friends.equals(otherPerson.friends);
    }
}
