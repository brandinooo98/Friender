package application;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Button;

/**
 *
 */
public class GUI extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;
    private static final String APP_TITLE = "Friender";
    private SocialNetwork socialNetwork;
    Stage window;

    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // save args example
        window = primaryStage;
        args = this.getParameters().getRaw();
        socialNetwork = new SocialNetwork();

        // Combo box
        String colors[] = {"Red", "Yellow", "Blue"};
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(colors));

        // Button
        Button button = new Button("Done");

        // Hyper Link
        Hyperlink hyperLink = new Hyperlink("Click");
        VBox vBox = new VBox(hyperLink);

        // Main layout is Border Pane example (top,left,center,right,bottom)
        BorderPane root = new BorderPane();

        // Add the vertical box to the center of the root pane
        root.setTop(new Label(APP_TITLE));
        root.setLeft(comboBox);
        root.setBottom(button);
        root.setRight(vBox);
        Scene mainScene = new Scene(root, 0, 0);

        // Makes window size of screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX());
        window.setY(primaryScreenBounds.getMinY());
        window.setWidth(primaryScreenBounds.getWidth());
        window.setHeight(primaryScreenBounds.getHeight());

        // Add the stuff and set the primary stage
        window.setTitle(APP_TITLE);
        window.setScene(mainScene);
        window.show();
    }

    /**
     * @param user
     */
    public void friendManagement(User user){
        Scene friendScene = new Scene();

        // Makes window size of screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX());
        window.setY(primaryScreenBounds.getMinY());
        window.setWidth(primaryScreenBounds.getWidth());
        window.setHeight(primaryScreenBounds.getHeight());

        // Puts scene in window
        window.setTitle(APP_TITLE);
        window.setScene(friendScene);
        window.show();
    }

    /**
     * @param user
     */
    public void userInformation(User user){
        Scene userScene = new Scene();

        // Makes window size of screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX());
        window.setY(primaryScreenBounds.getMinY());
        window.setWidth(primaryScreenBounds.getWidth());
        window.setHeight(primaryScreenBounds.getHeight());

        // Puts scene in window
        window.setTitle(APP_TITLE);
        window.setScene(userScene);
        window.show();
    }

    /**
     * @param user
     */
    public void friendImportExport(User user){
        Scene commandScene = new Scene();

        // Makes window size of screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX());
        window.setY(primaryScreenBounds.getMinY());
        window.setWidth(primaryScreenBounds.getWidth());
        window.setHeight(primaryScreenBounds.getHeight());

        // Puts scene in window
        window.setTitle(APP_TITLE);
        window.setScene(commandScene);
        window.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}