//@@author theJrLinguist
package seedu.address.model.event.exceptions;

/**
 * Signals that the current user is not the event organiser and hence is not permitted to edit the event.
 */
public class NotEventOrganiserException extends RuntimeException {
    public NotEventOrganiserException() {
        super("The current user is not the event organiser and may not edit the event");
    }
}
