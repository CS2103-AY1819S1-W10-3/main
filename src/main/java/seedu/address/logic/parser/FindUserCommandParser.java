package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.personcommands.FindUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.UserContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindUserCommand object
 */
public class FindUserCommandParser implements Parser<FindUserCommand> {

    private List<String> nameKeywords;
    private List<String> phoneKeywords;
    private List<String> emailKeywords;
    private List<String> addressKeywords;
    private List<String> interestKeywords;
    private List<String> tagKeywords;

    /**
     * Parses the given {@code String} of arguments in the context of the FindUserCommand
     * and returns an FindUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_INTEREST, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindUserCommand.MESSAGE_USAGE));
        }

        nameKeywords = (argMultimap.getValue(PREFIX_NAME).isPresent())
                ? parseKeywords(argMultimap, PREFIX_NAME) : null;

        phoneKeywords = (argMultimap.getValue(PREFIX_PHONE).isPresent())
                ? parseKeywords(argMultimap, PREFIX_PHONE) : null;

        emailKeywords = (argMultimap.getValue(PREFIX_EMAIL).isPresent())
                ? parseKeywords(argMultimap, PREFIX_EMAIL) : null;

        addressKeywords = (argMultimap.getValue(PREFIX_ADDRESS).isPresent())
                ? parseKeywords(argMultimap, PREFIX_ADDRESS) : null;

        interestKeywords = (argMultimap.getValue(PREFIX_INTEREST).isPresent())
                ? parseKeywords(argMultimap, PREFIX_INTEREST) : null;

        tagKeywords = (argMultimap.getValue(PREFIX_TAG).isPresent())
                ? parseKeywords(argMultimap, PREFIX_TAG) : null;

        if (nameKeywords == null
                && phoneKeywords == null
                && emailKeywords == null
                && addressKeywords == null
                && interestKeywords == null
                && tagKeywords == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindUserCommand.MESSAGE_USAGE));
        }

        return new FindUserCommand(
                new UserContainsKeywordsPredicate(nameKeywords,
                        phoneKeywords,
                        addressKeywords,
                        emailKeywords,
                        interestKeywords,
                        tagKeywords));
    }

    /**
     * Parses the given keywords and returns the keywords that is stored in a list of Strings
     */
    private List<String> parseKeywords(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        String keywords = argMultimap.getValue(prefix).get().trim();
        if (keywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindUserCommand.MESSAGE_USAGE));
        }
        return Arrays.asList(keywords.split("\\s+"));
    }

}
