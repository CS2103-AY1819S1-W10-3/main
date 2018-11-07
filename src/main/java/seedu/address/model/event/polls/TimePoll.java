//@@author theJrLinguist
package seedu.address.model.event.polls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a time poll associated with an event.
 */
public class TimePoll extends AbstractPoll {
    private Schedule sharedSchedule;

    /**
     * Constructs a TimePoll from the participant list, and the relevant date range.
     */
    public TimePoll(int id, UniquePersonList participantList, LocalDate startDate, LocalDate endDate) {
        super.id = id;
        super.pollName = "Time Poll";
        super.pollData = new HashMap<>();
        createOptions(participantList, startDate, endDate);
    }

    /**
     * Create a TimePoll without options.
     */
    private TimePoll(int id) {
        super.id = id;
        super.pollName = "Time Poll";
        super.pollData = new HashMap<>();
    }

    /**
     * Creates the poll options.
     */
    private void createOptions(UniquePersonList participantList, LocalDate startDate, LocalDate endDate) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        for (Person person : participantList) {
            Schedule schedule = person.getSchedule();
            schedules.add(schedule);
        }
        sharedSchedule = Schedule.maxSchedule(schedules.toArray(new Schedule[schedules.size()]));

        List<LocalDate> dates = startDate.datesUntil(endDate).collect(Collectors.toList());
        for (LocalDate date : dates) {
            ArrayList<String> options = createOptionsFromDate(date);
            for (String option : options) {
                UniquePersonList voterList = new UniquePersonList();
                pollData.put(option, voterList);
            }
        }
    }

    /**
     * Creates the options given a single date.
     */
    private ArrayList<String> createOptionsFromDate(LocalDate date) {
        Integer dayOfWeek = date.getDayOfWeek().getValue();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = date.format(dateFormat);
        List<String> times = sharedSchedule.getFreeSlotsByDay(dayOfWeek)
                .stream()
                .map(slot -> slot.getTime())
                .collect(Collectors.toList());
        ArrayList<String> options = new ArrayList<>();
        for (String time : times) {
            String option = dateString + " " + time;
            options.add(option);
        }
        return options;
    }

    /**
     * Returns a copy of the TimePoll.
     */
    public TimePoll copy() {
        TimePoll copy = new TimePoll(id);
        copy.pollData = super.copyData();
        return copy;
    }
}
