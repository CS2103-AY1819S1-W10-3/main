//@@author theJrLinguist
package seedu.address.model.event.polls;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * An abstract poll class.
 */
public class AbstractPoll {
    private static final Logger logger = LogsCenter.getLogger(AbstractPoll.class);
    protected int id;
    protected String pollName;
    protected HashMap<String, UniquePersonList> pollData;

    public int getId() {
        return id;
    }

    public String getPollName() {
        return pollName;
    }

    public HashMap<String, UniquePersonList> getPollData() {
        return pollData;
    }

    /**
     * Adds the vote of a user into an option
     */
    public void addVote(String option, Person person) throws IllegalArgumentException, DuplicatePersonException {
        if (!pollData.containsKey(option)) {
            throw new IllegalArgumentException();
        }
        pollData.get(option).add(person);
    }

    /**
     * Updates the person in the poll votes.
     */
    public void updatePerson(Person target, Person editedPerson) {
        for (Map.Entry<String, UniquePersonList> entry : pollData.entrySet()) {
            if (entry.getValue().contains(target)) {
                try {
                    entry.getValue().setPerson(target, editedPerson);
                } catch (PersonNotFoundException e) {
                    logger.info("Person is not in voter list.");
                } catch (DuplicatePersonException e) {
                    logger.info("The same person is already in the voter list.");
                }
            }
        }
    }

    /**
     * Retrieves most popular options by number of votes.
     */
    public LinkedList<String> getPopularOptions() {
        TreeMap<Integer, LinkedList<String>> frequency = new TreeMap<>();
        pollData.forEach((k, v) -> {
            if (!frequency.containsKey(v.size())) {
                LinkedList<String> options = new LinkedList<>();
                options.add(k);
                frequency.put(v.size(), options);
            } else {
                frequency.get(v.size()).add(k);
            }
        });
        return frequency.lastEntry().getValue();
    }

    /**
     * Returns a string representation of the poll
     */
    public String displayPoll() {
        String title = String.format("Poll %1$s: %2$s", Integer.toString(id), pollName);
        String mostPopularEntries = "";
        if (!pollData.isEmpty()) {
            mostPopularEntries = "Most popular options:\n" + getPopularOptions().toString();
        }
        String data = displayPollData();
        return title + "\n" + mostPopularEntries + "\n\n" + data;
    }

    /**
     * Returns the poll data as a string identifying people by their names.
     */
    public String displayPollData() {
        HashMap<String, List<String>> displayData = new HashMap<>();
        pollData.forEach((k, v) -> {
            List<String> nameList = v.asUnmodifiableObservableList()
                    .stream()
                    .map(person -> person.getName().toString())
                    .collect(Collectors.toList());
            displayData.put(k, nameList);
        });
        String result = "";
        for (Map.Entry<String, List<String>> entry : displayData.entrySet()) {
            result += entry.getKey() + ":\n" + entry.getValue().toString() + "\n";
        }
        return result;
    }

    /**
     * Returns a copy of the poll data.
     */
    public HashMap<String, UniquePersonList> copyData() {
        HashMap<String, UniquePersonList> dataCopy = new HashMap<>();
        for (Map.Entry<String, UniquePersonList> entry : pollData.entrySet()) {
            UniquePersonList newPersonList = new UniquePersonList();
            for (Person person : entry.getValue()) {
                newPersonList.add(person);
            }
            dataCopy.put(entry.getKey(), newPersonList);
        }
        return dataCopy;
    }

    /**
     * Deletes a person from the voter lists in the poll data.
     */
    public void deletePerson(Person target) {
        for (Map.Entry<String, UniquePersonList> entry : pollData.entrySet()) {
            if (entry.getValue().contains(target)) {
                entry.getValue().remove(target);
            }
        }
    }

    /**
     * Returns true if both polls have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AbstractPoll)) {
            return false;
        }

        AbstractPoll otherPoll = (AbstractPoll) other;
        return otherPoll.getId() == getId()
                && otherPoll.getPollData().equals(getPollData())
                && otherPoll.getPollName().equals(getPollName());
    }
}
