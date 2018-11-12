package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Time;

/**
 * Max Schedule Command where two or more persons' schedule are ORed together to get the maximum subset.
 *
 * @author adjscent
 */
public class MaxScheduleCommand extends Command {

    public static final String COMMAND_WORD = "maxSchedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find free time between several users. "
        + "Parameters: "
        + "1 2 3 4 5 ... [/sl 0800-0900]";
    public static final String MESSAGE_SUCCESS = "Free time found: %1$s!";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "This person does not exist in the address book";

    private static final Logger logger = LogsCenter.getLogger(MaxScheduleCommand.class);

    private Index[] indexs;
    private Schedule schedule;
    private String limit;

    /**
     * Creates an LoginCommand to log in the specified {@code CurrentUser}
     */
    public MaxScheduleCommand(Index[] indexs, String limit) {
        requireNonNull(indexs);
        this.indexs = indexs;
        this.limit = limit;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        schedule = new Schedule();
        ArrayList<Person> persons = new ArrayList<>();
        String text = "";

        try {
            for (Index index : indexs) {
                Person person = model.getPerson(index);
                persons.add(person);
                schedule = Schedule.maxSchedule(schedule, person.getSchedule());
                if (limit != null) {
                    String[] limitRange = limit.split("-");
                    Time startTime = new Time(limitRange[0]);
                    Time endTime = new Time(limitRange[1]);
                    text = schedule.freeTimeToStringByTime(startTime, endTime);
                } else {
                    text = schedule.freeTimeToString();
                }

            }
        } catch (Exception e) {
            logger.warning("MaxSchedule has compared an invalid index");
            throw new CommandException(MESSAGE_PERSON_DOES_NOT_EXIST);
        }

        logger.info("MaxSchedule has compared " + indexs.length + " schedules.");

        return new CommandResult(String.format(MESSAGE_SUCCESS, text));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MaxScheduleCommand)
            && Arrays.deepEquals(indexs, ((MaxScheduleCommand) other).indexs); // instanceof handles nulls
    }
}
