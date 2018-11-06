package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MaxScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * MaxSchedule Command Parser
 */
public class MaxScheduleCommandParser implements Parser<MaxScheduleCommand> {

    @Override
    public MaxScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index[] index;

        try {
            String[] argsArray = args.trim().split(" ");
            index = new Index[argsArray.length];
            for (int i = 0; i < index.length; i++) {
                index[i] = Index.fromOneBased(Integer.parseInt(argsArray[i]));
            }

        } catch (Exception pe) {
            pe.printStackTrace();
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MaxScheduleCommand.MESSAGE_USAGE), pe);
        }

        return new MaxScheduleCommand(index);
    }
}
