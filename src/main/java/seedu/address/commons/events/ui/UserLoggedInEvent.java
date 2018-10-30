package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class UserLoggedInEvent extends BaseEvent {

    public final String message;

    public UserLoggedInEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
