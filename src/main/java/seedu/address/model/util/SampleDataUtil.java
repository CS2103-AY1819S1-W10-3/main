package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
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
    private static final Person Amony = new Person(new Name("Amony Richman"),
        new Phone("93628942"), new Email("amonr@example.com"),
        new Password("password"), new Address("Blk 452 Hougang Street 83, #21-31"),
        getInterestSet("gaming"), getTagSet("FASS"));
    private static final Person RICHARD = new Person(new Name("Richard Craftman"),
        new Phone("92344417"), new Email("richardc@example.com"),
        new Password("password"), new Address("Blk 453 Pasir Ris Street 25, #03-31"),
        getInterestSet("play"), getTagSet("Dentistry"));
    private static final Person ROYCE = new Person(new Name("Royce Chocolater"),
        new Phone("92621417"), new Email("roycec@example.com"),
        new Password("password"), new Address("Blk 451 Marine Parade Street 81, #11-21"),
        getInterestSet("play"), getTagSet("Business"));
    private static final Person CHICKIN = new Person(new Name("Chickin Riceman"),
        new Phone("92621217"), new Email("chickinr@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("play"), getTagSet("Business"));
    private static final Person NAKESHAS = new Person(new Name("Nakesha Sovie"),
        new Phone("91664359"), new Email("nakeshas@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("tuberculomania"), getTagSet("SDE"));
    private static final Person ELFRIEDAT = new Person(new Name("Elfrieda Topping"),
        new Phone("97916051"), new Email("elfriedat@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("peel"), getTagSet("SOC"));
    private static final Person YERL = new Person(new Name("Yer Landfried"),
        new Phone("90578590"), new Email("yerl@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("anisogenous"), getTagSet("SDE"));
    private static final Person ESTHERG = new Person(new Name("Esther Galasso"),
        new Phone("99408147"), new Email("estherg@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("outbelch"), getTagSet("FOS"));
    private static final Person BLAIRP = new Person(new Name("Blair Paniagua"),
        new Phone("93397502"), new Email("blairp@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("similiter"), getTagSet("SDE"));
    private static final Person NORBERTP = new Person(new Name("Norbert Pitcak"),
        new Phone("98431734"), new Email("norbertp@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("dialectic"), getTagSet("NUH"));
    private static final Person LIGIAP = new Person(new Name("Ligia Prust"),
        new Phone("98348005"), new Email("ligiap@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("unbody"), getTagSet("SOC"));
    private static final Person GAYLEP = new Person(new Name("Gayle Pang"),
        new Phone("94360507"), new Email("gaylep@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("premegalithic"), getTagSet("SOC"));
    private static final Person ROSALINEH = new Person(new Name("Rosaline Hannah"),
        new Phone("90248451"), new Email("rosalineh@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("sicula"), getTagSet("SDE"));
    private static final Person SHAKITAH = new Person(new Name("Shakita Highers"),
        new Phone("97960704"), new Email("shakitah@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("pancratic"), getTagSet("SDE"));
    private static final Person LEONARDAV = new Person(new Name("Leonarda Venzon"),
        new Phone("90800518"), new Email("leonardav@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("uphillward"), getTagSet("SOC"));
    private static final Person BRONWYNK = new Person(new Name("Bronwyn Kirker"),
        new Phone("97591438"), new Email("bronwynk@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("ripgut"), getTagSet("FOS"));
    private static final Person MOISESO = new Person(new Name("Moises Oberbeck"),
        new Phone("93154709"), new Email("moiseso@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("pantheistical"), getTagSet("Business"));
    private static final Person LOUIEK = new Person(new Name("Louie Kalinoski"),
        new Phone("91096845"), new Email("louiek@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Transylvanian"), getTagSet("SOC"));
    private static final Person GAYEH = new Person(new Name("Gaye Havercroft"),
        new Phone("92538289"), new Email("gayeh@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("router"), getTagSet("Business"));
    private static final Person KELLYP = new Person(new Name("Kelly Phariss"),
        new Phone("90854117"), new Email("kellyp@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("apse"), getTagSet("SOC"));
    private static final Person BRITANYM = new Person(new Name("Britany Meleo"),
        new Phone("97485962"), new Email("britanym@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("decomposer"), getTagSet("FOS"));
    private static final Person STEPHENF = new Person(new Name("Stephen Fanno"),
        new Phone("95159836"), new Email("stephenf@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("rickshaw"), getTagSet("SOC"));
    private static final Person TANJAL = new Person(new Name("Tanja Luciano"),
        new Phone("94639027"), new Email("tanjal@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("incrossing"), getTagSet("FOS"));
    private static final Person ALEJANDROE = new Person(new Name("Alejandro Ehly"),
        new Phone("95941078"), new Email("alejandroe@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("peptonemia"), getTagSet("FOS"));
    private static final Person KENNYA = new Person(new Name("Kenny Antonucci"),
        new Phone("95209422"), new Email("kennya@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("posthumousness"), getTagSet("Business"));
    private static final Person KENDRAN = new Person(new Name("Kendra Nicole"),
        new Phone("93536771"), new Email("kendran@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("uninvalidated"), getTagSet("Business"));
    private static final Person SUSANNAB = new Person(new Name("Susanna Bey"),
        new Phone("90619422"), new Email("susannab@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("acrobatics"), getTagSet("SOC"));
    private static final Person LORIS = new Person(new Name("Lori Santanna"),
        new Phone("99358977"), new Email("loris@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("cardiectasis"), getTagSet("SDE"));
    private static final Person ROSELEEB = new Person(new Name("Roselee Bouten"),
        new Phone("91554176"), new Email("roseleeb@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("oratorian"), getTagSet("FOS"));
    private static final Person CATHERINAK = new Person(new Name("Catherina Kirtner"),
        new Phone("98058531"), new Email("catherinak@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("ateeter"), getTagSet("Business"));
    private static final Person LORYR = new Person(new Name("Lory Rossini"),
        new Phone("95835623"), new Email("loryr@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("downtrodden"), getTagSet("NUH"));
    private static final Person MICHEALW = new Person(new Name("Micheal Whinnery"),
        new Phone("91068931"), new Email("michealw@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("varietism"), getTagSet("SOC"));
    private static final Person ZELLAS = new Person(new Name("Zella Stefanick"),
        new Phone("90886406"), new Email("zellas@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("bettong"), getTagSet("FASS"));
    private static final Person NENITAD = new Person(new Name("Nenita Dhar"),
        new Phone("91731094"), new Email("nenitad@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("azoparaffin"), getTagSet("FOS"));
    private static final Person CARMELOS = new Person(new Name("Carmelo Shearing"),
        new Phone("95829633"), new Email("carmelos@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("prefigure"), getTagSet("SDE"));
    private static final Person BRYANTS = new Person(new Name("Bryant Slater"),
        new Phone("93485686"), new Email("bryants@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("obmutescence"), getTagSet("FASS"));
    private static final Person TANISHAM = new Person(new Name("Tanisha Marchetta"),
        new Phone("90827372"), new Email("tanisham@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("janissary"), getTagSet("FASS"));
    private static final Person NOEL = new Person(new Name("Noe Lenigan"),
        new Phone("91546477"), new Email("noel@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("demijohn"), getTagSet("SDE"));
    private static final Person JIMMYS = new Person(new Name("Jimmy Stopa"),
        new Phone("98095338"), new Email("jimmys@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("prevalent"), getTagSet("SDE"));
    private static final Person BERTRAMG = new Person(new Name("Bertram Garon"),
        new Phone("98870388"), new Email("bertramg@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("gallet"), getTagSet("SDE"));
    private static final Person JANAET = new Person(new Name("Janae Tarran"),
        new Phone("99992880"), new Email("janaet@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("rudimentary"), getTagSet("SOC"));
    private static final Person RONNAL = new Person(new Name("Ronna Luetkemeyer"),
        new Phone("95598290"), new Email("ronnal@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("questioner"), getTagSet("SDE"));
    private static final Person SHELBAH = new Person(new Name("Shelba Handkins"),
        new Phone("97715824"), new Email("shelbah@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("vatic"), getTagSet("Business"));
    private static final Person ELDRIDGES = new Person(new Name("Eldridge Shrier"),
        new Phone("98617272"), new Email("eldridges@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("bereavement"), getTagSet("FOS"));
    private static final Person SHANS = new Person(new Name("Shan Safrit"),
        new Phone("96870860"), new Email("shans@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("inspiritment"), getTagSet("NUH"));
    private static final Person SEAND = new Person(new Name("Sean Deeley"),
        new Phone("98031353"), new Email("seand@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Placaean"), getTagSet("NUH"));
    private static final Person LINETTEB = new Person(new Name("Linette Bomilla"),
        new Phone("94022216"), new Email("linetteb@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("caciquism"), getTagSet("NUH"));
    private static final Person CAMERONT = new Person(new Name("Cameron Taplin"),
        new Phone("94647642"), new Email("cameront@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("hemozoon"), getTagSet("FOS"));
    private static final Person JULENEG = new Person(new Name("Julene Grande"),
        new Phone("90291309"), new Email("juleneg@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("unchance"), getTagSet("Business"));
    private static final Person DANNS = new Person(new Name("Dann Signore"),
        new Phone("94339094"), new Email("danns@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("tyloma"), getTagSet("NUH"));
    private static final Person DEREKG = new Person(new Name("Derek Gideon"),
        new Phone("90076767"), new Email("derekg@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("overpunishment"), getTagSet("FASS"));
    private static final Person KEVIND = new Person(new Name("Kevin Dencklau"),
        new Phone("92579252"), new Email("kevind@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("haglin"), getTagSet("NUH"));
    private static final Person SUZANNEH = new Person(new Name("Suzanne Hayashida"),
        new Phone("96476068"), new Email("suzanneh@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("plagiograph"), getTagSet("Business"));
    private static final Person ROYH = new Person(new Name("Roy Helgert"),
        new Phone("99708498"), new Email("royh@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("polestar"), getTagSet("NUH"));

    public static Person[] getSamplePersons() {
        return new Person[] {ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY, Amony, RICHARD, ROYCE,
            CHICKIN, NAKESHAS, ELFRIEDAT, YERL, ESTHERG, BLAIRP, NORBERTP, LIGIAP, GAYLEP, ROSALINEH,
            SHAKITAH, LEONARDAV, BRONWYNK, MOISESO, LOUIEK, GAYEH, KELLYP, BRITANYM, STEPHENF, TANJAL,
            ALEJANDROE, KENNYA, KENDRAN, SUSANNAB, LORIS, ROSELEEB, CATHERINAK, LORYR, MICHEALW, ZELLAS,
            NENITAD, CARMELOS, BRYANTS, TANISHAM, NOEL, JIMMYS, BERTRAMG, JANAET, RONNAL, SHELBAH,
            ELDRIDGES, SHANS, SEAND, LINETTEB, CAMERONT, JULENEG, DANNS, DEREKG, KEVIND, SUZANNEH,
            ROYH
        };
    }

    /**
     * Creates an event with a poll and poll options.
     */
    private static Event createEventWithPoll() {
        Event eventWithPoll = new Event(new EventName("CS2103 weekly meeting"), new Address("NUS SoC Canteen"),
                getTagSet("URGENT"),
                LocalDate.of(2018, 12, 19), LocalTime.of(12, 30),
                LocalTime.of(13, 00), ALEX);
        eventWithPoll.addPoll("Meeting date");
        eventWithPoll.addOptionToPoll(Index.fromOneBased(1), "12 December");
        eventWithPoll.addOptionToPoll(Index.fromOneBased(1), "13 December");
        return eventWithPoll;
    }

    private static Event[] getSampleEvents() {
        return new Event[] {
            createEventWithPoll(),
            new Event(new EventName("CS2100 discussion"), new Address("NUS Deck"), getTagSet(),
                    LocalDate.of(2018, 11, 12), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), DAVID),
            new Event(new EventName("Week 12 karaoke session"), new Address("Clementi MRT"),
                    getTagSet("Public"),
                    LocalDate.of(2018, 11, 8), LocalTime.of(20, 30),
                    LocalTime.of(22, 30), ROY),
            new Event(new EventName("Linguistics Reading Group"), new Address("NUS Central Library"),
                    getTagSet("Week12"),
                    LocalDate.of(2018, 11, 12), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event(new EventName("Linguistics Reading Group"), new Address("NUS Central Library"),
                    getTagSet("Week13"),
                    LocalDate.of(2018, 11, 19), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event(new EventName("Linguistics Reading Group"), new Address("NUS Central Library"),
                    getTagSet("Week14"),
                    LocalDate.of(2018, 11, 26), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event(new EventName("Bernice's BD party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(22, 00),
                    LocalTime.of(23, 30), DAVID),
            new Event(new EventName("David's BD party"), new Address("Residental College 4"), getTagSet(),
                    LocalDate.of(2018, 12, 14), LocalTime.of(22, 00),
                    LocalTime.of(23, 30), BERNICE),
            new Event(new EventName("11th floor party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 11, 15), LocalTime.of(22, 00),
                    LocalTime.of(23, 00), IRFAN),
            new Event(new EventName("Hackathon"), new Address("SoC LT19"), getTagSet(),
                    LocalDate.of(2018, 11, 27), LocalTime.of(10, 00),
                    LocalTime.of(22, 30), BERNICE),
            new Event(new EventName("Lunch"), new Address("NUS Techno"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), IRFAN),
            new Event(new EventName("Dinner"), new Address("NUS Techno"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(19, 30),
                    LocalTime.of(20, 00), IRFAN),
            new Event(new EventName("EOY Christmas Party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 22), LocalTime.of(19, 30),
                    LocalTime.of(22, 00), IRFAN),
            new Event(new EventName("New Year Party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 29), LocalTime.of(19, 30),
                    LocalTime.of(22, 30), IRFAN),
            new Event(new EventName("EOY Floor Party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 10), LocalTime.of(21, 30),
                    LocalTime.of(22, 30), DAVID),
            new Event(new EventName("CS1010 Discussion"), new Address("Tembusu College"), getTagSet(),
                    LocalDate.of(2018, 12, 10), LocalTime.of(21, 30),
                    LocalTime.of(22, 30), DAVID),
            new Event(new EventName("CS1101S Discussion"), new Address("Tembusu College"), getTagSet(),
                    LocalDate.of(2018, 12, 11), LocalTime.of(21, 30),
                    LocalTime.of(22, 30), RICHARD),
            new Event(new EventName("CS1101S Discussion"), new Address("Tembusu College"), getTagSet(),
                    LocalDate.of(2018, 12, 13), LocalTime.of(21, 30),
                    LocalTime.of(22, 30), BERNICE),
            new Event(new EventName("IFG Badminton"), new Address("Indoor Sports Hall 4"), getTagSet(),
                    LocalDate.of(2018, 1, 15), LocalTime.of(12, 30),
                    LocalTime.of(14, 00), ROY),
            new Event(new EventName("IFG Soccer"), new Address("Indoor Sports Hall 4"), getTagSet(),
                    LocalDate.of(2018, 1, 17), LocalTime.of(12, 30),
                    LocalTime.of(15, 00), ROY),
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
