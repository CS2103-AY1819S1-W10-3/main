package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid Password
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("^")); // only non-alphanumeric characters
        assertFalse(Password.isValidPassword("peter*")); // contains non-alphanumeric characters

        // valid Password
        assertTrue(Password.isValidPassword("password")); // alphabets only
        assertTrue(Password.isValidPassword("12345")); // numbers only
        assertTrue(Password.isValidPassword("password 123")); // alphanumeric characters
        assertTrue(Password.isValidPassword("Password 123")); // with capital letters
        assertTrue(Password.isValidPassword("Password1 Password2 Password3")); // long Passwords
    }
}
