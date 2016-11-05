package seedu.cmdo.ui;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.cmdo.commons.core.LogsCenter;

//@@author A0141006B
/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    public static final String HELP_WINDOW_ROOT_FIELD_ID = "helpWindow";

    
    private AnchorPane mainPane;
    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }    
    
    private void configure(){
        Scene scene = new Scene(mainPane);
        dialogStage = createDialogStage(TITLE, null, scene);
        setIcon(dialogStage, ICON);
        mainPane.getChildren().add(new HelpCard().getHelpCardPane());
        mainPane.setId(HELP_WINDOW_ROOT_FIELD_ID);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
