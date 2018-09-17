package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;


/** Adds a person to the address book. */
public class AddEventCommand extends Command {

  public static final String COMMAND_WORD = "addEvent";

  public static final String MESSAGE_USAGE = COMMAND_WORD;
  public static final String MESSAGE_SUCCESS = "New event added: %1$s";
  public static final String MESSAGE_DUPLICATE_PERSON =
      "This event already exists in the address book";

  public final Event toAdd;

  /* Creates an AddCommand to add the specified {@code Event}. */
  public AddEventCommand(Event event) {
    requireNonNull(event);
    toAdd = event;
  }

  @Override
  public CommandResult execute(Model model, CommandHistory history) throws CommandException {
    requireNonNull(model);

    if (model.hasEvent(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_PERSON);
    }

    model.addEvent(toAdd);
    model.commitAddressBook();
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddEventCommand // instanceof handles nulls
            && toAdd.equals(((AddEventCommand) other).toAdd));
  }
}
