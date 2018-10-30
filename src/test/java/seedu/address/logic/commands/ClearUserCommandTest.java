package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.personcommands.ClearUserCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearUserCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        model.setClearEnabled();
        Model expectedModel = new ModelManager();
        expectedModel.setClearEnabled();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearUserCommand(), model, commandHistory,
                ClearUserCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setClearEnabled();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setClearEnabled();
        expectedModel.resetData(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearUserCommand(), model, commandHistory,
                ClearUserCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
