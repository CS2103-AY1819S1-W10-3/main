package seedu.address.logic.commands.eventcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventAttributesPredicate;

public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_multipleKeywords_eventFound() throws ParseException {
        EventAttributesPredicate predicate = makeEventsAttributesPredicate(NAME_DESC_MEETING);
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        String expectedMessage = String.format(FindEventCommand.MESSAGE_SUCCESS, 1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING), model.getFilteredEventList());
    }

    /**
     * Creates an EventAttributesPredicate from the user input.
     */
    public static EventAttributesPredicate makeEventsAttributesPredicate(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT_NAME, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_DATE, PREFIX_TIME_START, PREFIX_ORGANISER_NAME, PREFIX_PARTICIPANT_NAME);

        EventAttributesPredicate predicate = new EventAttributesPredicate();
        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            predicate.setName(ParserUtil.parseGenericString(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicate.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME_START).isPresent()) {
            predicate.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME_START).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            predicate.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ORGANISER_NAME).isPresent()) {
            predicate.setParticipant(ParserUtil.parseName(argMultimap.getValue(PREFIX_ORGANISER_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PARTICIPANT_NAME).isPresent()) {
            predicate.setParticipant(ParserUtil.parseName(argMultimap.getValue(PREFIX_PARTICIPANT_NAME).get()));
        }

        return predicate;
    }
}
