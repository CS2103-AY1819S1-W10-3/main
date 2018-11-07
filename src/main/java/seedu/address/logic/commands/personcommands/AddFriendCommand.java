package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Given two persons, add friends for each other using two displayed indexes from the address book.
 * Friendships must be bilateral, for example person A and B must be friends with each other.
 *
 * @author agendazhang
 */
public class AddFriendCommand extends Command {

    public static final String COMMAND_WORD = "addFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": For two persons identified by the index number used in the displayed person list, "
            + "add friend for each other.\n"
            + "Parameters: INDEX,INDEX (both must be a positive integer and different from each other)\n"
            + "Example: " + COMMAND_WORD + " 1,2";

    public static final String MESSAGE_ADD_FRIEND_SUCCESS = "Friends added: %1$s, %2$s";

    private final Index indexes;

    public AddFriendCommand(Index indexes) {
        this.indexes = indexes;
    }

    /**
     * Given two persons, add each other into their friend lists.
     * throws {@code CommandException} if the each other is already in the list.
     */
    public static void addFriendEachOther(Person person1, Person person2) throws CommandException {
        person1.addFriendInList(person2);
        person2.addFriendInList(person1);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (indexes.getZeroBased() >= lastShownList.size()
                || indexes.getZeroBased2() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (indexes.getZeroBased() == indexes.getZeroBased2()) {
            throw new CommandException(Messages.MESSAGE_CANNOT_ADD_FRIEND_OWNSELF);
        }

        Person person1 = lastShownList.get(indexes.getZeroBased());
        Person person2 = lastShownList.get(indexes.getZeroBased2());
        Person person1Copy = new Person(person1);
        Person person2Copy = new Person(person2);
        addFriendEachOther(person1Copy, person2Copy);

        model.updatePerson(person1, person1Copy);
        model.updatePerson(person2, person2Copy);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_FRIEND_SUCCESS, person1.getName(),
                person2.getName()));
    }

    @Override
    public String toString() {
        return COMMAND_WORD + " " + indexes.getZeroBased() + StringUtil.COMMA + indexes.getZeroBased2();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFriendCommand // instanceof handles nulls
                && indexes.equals(((AddFriendCommand) other).indexes)); // state check;
    }
}
