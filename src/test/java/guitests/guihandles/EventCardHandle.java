package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String ORGANISER_FIELD_ID = "#organiser";
    private static final String TIME_FIELD_ID = "#time";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label dateLabel;
    private final Label timeLabel;
    private final Label organiserLabel;
    private final List<Label> tagLabels;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        timeLabel = getChildNode(TIME_FIELD_ID);
        organiserLabel = getChildNode(ORGANISER_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    public String getOrganiser() {
        return organiserLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Event event) {
        return getName().equals(event.getName())
                && getAddress().equals(event.getLocation().value)
                && getOrganiser().equals(event.getOrganiser().getName().fullName)
                && getDate().equals(event.getDateString())
                && getTime().equals(event.getTimeString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(event.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
