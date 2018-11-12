//@@author theJrLinguist
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayPollEvent;

/**
 * Panel which displays the event and poll details.
 */
public class EventDetailsPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "EventDetailsPanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea eventDetails;

    public EventDetailsPanel() {
        super(FXML);
        eventDetails.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(DisplayPollEvent event) {
        if (event.message == null) {
            displayed.set("");
        } else {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            Platform.runLater(() -> displayed.setValue(event.message));
        }

    }
}
