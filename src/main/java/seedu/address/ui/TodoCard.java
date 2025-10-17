package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.todo.Todo;

/**
 * An UI component that displays information of a {@code Todo}.
 */
public class TodoCard extends UiPart<Region> {

    private static final String FXML = "TodoListCard.fxml";

    public final Todo todo;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label todoName;
    @FXML
    private Label description;
    @FXML
    private Label contactName;
    @FXML
    private Label status;

    /**
     * Creates a {@code TodoCard} with the given {@code Todo} and index to display.
     */
    public TodoCard(Todo todo, int displayedIndex) {
        super(FXML);
        this.todo = todo;
        id.setText(displayedIndex + ". ");
        todoName.setText(todo.getTodoName().toString());
        description.setText("Description: " + todo.getDescription());
        // Set contact tag
        if (todo.getContactName() != null) {
            contactName.setText(todo.getContactName().fullName);
            contactName.getStyleClass().add("contact-tag");
        } else {
            contactName.setText("No contact linked");
            contactName.getStyleClass().add("contact-tag");
            contactName.getStyleClass().add("no-contact");
        }
        // Set status checkbox
        if (todo.getIsCompleted()) {
            status.setText("☑ Completed"); // Checked box with X
            status.getStyleClass().add("status-checkbox");
        } else {
            status.setText("☐ In Progress"); // Empty box
            status.getStyleClass().add("status-checkbox");
            status.getStyleClass().add("incomplete");
        }
    }
}
