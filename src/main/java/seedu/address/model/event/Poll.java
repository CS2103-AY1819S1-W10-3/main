package seedu.address.model.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * Represents a poll associated with an event.
 */
public class Poll {
    private int id;
    private String pollName;
    private HashMap<String, LinkedList<Person>> pollData;

    /**
     * Creates a new Poll object
     * @param pollName The name of the poll
     */
    public Poll(int id, String pollName) {
        this.id = id;
        this.pollName = pollName;
        pollData = new HashMap<>();
    }

    /**
     * Creates a new poll object with the poll data.
     */
    public Poll(int id, String pollName, HashMap<String, LinkedList<Person>> pollData) {
        this.id = id;
        this.pollName = pollName;
        this.pollData = pollData;
    }

    public int getId() {
        return id;
    }

    public String getPollName() {
        return pollName;
    }

    public HashMap<String, LinkedList<Person>> getPollData() {
        return pollData;
    }

    /**
     * Add an option into the poll
     * @param option The string representing the option to be added
     */
    public void addOption(String option) {
        LinkedList<Person> personList = new LinkedList<>();
        pollData.put(option, personList);
    }

    /**
     * Adds the vote of a user into an option
     */
    public void addVote(String option, Person person) throws IllegalArgumentException {
        if (!pollData.containsKey(option)) {
            throw new IllegalArgumentException();
        }
        pollData.get(option).add(person);
    }

    /**
     * Retrieves most popular options by number of votes.
     */
    public LinkedList<String> getHighest() {
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
            mostPopularEntries = "Most popular options: " + getHighest().toString();
        }
        String data = displayPollData();
        return title + "\n" + mostPopularEntries + "\n" + data;
    }

    public String displayPollData() {
        HashMap<String, List<String>> displayData = new HashMap<>();
        pollData.forEach((k, v) -> {
                List<String> nameList = v.stream()
                        .map(person -> person.getName().toString())
                        .collect(Collectors.toList());
                displayData.put(k, nameList);
        });
        return displayData.toString();
    }
}
