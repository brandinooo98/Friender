package application;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Button;

/**
 *
 *
 *
 */
public class GUI extends Application {
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<String> args;

    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 200;
    private static final String APP_TITLE = "Friender";

    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // save args example
        args = this.getParameters().getRaw();

        // Create a vertical box with Hello labels for each args
        VBox vbox = new VBox();
        for (String arg : args) {
            vbox.getChildren().add(new Label("hello " + arg));
        }
        // Combo box
        String colors[] = {"Red", "Yellow", "Blue"};
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(colors));

        // Image view
        FileInputStream file = new FileInputStream("brandon.png");
        Image image = new Image(file);
        ImageView imageView = new ImageView(image);

        // Button
        Button button = new Button("Done");

        // Hyper Link
        Hyperlink hyperLink = new Hyperlink("Click");
        VBox vBox = new VBox(hyperLink);

        // Main layout is Border Pane example (top,left,center,right,bottom)
        BorderPane root = new BorderPane();

        // Add the vertical box to the center of the root pane
        root.setTop(new Label(APP_TITLE));
        root.setCenter(imageView);
        root.setLeft(comboBox);
        root.setBottom(button);
        root.setRight(vBox);
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * @param user
     */
    public void friendManagement(User user){

    }

    /**
     * @param user
     */
    public void userInformation(User user){

    }

    /**
     * @param user
     */
    public void friendImportExport(User user){

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}