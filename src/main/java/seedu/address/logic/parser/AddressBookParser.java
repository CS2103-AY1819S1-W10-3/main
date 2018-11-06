package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.MaxScheduleCommand;
import seedu.address.logic.commands.eventcommands.AddEventCommand;
import seedu.address.logic.commands.eventcommands.AddPollCommand;
import seedu.address.logic.commands.eventcommands.AddPollOptionCommand;
import seedu.address.logic.commands.eventcommands.AddTimePollCommand;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.DisplayPollCommand;
import seedu.address.logic.commands.eventcommands.FindEventByTimeCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.JoinEventCommand;
import seedu.address.logic.commands.eventcommands.SelectEventCommand;
import seedu.address.logic.commands.eventcommands.SetDateCommand;
import seedu.address.logic.commands.eventcommands.SetTimeCommand;
import seedu.address.logic.commands.eventcommands.VoteCommand;
import seedu.address.logic.commands.personcommands.AddFriendCommand;
import seedu.address.logic.commands.personcommands.AddUserCommand;
import seedu.address.logic.commands.personcommands.ClearUserCommand;
import seedu.address.logic.commands.personcommands.DeleteFriendCommand;
import seedu.address.logic.commands.personcommands.DeleteUserCommand;
import seedu.address.logic.commands.personcommands.EditUserCommand;
import seedu.address.logic.commands.personcommands.FindUserCommand;
import seedu.address.logic.commands.personcommands.ListUserCommand;
import seedu.address.logic.commands.personcommands.SelectUserCommand;
import seedu.address.logic.commands.personcommands.SuggestFriendsByInterestsCommand;
import seedu.address.logic.parser.eventparsers.AddEventCommandParser;
import seedu.address.logic.parser.eventparsers.AddPollCommandParser;
import seedu.address.logic.parser.eventparsers.AddPollOptionCommandParser;
import seedu.address.logic.parser.eventparsers.AddTimePollCommandParser;
import seedu.address.logic.parser.eventparsers.DeleteEventCommandParser;
import seedu.address.logic.parser.eventparsers.DisplayPollCommandParser;
import seedu.address.logic.parser.eventparsers.FindEventByTimeCommandParser;
import seedu.address.logic.parser.eventparsers.FindEventCommandParser;
import seedu.address.logic.parser.eventparsers.JoinEventCommandParser;
import seedu.address.logic.parser.eventparsers.SelectEventCommandParser;
import seedu.address.logic.parser.eventparsers.SetDateCommandParser;
import seedu.address.logic.parser.eventparsers.SetTimeCommandParser;
import seedu.address.logic.parser.eventparsers.VoteCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case AddUserCommand.COMMAND_WORD:
            return new AddUserCommandParser().parse(arguments);

        case AddFriendCommand.COMMAND_WORD:
            return new AddFriendCommandParser().parse(arguments);

        case DeleteFriendCommand.COMMAND_WORD:
            return new DeleteFriendCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case SelectEventCommand.COMMAND_WORD:
            return new SelectEventCommandParser().parse(arguments);

        case SetDateCommand.COMMAND_WORD:
            return new SetDateCommandParser().parse(arguments);

        case SetTimeCommand.COMMAND_WORD:
            return new SetTimeCommandParser().parse(arguments);

        case JoinEventCommand.COMMAND_WORD:
            return new JoinEventCommandParser().parse(arguments);

        case AddPollCommand.COMMAND_WORD:
            return new AddPollCommandParser().parse(arguments);

        case AddTimePollCommand.COMMAND_WORD:
            return new AddTimePollCommandParser().parse(arguments);

        case AddPollOptionCommand.COMMAND_WORD:
            return new AddPollOptionCommandParser().parse(arguments);

        case ClearUserCommand.COMMAND_WORD:
            return new ClearUserCommand();

        case DisplayPollCommand.COMMAND_WORD:
            return new DisplayPollCommandParser().parse(arguments);

        case VoteCommand.COMMAND_WORD:
            return new VoteCommandParser().parse(arguments);

        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);

        case FindEventByTimeCommand.COMMAND_WORD:
            return new FindEventByTimeCommandParser().parse(arguments);

        case EditUserCommand.COMMAND_WORD:
            return new EditUserCommandParser().parse(arguments);

        case SelectUserCommand.COMMAND_WORD:
            return new SelectUserCommandParser().parse(arguments);

        case DeleteUserCommand.COMMAND_WORD:
            return new DeleteUserCommandParser().parse(arguments);

        case FindUserCommand.COMMAND_WORD:
            return new FindUserCommandParser().parse(arguments);

        case SuggestFriendsByInterestsCommand.COMMAND_WORD:
            return new SuggestFriendsByInterestsCommandParser().parse(arguments);

        case ListUserCommand.COMMAND_WORD:
            return new ListUserCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case MaxScheduleCommand.COMMAND_WORD:
            return new MaxScheduleCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
