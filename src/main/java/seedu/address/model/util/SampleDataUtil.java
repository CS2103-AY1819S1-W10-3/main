package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Person ALEX = new Person(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), new Password("password"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            getInterestSet("study"), getTagSet("SOC"));
    private static final Person BERNICE = new Person(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), new Password("password"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getInterestSet("play"), getTagSet("SOC", "SDE"));
    private static final Person CHARLOTTE = new Person(new Name("Charlotte Oliveiro"),
            new Phone("93210283"), new Email("charlotte@example.com"),
            new Password("password"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            getInterestSet("study"), getTagSet("FASS"));
    private static final Person DAVID = new Person(new Name("David Li"),
            new Phone("91031282"), new Email("lidavid@example.com"),
            new Password("password"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            getInterestSet("play"), getTagSet("FASS"));
    private static final Person IRFAN = new Person(new Name("Irfan Ibrahim"),
            new Phone("92492021"), new Email("irfan@example.com"),
            new Password("password"), new Address("Blk 47 Tampines Street 20, #17-35"),
            getInterestSet("study"), getTagSet("Dentistry"));
    private static final Person ROY = new Person(new Name("Roy Balakrishnan"),
            new Phone("92624417"), new Email("royb@example.com"),
            new Password("password"), new Address("Blk 45 Aljunied Street 85, #11-31"),
            getInterestSet("play"), getTagSet("Business"));

    public static Person[] getSamplePersons() {
        return new Person[] { ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event("CS2103 weekly meeting", new Address("NUS SoC Canteen"), getTagSet("Urgent"),
                    LocalDate.of(2018, 12, 12), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), ALEX),
            new Event("CS2103 weekly meeting", new Address("NUS SoC Canteen"), getTagSet(),
                    LocalDate.of(2018, 12, 19), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), ALEX),
            new Event("CS2100 discussion", new Address("NUS Deck"), getTagSet(),
                    LocalDate.of(2018, 11, 12), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), DAVID),
            new Event("Week 12 karaoke session", new Address("Clementi MRT"), getTagSet("Public"),
                    LocalDate.of(2018, 11, 8), LocalTime.of(20, 30),
                    LocalTime.of(22, 30), ROY),
            new Event("Linguistics Reading Group", new Address("NUS Central Library"), getTagSet(),
                    LocalDate.of(2018, 11, 12), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event("Linguistics Reading Group", new Address("NUS Central Library"), getTagSet(),
                    LocalDate.of(2018, 11, 19), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event("Bernice's BD party", new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(22, 00),
                    LocalTime.of(23, 30), DAVID),
            new Event("11th floor party", new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 11, 15), LocalTime.of(22, 00),
                    LocalTime.of(23, 00), IRFAN),
            new Event("Hackathon", new Address("SoC LT19"), getTagSet(),
                    LocalDate.of(2018, 11, 27), LocalTime.of(10, 00),
                    LocalTime.of(22, 30), BERNICE),
            new Event("Lunch", new Address("NUS Techno"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), IRFAN),
            new Event("EOY Christmas Party", new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 22), LocalTime.of(19, 30),
                    LocalTime.of(22, 00), IRFAN)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a interest set containing the list of strings given.
     */
    public static Set<Interest> getInterestSet(String... strings) {
        return Arrays.stream(strings)
                .map(Interest::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a friend set containing the list of strings given.
     */
    public static Set<Friend> getFriendSet(String... strings) {
        return Arrays.stream(strings)
                .map(Friend::new)
                .collect(Collectors.toSet());
    }

}
