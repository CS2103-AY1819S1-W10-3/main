package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_LIMIT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MaxScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * MaxSchedule Command Parser
 */
public class MaxScheduleCommandParser implements Parser<MaxScheduleCommand> {

    private List<String> limitKeyboards;
    private String limit;

    @Override
    public MaxScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index[] index;

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE_LIMIT);

        if (argMultimap.getValue(PREFIX_SCHEDULE_LIMIT).isPresent()) {
            limit = argMultimap.getValue(PREFIX_SCHEDULE_LIMIT).get().trim();
            if (!limit.contains("-")) {
                throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
            }

            String[] limitKeyword = limit.split("-");

            if (limitKeyword.length != 2) {
                throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
            }

            if (limitKeyword[0].length() != 4 || limitKeyword[1].length() != 4) {
                throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
            }

            try {
                int s = Integer.valueOf(limitKeyword[0]);
                int e = Integer.valueOf(limitKeyword[1]);
                if (s > e) {
                    throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE));
                }
            } catch (NumberFormatException e) {
                throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE), e);
            }
        }

        try {
            String[] argsArray = argMultimap.getPreamble().split(" ");
            index = new Index[argsArray.length];
            for (int i = 0; i < index.length; i++) {
                index[i] = Index.fromOneBased(Integer.parseInt(argsArray[i]));
            }

        } catch (Exception pe) {
            pe.printStackTrace();
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE), pe);
        }

        return new MaxScheduleCommand(index, limit);
    }

}
