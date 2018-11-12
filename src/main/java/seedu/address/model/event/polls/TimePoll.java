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
    private static final String TIMEPOLL_NAME = "Time Poll";
    private Schedule sharedSchedule;

    /**
     * Constructs a TimePoll from the participant list, and the relevant date range.
     */
    public TimePoll(int id, UniquePersonList participantList, LocalDate startDate, LocalDate endDate) {
        super(id, TIMEPOLL_NAME, new HashMap<>());
        createSharedSchedule(participantList);
        createOptions(startDate, endDate);
    }

    /**
     * Create a TimePoll without options.
     */
    private TimePoll(int id) {
        super(id, TIMEPOLL_NAME, new HashMap<>());
    }

    /**
     * Creates the poll options based on the schedules of the participants.
     */
    private void createOptions(LocalDate startDate, LocalDate endDate) {
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
     * Creates the shared schedule based on the schedules of all the event participants.
     */
    private void createSharedSchedule(UniquePersonList participantList) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        for (Person person : participantList) {
            Schedule schedule = person.getSchedule();
            schedules.add(schedule);
        }
        sharedSchedule = Schedule.maxSchedule(schedules.toArray(new Schedule[schedules.size()]));
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
                .map(slot -> slot.getTime().getStringRepresentation())
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
    @Override
    public TimePoll copy() {
        TimePoll copy = new TimePoll(id);
        copy.pollData = copyData();
        return copy;
    }
}
