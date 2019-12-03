package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;

/**
 *
 */
public class GUI extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private static final String APP_TITLE = "Friender";
	private SocialNetwork socialNetwork;
	private Stage window;
	private User centralUser;
	private HBox root;
	private VBox infoVBox;
	private VBox contentBox;
	private HBox navBar;
	private Rectangle2D primaryScreenBounds;

	/**
	 * @param primaryStage
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		// General instantiations
		root = new HBox();
		infoVBox = new VBox();
		window = primaryStage;
		socialNetwork = new SocialNetwork();

		// Graph visualization
		Canvas graph = new Canvas();
		graph.setWidth(primaryScreenBounds.getWidth() * 0.75);
		graph.setHeight(primaryScreenBounds.getHeight());
		GraphicsContext gc = graph.getGraphicsContext2D();

		// NAV BAR AND CANVAS
		contentBox = new VBox();

		navBar = new HBox();
		navBar.setMinWidth(primaryScreenBounds.getWidth() * 0.75);
		navBar.setMinHeight(50);

		// BUTTONS
		Button view = new Button("View");
		view.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		view.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
		view.setOnAction(e -> graphVisual());
		view.setMinWidth(navBar.getMinWidth() * 0.25);
		view.setMinHeight(navBar.getMinHeight());
		Button edit = new Button("Edit");
		edit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		edit.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
		edit.setOnAction(e -> friendManagement());
		edit.setMinWidth(navBar.getMinWidth() * 0.25);
		edit.setMinHeight(navBar.getMinHeight());
		Button friends = new Button("Friends");
		friends.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		friends.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
		friends.setOnAction(e -> userInformation());
		friends.setMinWidth(navBar.getMinWidth() * 0.25);
		friends.setMinHeight(navBar.getMinHeight());
		Button im = new Button("Import");
		im.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		im.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
		im.setOnAction(e -> friendImportExport());
		im.setMinWidth(navBar.getMinWidth() * 0.25);
		im.setMinHeight(navBar.getMinHeight());
		Button[] buttons = { view, edit, friends, im };

		navBar.getChildren().addAll(buttons);
		contentBox.getChildren().addAll(navBar, graph);

		// ERROR BOX AND STATUS BOX
		infoVBox.setMinHeight(primaryScreenBounds.getHeight());

		Text box1 = new Text(10, 50, "Status text");
		Text box2 = new Text(10, 50, "Error text");
		Label status = new Label("Status");
		Label error = new Label("Error");

		VBox statusBox = new VBox();
		statusBox.setMinHeight(infoVBox.getMinHeight() * 0.5);

		VBox errorBox = new VBox();
		errorBox.setMinHeight(infoVBox.getMinHeight() * 0.5);

		statusBox.getChildren().addAll(status, box1);
		statusBox.setStyle("-fx-background-color: #336699;");
		errorBox.getChildren().addAll(error, box2);
		errorBox.setStyle("-fx-background-color: #123456;");

		infoVBox.getChildren().addAll(statusBox, errorBox);
		infoVBox.setMinWidth(primaryScreenBounds.getWidth() * 0.25);

		root.getChildren().addAll(contentBox, infoVBox);

		Scene mainScene = new Scene(root);

		// Makes window size of screen
		window.setX(primaryScreenBounds.getMinX());
		window.setY(primaryScreenBounds.getMinY());
		window.setWidth(primaryScreenBounds.getWidth());
		window.setHeight(primaryScreenBounds.getHeight());

		// Add the stuff and set the primary stage
		window.setTitle(APP_TITLE);
		window.setScene(mainScene);
		window.show();
	}

	public void graphVisual(){
		// Initializes layout
		root = new HBox();
		contentBox = new VBox();

		// Graph visualization
		Canvas graph = new Canvas();
		graph.setWidth(primaryScreenBounds.getWidth() * 0.75);
		graph.setHeight(primaryScreenBounds.getHeight());
		GraphicsContext gc = graph.getGraphicsContext2D();

		// Adds content to scene
		contentBox.getChildren().addAll(navBar, graph);
		root.getChildren().addAll(contentBox, infoVBox);
		Scene graphScene = new Scene(root);

		// Makes window size of screen
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		window.setX(primaryScreenBounds.getMinX());
		window.setY(primaryScreenBounds.getMinY());
		window.setWidth(primaryScreenBounds.getWidth());
		window.setHeight(primaryScreenBounds.getHeight());

		// Puts scene in window
		window.setTitle(APP_TITLE);
		window.setScene(graphScene);
		window.show();
	}

	/**
	 */
	public void friendManagement() {
		// Initializes layout
		root = new HBox();
		contentBox = new VBox();
		VBox content = new VBox();
		HBox add = new HBox();
		HBox remove = new HBox();

		// Add area creation
		Label addLabel = new Label("Add:");
		TextArea addArea = new TextArea();
		add.getChildren().addAll(addLabel, addArea);

		// Remove area creation
		Label removeLabel = new Label("Remove");
		TextArea removeArea = new TextArea();
		remove.getChildren().addAll(removeLabel, removeArea);

		// Adds content to scene
		content.getChildren().addAll(add, remove);
		contentBox.getChildren().addAll(navBar, content);
		root.getChildren().addAll(contentBox, infoVBox);
		Scene friendScene = new Scene(root);

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
	 */
	public void userInformation() {
		// Initializes layout
		root = new HBox();
		contentBox = new VBox();
		HBox content = new HBox();
		VBox friendList = new VBox();
		VBox friendSearch = new VBox();
		HBox currentUserArea = new HBox();

		// Friends list creation
		Label listLabel = new Label("Friends");
		ListView list = new ListView();
//		for (User user : socialNetwork.getFriends(centralUser.username))
//			list.getItems().add(user.username);
		friendList.getChildren().addAll(listLabel, list);

		// Current user creation
		Label userLabel = new Label("Current User: ");
		Text userText = new Text("username"); // TODO
		currentUserArea.getChildren().addAll(userLabel, userText);

		// Friend search creation
		Label searchLabel = new Label("Find Friend:");
		TextArea searchBar = new TextArea();
		Button searchButton = new Button("Search");
		friendSearch.getChildren().addAll(currentUserArea, searchLabel, searchBar, searchButton);

		// Adds content to the scene
		content.getChildren().addAll(friendList, friendSearch);
		contentBox.getChildren().addAll(navBar, content);
		root.getChildren().addAll(contentBox, infoVBox);
		Scene userScene = new Scene(root);

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
	 */
	public void friendImportExport() {
		// Initializes layout
		root = new HBox();
		contentBox = new VBox();
		VBox content = new VBox();
		VBox importExport = new VBox();
		VBox networkControl = new VBox();
		VBox setUser = new VBox();

		// Import/Export buttons
		Button im = new Button("Import");
		im.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		im.setStyle("-fx-background-color: #ffffff;");
		im.setPrefSize(200, 100);
		Button export = new Button("Export");
		export.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		export.setStyle("-fx-background-color: #ffffff;");
		export.setPrefSize(200, 100);
		importExport.getChildren().addAll(im, export);

		// Set user and clear buttons and fields
		Label setUserLabel = new Label("Set Main User:");
		setUserLabel.setPadding(new Insets(0, 0, 19,15));
		setUserLabel.setFont(Font.font("Arial", FontWeight.BOLD, 23));
		TextField setUserField = new TextField();
		setUserField.setPrefSize(100, 20);
		setUserField.setMinHeight(50);
		setUserField.setStyle("-fx-background-color: #ffffff;");
		setUser.getChildren().addAll(setUserLabel, setUserField);

		Button clear = new Button("Clear");
		clear.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		clear.setStyle("-fx-background-color: #ffffff;");
		clear.setPrefSize(200, 100);
		networkControl.getChildren().addAll(setUser, clear);

		content.setSpacing(150);
		content.setPadding(new Insets(100, 610, 200, 590));
		content.getChildren().addAll(setUser, im, export, clear);
		content.setStyle("-fx-background-color: #3490D1;");
		content.getChildren().addAll(importExport, networkControl);
		contentBox.getChildren().addAll(navBar, content);
		root.getChildren().addAll(contentBox, infoVBox);
		Scene commandScene = new Scene(root);

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