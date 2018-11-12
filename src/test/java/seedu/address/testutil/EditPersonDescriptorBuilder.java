package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.personcommands.EditUserCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setInterests(person.getInterests());
        descriptor.setTags(person.getTags());
        descriptor.setFriends(person.getFriends());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code interests} into a {@code Set<Interest>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withInterests(String... interests) {
        Set<Interest> interestSet = Stream.of(interests).map(Interest::new).collect(Collectors.toSet());
        descriptor.setInterests(interestSet);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }

    /**
     * Sets the {@code Schedule} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param schedule
     * @return
     */
    public EditPersonDescriptorBuilder withSchedule(String schedule) {
        descriptor.setSchedule(new Schedule(schedule));
        return this;
    }

    /**
     * Sets the {@code UpdateSchedule} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param validScheduleUpdateDay validScheduleUpdateTime
     * @return
     */
    public EditPersonDescriptorBuilder withUpdateSchedule(String validScheduleUpdateDay,
                                                          String validScheduleUpdateTime) throws ParseException {
        Schedule updateSchedule = new Schedule();
        Slot slot = new Slot(validScheduleUpdateDay, validScheduleUpdateTime);
        updateSchedule.setTimeDay(slot, true);
        descriptor.setUpdateSchedule(updateSchedule);
        return this;
    }
}
