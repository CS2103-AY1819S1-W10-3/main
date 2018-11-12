package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Time;


public class MaxScheduleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MaxScheduleCommand(null, null);
    }


    @Test
    public void executeValidIndexUnfilteredList_success() throws ParseException {
        Person personToFind1 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person personToFind2 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());

        Index[] index = {
            INDEX_FIRST, INDEX_SECOND
        };
        MaxScheduleCommand maxScheduleCommand = new MaxScheduleCommand(index, null);

        Schedule newSchedule = personToFind1.getSchedule().maxSchedule(personToFind2.getSchedule());

        String expectedMessage = String.format(MaxScheduleCommand.MESSAGE_SUCCESS, newSchedule.freeTimeToString());

        // no change in model
        assertCommandSuccess(maxScheduleCommand, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void executeInvalidIndexUnfilteredList_success() throws ParseException {

        Index invalidIndex = Index.fromZeroBased(20);
        Index[] index = {INDEX_FIRST, INDEX_SECOND, invalidIndex};
        MaxScheduleCommand maxScheduleCommand = new MaxScheduleCommand(index, null);

        String expectedMessage = String.format(MaxScheduleCommand.MESSAGE_PERSON_DOES_NOT_EXIST);

        // no change in model
        assertCommandFailure(maxScheduleCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void executeWithLimits() throws ParseException {

        Person personToFind1 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person personToFind2 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());

        Index[] index = {
            INDEX_FIRST, INDEX_SECOND
        };
        MaxScheduleCommand maxScheduleCommand = new MaxScheduleCommand(index, "0800-0900");

        Schedule newSchedule = personToFind1.getSchedule().maxSchedule(personToFind2.getSchedule());

        Time time1 = new Time("0800");
        Time time2 = new Time("0900");
        String expectedMessage =
            String.format(MaxScheduleCommand.MESSAGE_SUCCESS, newSchedule
                .freeTimeToStringByTime(time1, time2));

        // no change in model
        assertCommandSuccess(maxScheduleCommand, model, commandHistory, expectedMessage, model);
    }
}
