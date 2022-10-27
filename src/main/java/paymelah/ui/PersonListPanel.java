package paymelah.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import paymelah.commons.core.LogsCenter;
import paymelah.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private Accordion personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        List<TitledPane> titledPanes = new ArrayList<TitledPane>();
        personList.addListener((ListChangeListener<Person>) c -> {
            personListView.getPanes().clear();
            titledPanes.clear();
            for (Person person : c.getList()) {
                titledPanes.add(createTitledPane(person, c.getList()));
            }
            personListView.getPanes().addAll(titledPanes);
        });
        for (Person person : personList) {
            titledPanes.add(createTitledPane(person, personList));
        }
        personListView.getPanes().addAll(titledPanes);
    }

    // private void resetIndices(List<TitledPane> titledPanes) {        
    //     for (int i = 1; i <= titledPanes.size(); ++i) {
    //         Label label = (Label) ((HBox) titledPanes.get(i - 1).getGraphic())
    //                 .getChildren().get(0);
    //         String labelText = label.getText();
    //         label.setText(i + labelText.substring(labelText.indexOf('.')));
    //     }
    // }

    private TitledPane createTitledPane(Person person, ObservableList<? extends Person> observableList) {
        int oneBasedIndex = observableList.indexOf(person) + 1;
        if (oneBasedIndex == 0) {
            oneBasedIndex = observableList.size();
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setAlignment(Pos.CENTER);

        // Create HBox to hold our 2 labels
        HBox contentPane = new HBox();
        contentPane.setAlignment(Pos.CENTER);

        // Set padding on the left to avoid overlapping TitledPane's expand arrow
        contentPane.setPadding(new Insets(0, 10, 0, 25));

        // Now, since the TitledPane's graphic node generally has a fixed size, we need to bind our
        // contentPane's width to match the width of the TitledPane. This will account for resizing as well
        contentPane.minWidthProperty().bind(titledPane.widthProperty());

        // Create a Region to act as a separator for the 2 labels
        HBox region = new HBox();
        region.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(region, Priority.ALWAYS);
        
        Label name = new Label(oneBasedIndex + ". " + person.getName().fullName);
        name.setMaxWidth(250);

        Label totalAmount = new Label("Total: $" + person.getDebtsAmountAsMoney().toString());
        totalAmount.setMaxWidth(100);

        // Add our nodes to the contentPane
        contentPane.getChildren().addAll(
            name,
            region,
            totalAmount
        );

        // Add the contentPane as the graphic for the TitledPane
        titledPane.setGraphic(contentPane);
        titledPane.setContent(new PersonCard(person).personCardPane);
        return titledPane;
    }
}
