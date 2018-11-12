//@@author theJrLinguist
package seedu.address.model.event.polls;

import java.util.HashMap;

import seedu.address.model.person.UniquePersonList;

/**
 * Represents a generic poll associated with an event.
 */
public class Poll extends AbstractPoll {
    /**
     * Creates a new Poll object with no existing poll data.
     */
    public Poll(int id, String pollName) {
        super(id, pollName, new HashMap<>());
    }

    /**
     * Creates a new poll object with the poll data.
     */
    public Poll(int id, String pollName, HashMap<String, UniquePersonList> pollData) {
        super(id, pollName, pollData);
    }

    /**
     * Adds an option into the poll
     * @param option The string representing the option to be added
     */
    public void addOption(String option) {
        UniquePersonList personList = new UniquePersonList();
        pollData.put(option, personList);
    }

    /**
     * Returns a copy of the poll.
     */
    @Override
    public Poll copy() {
        Poll copy = new Poll(id, pollName);
        copy.pollData = copyData();
        return copy;
    }
}
