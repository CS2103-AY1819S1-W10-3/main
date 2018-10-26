package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.personcommands.FindUserByPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindUserCommand object
 */
public class FindUserByPhoneCommandParser implements Parser<FindUserByPhoneCommand> {

    /**
     * Parses the given {@code Phone number} in the context of the FindUserByPhoneCommand
     * and returns an FindUserByPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindUserByPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindUserByPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneKeywords = trimmedArgs.split("\\s+");

        return new FindUserByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
    }

}
