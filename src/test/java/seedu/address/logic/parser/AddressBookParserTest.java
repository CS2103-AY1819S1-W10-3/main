package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddUserCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.eventcommands.AddPollCommand;
import seedu.address.logic.commands.eventcommands.AddPollOptionCommand;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.DisplayPollCommand;
import seedu.address.logic.commands.eventcommands.JoinEventCommand;
import seedu.address.logic.commands.eventcommands.SelectEventCommand;
import seedu.address.logic.commands.eventcommands.SetDateCommand;
import seedu.address.logic.commands.eventcommands.SetTimeCommand;
import seedu.address.logic.commands.eventcommands.VoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_login() throws Exception {
        Person person = new PersonBuilder().build();
        LoginCommand command = (LoginCommand) parser.parseCommand(PersonUtil.getLoginCommand(person));
        assertEquals(new LoginCommand(person), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_addPoll() throws Exception {
        AddPollCommand command = (AddPollCommand) parser.parseCommand(
                AddPollCommand.COMMAND_WORD + " " + "n/Date poll");
        assertEquals(new AddPollCommand("Date poll"), command);
    }

    @Test
    public void parseCommand_addPollOption() throws Exception {
        AddPollOptionCommand command = (AddPollOptionCommand) parser.parseCommand(
                AddPollOptionCommand.COMMAND_WORD + " " + "i/1 o/12 August");
        assertEquals(new AddPollOptionCommand(INDEX_FIRST, "12 August"), command);
    }

    @Test
    public void parseCommand_joinEvent() throws Exception {
        JoinEventCommand command = (JoinEventCommand) parser.parseCommand(
                JoinEventCommand.COMMAND_WORD + " " + "1");
        assertEquals(new JoinEventCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectEvent() throws Exception {
        SelectEventCommand command = (SelectEventCommand) parser.parseCommand(
                SelectEventCommand.COMMAND_WORD + " " + "1");
        assertEquals(new SelectEventCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_setDate() throws Exception {
        SetDateCommand command = (SetDateCommand) parser.parseCommand(
                SetDateCommand.COMMAND_WORD + " " + "d/02-03-2018");
        LocalDate date = LocalDate.of(2018, 3, 2);
        assertEquals(new SetDateCommand(date), command);
    }

    @Test
    public void parseCommand_setTime() throws Exception {
        SetTimeCommand command = (SetTimeCommand) parser.parseCommand(
                SetTimeCommand.COMMAND_WORD + " " + "t1/12:00 t2/13:30");
        LocalTime startTime = LocalTime.of(12, 00);
        LocalTime endTime = LocalTime.of(13, 30);
        assertEquals(new SetTimeCommand(startTime, endTime), command);
    }

    @Test
    public void parseCommand_vote() throws Exception {
        VoteCommand command = (VoteCommand) parser.parseCommand(
                VoteCommand.COMMAND_WORD + " " + "i/1 o/12 August");
        assertEquals(new VoteCommand(INDEX_FIRST, "12 August"), command);
    }

    @Test
    public void parseCommand_joinOption() throws Exception {
        DisplayPollCommand command = (DisplayPollCommand) parser.parseCommand(
                DisplayPollCommand.COMMAND_WORD + " " + "1");
        assertEquals(new DisplayPollCommand(INDEX_FIRST), command);
    }


    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddUserCommand command = (AddUserCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddUserCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
