package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditUserCommand extends Command {

    public static final String COMMAND_WORD = "editUser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_PASSWORD + "PASSWORD] "
        + "[" + PREFIX_TIMETABLE + "TIMETABLE] "
        + "[" + PREFIX_INTEREST + "INTEREST] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com "
        + PREFIX_PASSWORD + "password"
        + PREFIX_TIMETABLE + "http://modsn.us/H4v8s";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditUserCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        updateFriendListsDueToEditedPerson(model, lastShownList, personToEdit, editedPerson);

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Password updatedPassword = editPersonDescriptor.getPassword().orElse(personToEdit.getPassword());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Interest> updatedInterests = editPersonDescriptor.getInterests().orElse(personToEdit.getInterests());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Schedule updatedSchedule = editPersonDescriptor.getSchedule().orElse(personToEdit.getSchedule());
        Set<Friend> updatedFriends = editPersonDescriptor.getFriends().orElse(personToEdit.getFriends());
        editPersonDescriptor.getUpdateSchedule().ifPresent((x)-> {
            updatedSchedule.xor(x);
        });

        return new Person(updatedName, updatedPhone, updatedEmail, updatedPassword, updatedAddress, updatedInterests,
                updatedTags, updatedSchedule, updatedFriends);
    }

    /**
     * After the current user's details are edited, this function will search through
     * the user list, and updates the {@code Friend} attribute of the persons with this
     * current user in their friend list
     */
    private void updateFriendListsDueToEditedPerson(Model model, List<Person> personList, Person personToEdit,
                                                   Person editedPerson) throws CommandException {
        for (Person currentPerson : personList) {
            if (currentPerson.hasFriendInList(personToEdit)) {
                Person currentPersonCopy = new Person(currentPerson);
                currentPersonCopy.deleteFriendInList(personToEdit);
                currentPersonCopy.addFriendInList(editedPerson);

                model.updatePerson(currentPerson, currentPersonCopy);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                model.commitAddressBook();
            }
        }
    }

    @Override
    public String toString() {
        return editPersonDescriptor.toString();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditUserCommand)) {
            return false;
        }

        // state check
        EditUserCommand e = (EditUserCommand) other;
        return index.equals(e.index)
            && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Password password;
        private Address address;
        private Schedule schedule;
        private Set<Interest> interests;
        private Set<Tag> tags;
        private Schedule updateSchedule;
        private Set<Friend> friends;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setPassword(toCopy.password);
            setAddress(toCopy.address);
            setInterests(toCopy.interests);
            setTags(toCopy.tags);
            setSchedule(toCopy.schedule);
            setUpdateSchedule(toCopy.updateSchedule);
            setFriends(toCopy.friends);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, password, address, tags, schedule, updateSchedule);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }

        public void setUpdateSchedule(Schedule schedule) {
            this.updateSchedule = schedule;
        }

        public Optional<Schedule> getUpdateSchedule() {
            return Optional.ofNullable(this.updateSchedule);
        }

        public Optional<Schedule> getSchedule() {
            return Optional.ofNullable(schedule);
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code interests} to this object's {@code interests}.
         * A defensive copy of {@code interests} is used internally.
         */
        public void setInterests(Set<Interest> interests) {
            this.interests = (interests != null) ? new HashSet<>(interests) : null;
        }

        /**
         * Returns an unmodifiable interest set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code interests} is null.
         */
        public Optional<Set<Interest>> getInterests() {
            return (interests != null) ? Optional.of(Collections.unmodifiableSet(interests)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code friends} to this object's {@code friends}.
         * A defensive copy of {@code friends} is used internally.
         */
        public void setFriends(Set<Friend> friends) {
            this.friends = (friends != null) ? new HashSet<>(friends) : null;
        }

        /**
         * Returns an unmodifiable friend set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code friends} is null.
         */
        public Optional<Set<Friend>> getFriends() {
            return (friends != null) ? Optional.of(Collections.unmodifiableSet(friends)) : Optional.empty();
        }

        /**
         * Returns true if both persons have the same primary attributes
         * that consist of name, phone, email, address
         */
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress());
        }
    }
}
