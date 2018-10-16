package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_UPDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.TimeTableUtil;
import seedu.address.logic.commands.personcommands.EditCommand;
import seedu.address.logic.commands.personcommands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TimeTable;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_INTEREST, PREFIX_TAG, PREFIX_TIMETABLE, PREFIX_SCHEDULE_UPDATE, PREFIX_SCHEDULE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_TIMETABLE).isPresent()) {
            String link = argMultimap.getValue(PREFIX_TIMETABLE).get();
            TimeTable tt = TimeTableUtil.parseUrl(link);
            editPersonDescriptor.setSchedule(ParserUtil.parseSchedule(tt.convertToSchedule().valueToString()));
        }

        if (argMultimap.getValue(PREFIX_SCHEDULE).isPresent()) {
            String scheduleString = argMultimap.getValue(PREFIX_SCHEDULE).get();
            editPersonDescriptor.setSchedule(ParserUtil.parseSchedule(scheduleString));
        }

        if (argMultimap.getValue(PREFIX_SCHEDULE_UPDATE).isPresent()) {
            String link = argMultimap.getValue(PREFIX_SCHEDULE_UPDATE).get();
            Schedule s = new Schedule();
            String[] parms = link.split(" ");
            s.setTimeDay(parms[0].trim(), parms[1].trim(), true);
            editPersonDescriptor.setUpdateSchedule(s);
        }


        // This one is for schedule to schedule
        //editPersonDescriptor.setSchedule(ParserUtil.parseSchedule(argMultimap.getValue(PREFIX_TIMETABLE).get()));

        parseInterestsForEdit(argMultimap.getAllValues(PREFIX_INTEREST)).ifPresent(editPersonDescriptor::setInterests);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> interests} into a {@code Set<Interests>} if {@code interests} is non-empty.
     * If {@code interests} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Interests>} containing zero interests.
     */
    private Optional<Set<Interest>> parseInterestsForEdit(Collection<String> interests) throws ParseException {
        assert interests != null;

        if (interests.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> interestSet = interests.size() == 1
                && interests.contains("") ? Collections.emptySet() : interests;
        return Optional.of(ParserUtil.parseInterests(interestSet));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
