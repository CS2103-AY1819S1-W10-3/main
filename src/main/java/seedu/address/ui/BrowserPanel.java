package seedu.address.ui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonToEventPopulateEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String PERSON_PAGE = "browsePerson.html";
    public static final String EVENT_PAGE = "browseEvent.html";
    public static final String SEARCH_PAGE_URL =
        "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load Person into browser panel
     *
     * @param person
     */
    private void loadPersonPage(Person person, ObservableList<seedu.address.model.event.Event> events) {
        String resourceHtml = readResourceHtml(PERSON_PAGE);

        // replace the template with person stuff
        Object[] params = new Object[] {
            person.getName(),
            person.getPhone(),
            person.getEmail(),
            person.getTags().stream().map(u -> u.tagName).collect(Collectors.joining(", ")),
            person.getAddress(),
            person.getInterests().stream().map(u -> u.interestName).collect(Collectors.joining(", ")),
            person.getFriends().stream()
                .map(u -> u.toString()).collect(Collectors.joining(" ", "<p>", "</p>")),
            events.filtered((i) -> i.getParticipantList().contains(person))
                .stream().map(u -> u.getName().value).collect(Collectors.joining(", ")),
            person.getSchedule().prettyPrint(),
        };
        String html = MessageFormat.format(resourceHtml, params);

        Platform.runLater(() -> {
                browser.getEngine().loadContent(html);
            }
        );
    }

    /**
     * Reads resource html inside the jar
     * @param filename
     * @return
     */
    private String readResourceHtml(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(
                MainApp.class.getResourceAsStream(FXML_FILE_FOLDER + filename));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {

        String resourceHtml = readResourceHtml(DEFAULT_PAGE);

        Platform.runLater(() -> {
                browser.getEngine().loadContent(resourceHtml);
            }
        );
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }


    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonToEventPopulateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() == null) {
            loadDefaultPage();
        } else {
            loadPersonPage(event.getNewSelection(), event.getEventModel());
        }

    }

}
