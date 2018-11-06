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

        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(
                MainApp.class.getResourceAsStream(FXML_FILE_FOLDER + PERSON_PAGE));
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            events.filtered((i) -> i.getPersonList().contains(person))
                .stream().map(u -> u.getName()).collect(Collectors.joining(", ")),
            person.getSchedule().prettyPrint(),
        };
        String html = MessageFormat.format(sb.toString(), params);

        Platform.runLater(() -> {
                browser.getEngine().loadContent(html);
            }
        );


        //loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    //    /**
    //     * Load Person into browser panel
    //     *
    //     * @param event
    //     */
    //    private void loadEventPage(seedu.address.model.event.Event event) {
    //
    //        StringBuilder sb = new StringBuilder();
    //        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + EVENT_PAGE);
    //        try {
    //            BufferedInputStream bin = ((BufferedInputStream) defaultPage.getContent());
    //            byte[] contents = new byte[1024];
    //            int bytesRead = 0;
    //            while ((bytesRead = bin.read(contents)) != -1) {
    //                sb.append(new String(contents, 0, bytesRead));
    //            }
    //            bin.close();
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //
    //        // replace the template with person stuff
    //        Object[] params = new Object[] {
    //            event.getName(),
    //            event.getOrganiser().getName(),
    //            event.getStartTime() == null ? "No Start time" : event.getStartTime().toString(),
    //            event.getEndTime() == null ? "No End time" : event.getEndTime().toString(),
    //            event.getDate() == null ? "No date time" : event.getDate().toString()
    //        };
    //        String html = MessageFormat.format(sb.toString(), params);
    //
    //        Platform.runLater(() -> {
    //                browser.getEngine().loadContent(html);
    //            }
    //        );
    //
    //
    //        //loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    //    }


    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bin = new BufferedInputStream(
                MainApp.class.getResourceAsStream(FXML_FILE_FOLDER + DEFAULT_PAGE));
            byte[] contents = new byte[1022];
            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
                browser.getEngine().loadContent(sb.toString());
            }
        );
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    //    @Subscribe
    //    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
    //        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    //        if (event.getNewSelection() == null) {
    //            loadDefaultPage();
    //        } else {
    //            loadPersonPage(event.getNewSelection());
    //        }
    //
    //    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonToEventPopulateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() == null) {
            loadDefaultPage();
        } else {
            loadPersonPage(event.getNewSelection(), event.getEventModel());
        }

    }

    //    @Subscribe
    //    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
    //        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    //        if (event.getNewSelection() == null) {
    //            loadDefaultPage();
    //        } else {
    //            loadEventPage(event.getNewSelection());
    //        }
    //    }
}
