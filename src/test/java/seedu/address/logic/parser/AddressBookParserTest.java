package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTEREST_DESC_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.eventcommands.AddPollCommand;
import seedu.address.logic.commands.eventcommands.AddPollOptionCommand;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.DisplayPollCommand;
import seedu.address.logic.commands.eventcommands.FindEventByTimeCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommandTest;
import seedu.address.logic.commands.eventcommands.JoinEventCommand;
import seedu.address.logic.commands.eventcommands.SelectEventCommand;
import seedu.address.logic.commands.eventcommands.SetDateCommand;
import seedu.address.logic.commands.eventcommands.SetTimeCommand;
import seedu.address.logic.commands.eventcommands.VoteCommand;
import seedu.address.logic.commands.personcommands.AddFriendCommand;
import seedu.address.logic.commands.personcommands.AddUserCommand;
import seedu.address.logic.commands.personcommands.DeleteFriendCommand;
import seedu.address.logic.commands.personcommands.DeleteUserCommand;
import seedu.address.logic.commands.personcommands.EditUserCommand;
import seedu.address.logic.commands.personcommands.EditUserCommand.EditPersonDescriptor;
import seedu.address.logic.commands.personcommands.FindUserCommand;
import seedu.address.logic.commands.personcommands.ListUserCommand;
import seedu.address.logic.commands.personcommands.SelectUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAttributesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.StubUserBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_login() throws Exception {
        Person person = new StubUserBuilder().build();
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
    public void parseCommand_findEventByTime() throws Exception {
        FindEventByTimeCommand command = (FindEventByTimeCommand) parser.parseCommand(
                FindEventByTimeCommand.COMMAND_WORD + " " + "d/12-12-2018 t1/12:30 t2/13:30");
        LocalDate date = LocalDate.of(2018, 12, 12);
        LocalTime startTime = LocalTime.of(12, 30);
        LocalTime endTime = LocalTime.of(13, 30);
        assertEquals(new FindEventByTimeCommand(date, startTime, endTime), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        String userInput = FindEventCommand.COMMAND_WORD + " " + NAME_DESC_MEETING + ADDRESS_DESC_BOB;
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                userInput);
        EventAttributesPredicate predicate = FindEventCommandTest.makeEventsAttributesPredicate(userInput);
        assertEquals(new FindEventCommand(predicate), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddUserCommand command = (AddUserCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddUserCommand(person), command);
    }

    @Test
    public void parseCommandAddFriend() throws Exception {
        AddFriendCommand command = (AddFriendCommand) parser.parseCommand(
                AddFriendCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + StringUtil.COMMA + INDEX_SECOND.getOneBased());
        assertEquals(new AddFriendCommand(Index.fromOneBased(INDEX_FIRST.getOneBased(),
                INDEX_SECOND.getOneBased())), command);
    }

    @Test
    public void parseCommandDeleteFriend() throws Exception {
        DeleteFriendCommand command = (DeleteFriendCommand) parser.parseCommand(
                DeleteFriendCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + StringUtil.COMMA + INDEX_SECOND.getOneBased());
        assertEquals(new DeleteFriendCommand(Index.fromOneBased(INDEX_FIRST.getOneBased(),
                INDEX_SECOND.getOneBased())), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteUserCommand command = (DeleteUserCommand) parser.parseCommand(
                DeleteUserCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteUserCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditUserCommand command = (EditUserCommand) parser.parseCommand(EditUserCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditUserCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findUser() throws Exception {
        List<String> nameKeywordList = Arrays.asList(VALID_NAME_BOB.split(" "));
        List<String> phoneKeywordList = Collections.singletonList(VALID_PHONE_BOB);
        List<String> addressKeywordList = Arrays.asList(VALID_ADDRESS_BOB.split(" "));
        List<String> emailKeywordList = Collections.singletonList(VALID_EMAIL_BOB);
        List<String> interestsKeywordList = Collections.singletonList(VALID_INTEREST_STUDY);
        List<String> tagsKeywordList = Collections.singletonList(VALID_TAG_FRIEND);
        UserContainsKeywordsPredicate predicate =
                new UserContainsKeywordsPredicate(nameKeywordList, phoneKeywordList, addressKeywordList,
                        emailKeywordList, interestsKeywordList, tagsKeywordList);

        FindUserCommand command = (FindUserCommand) parser.parseCommand(
                FindUserCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INTEREST_DESC_STUDY + TAG_DESC_FRIEND);
        assertEquals(new FindUserCommand(predicate), command);
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
        assertTrue(parser.parseCommand(ListUserCommand.COMMAND_WORD) instanceof ListUserCommand);
        assertTrue(parser.parseCommand(ListUserCommand.COMMAND_WORD + " 3") instanceof ListUserCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectUserCommand command = (SelectUserCommand) parser.parseCommand(
                SelectUserCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectUserCommand(INDEX_FIRST), command);
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
