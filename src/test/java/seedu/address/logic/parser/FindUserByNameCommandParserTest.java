package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.personcommands.FindUserByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindUserByNameCommand object
 */
public class FindUserByNameCommandParserTest implements Parser<FindUserByNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindUserByNameCommand
     * and returns an FindUserByNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindUserByNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindUserByNameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindUserByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
