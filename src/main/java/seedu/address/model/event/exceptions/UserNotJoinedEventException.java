//@@author theJrLinguist
package seedu.address.model.event.exceptions;

/**
 * Signals that the user has not yet joined the event.
 */
public class UserNotJoinedEventException extends RuntimeException {
    public UserNotJoinedEventException() {
        super("User has yet to join the event");
    }
}
