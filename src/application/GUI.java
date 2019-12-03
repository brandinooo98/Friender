package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
	private String centralUser;
	private VBox contentBox;
	private HBox navBar;
	private Rectangle2D primaryScreenBounds;
	private ArrayList<String> commands;

    /**
	 * @param primaryStage
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		// General instantiations
		window = primaryStage;
		socialNetwork = new SocialNetwork();

		// NAV BAR AND CANVAS
		contentBox = new VBox();

		navBar = new HBox();
		navBar.setMinWidth(primaryScreenBounds.getWidth());
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
		contentBox.getChildren().addAll(navBar);

		Scene mainScene = new Scene(contentBox);

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

	public void graphVisual() {
		// Initializes layout
		contentBox = new VBox();

		// Graph visualization
		Canvas graph = new Canvas();
		graph.setWidth(primaryScreenBounds.getWidth() * 0.75);
		graph.setHeight(primaryScreenBounds.getHeight());
		GraphicsContext gc = graph.getGraphicsContext2D();

		// Adds content to scene
		contentBox.getChildren().addAll(navBar, graph);

		Scene graphScene = new Scene(contentBox);

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

	private double relativeWidth(double in) {
		return primaryScreenBounds.getWidth() * (in / primaryScreenBounds.getWidth());
	}

	private double relativeHeight(double in) {
		return primaryScreenBounds.getHeight() * (in / primaryScreenBounds.getHeight());
	}

	public void friendManagement() {
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		// Initializes layout
		contentBox = new VBox();
		contentBox.setStyle("-fx-background-color: #3490D1;");

		VBox content = new VBox();
		HBox add = new HBox();
		HBox remove = new HBox();
		VBox friendships = new VBox();

		add.setPadding(new Insets(relativeHeight(100), relativeWidth(50), 0, relativeWidth(50)));
		remove.setPadding(new Insets(relativeWidth(50), relativeWidth(50), relativeWidth(100), relativeWidth(50)));
		friendships.setPadding(new Insets(relativeWidth(100), relativeWidth(50), relativeWidth(50), relativeWidth(50)));

		// Add area creation
		Label addLabel = new Label("Add Person: ");
		addLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
		addLabel.setPadding(new Insets(0, relativeWidth(78), 0, 0));

		TextField addArea = new TextField();
		addArea.setMaxHeight(0);
		addArea.setMinWidth(relativeWidth(500));
		addArea.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		Button addButton = new Button("Create");
		addButton.setOnAction(e -> {
			socialNetwork.addUser(addArea.getText());
			addArea.clear();
		});

		addButton.setMinHeight(relativeWidth(75));
		addButton.setMinWidth(relativeWidth(200));
		addButton.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		Region filler1 = new Region();
		HBox.setHgrow(filler1, Priority.ALWAYS);

		add.getChildren().addAll(addLabel, addArea, filler1, addButton);

		// Remove area creation
		Label removeLabel = new Label("Remove Person: ");
		removeLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		TextField removeArea = new TextField();
		removeArea.setMaxHeight(0);
		removeArea.setMinWidth(relativeWidth(500));
		removeArea.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		Button removeButton = new Button("Remove");
		removeButton.setOnAction(e -> {
			try {
				socialNetwork.removeUser(removeArea.getText());
			} catch (UserNotFoundException e1) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("This user does not exist in the Social Network. No action was taken!");
				a.show();
			}
			removeArea.clear();
		});

		removeButton.setMinHeight(relativeWidth(75));
		removeButton.setMinWidth(relativeWidth(200));
		removeButton.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		Region filler2 = new Region();
		HBox.setHgrow(filler2, Priority.ALWAYS);

		remove.getChildren().addAll(removeLabel, removeArea, filler2, removeButton);

		// Add friendship creation
		Label friendsLabel = new Label("Add Friendships: ");
		friendsLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		HBox fromBox = new HBox();
		fromBox.setPadding(new Insets(relativeWidth(50), relativeWidth(50), 0, relativeWidth(50)));

		Label fromLabel = new Label("From: ");
		fromLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		TextField fromArea = new TextField();
		fromArea.setMaxHeight(0);
		fromArea.setMinWidth(relativeWidth(500));
		fromArea.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		fromBox.getChildren().addAll(fromLabel, fromArea);

		HBox toBox = new HBox();
		toBox.setPadding(new Insets(relativeWidth(50), relativeWidth(50), relativeWidth(50), relativeWidth(50)));

		Label toLabel = new Label("To: ");
		toLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
		toLabel.setPadding(new Insets(0, relativeWidth(49), 0, 0));

		TextField toArea = new TextField();
		toArea.setMaxHeight(0);
		toArea.setMinWidth(relativeWidth(500));
		toArea.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		toBox.getChildren().addAll(toLabel, toArea);

		Button friendshipButton = new Button("Add");
		friendshipButton.setMinHeight(relativeWidth(75));
		friendshipButton.setMinWidth(relativeWidth(200));
		friendshipButton.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

		friendships.getChildren().addAll(friendsLabel, fromBox, toBox, friendshipButton);

		// Adds content to scene
		Separator separator1 = new Separator();
		content.getChildren().addAll(add, remove, separator1, friendships);
		contentBox.getChildren().addAll(navBar, content);
		Scene friendScene = new Scene(contentBox);

		// Makes window size of screen
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
		contentBox = new VBox();
		HBox content = new HBox();
		VBox friendList = new VBox();
		VBox friendSearch = new VBox();
		HBox currentUserArea = new HBox();

		// Friends list creation
		Label listLabel = new Label("Friends");
		ListView list = new ListView();
		// for (User user : socialNetwork.getFriends(centralUser.username))
		// list.getItems().add(user.username);
		friendList.getChildren().addAll(listLabel, list);

		// Current user creation
		Label userLabel = new Label("Current User: ");
		Text userText = new Text("username"); // TODO This should be centralUser.username
		currentUserArea.getChildren().addAll(userLabel, userText);

		// Friend search creation
		Label searchLabel = new Label("Find Friend:");
		TextField searchBar = new TextField();
		Button searchButton = new Button("Search");
		friendSearch.getChildren().addAll(currentUserArea, searchLabel, searchBar, searchButton);

		// Adds content to the scene
		content.getChildren().addAll(friendList, friendSearch);
		contentBox.getChildren().addAll(navBar, content);
		Scene userScene = new Scene(contentBox);

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
		contentBox = new VBox();
		VBox content = new VBox();
		VBox importExport = new VBox();
		VBox networkControl = new VBox();
		VBox setUser = new VBox();

		// Import button
		Button im = new Button("Import");
        im.setOnAction(e -> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Command Import");
                    dialog.setHeaderText("Insert the filename of your command file");
                    dialog.setContentText("Please enter file name:");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(filename -> {
                        try {
                            commands = socialNetwork.importCommands(filename);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                });
		im.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
		im.setStyle("-fx-background-color: #ffffff;");
		im.setPrefSize(relativeWidth(200), relativeHeight(100));

		// Export button
		Button export = new Button("Export");
        export.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Command Export");
            dialog.setHeaderText("Insert the filename of the log file you wish to print commands to");
            dialog.setContentText("Please enter file name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(filename -> {
                try {
                    socialNetwork.export(filename, commands);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
        });
		export.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
		export.setStyle("-fx-background-color: #ffffff;");
		export.setPrefSize(relativeWidth(200), relativeHeight(100));
		importExport.getChildren().addAll(im, export);

		// Set user and clear buttons and fields
		Label setUserLabel = new Label("Set Main User:");
		setUserLabel.setPadding(new Insets(0, 0, 0, relativeWidth(18)));
		setUserLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
		TextField setUserField = new TextField();
		setUserField.setPrefSize(relativeWidth(100), relativeHeight(20));
		setUserField.setMinHeight(relativeWidth(50));
		setUserField.setStyle("-fx-background-color: #ffffff;");
		Button set = new Button("Set User");
		set.setOnAction(e -> {
			if (socialNetwork.getAllUsers().contains(setUserField.getText())){
				centralUser = setUserField.getText();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText(centralUser + "is now the central user!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Username was not found");
				alert.setContentText("Would you like to create a user with this username?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					socialNetwork.addUser(setUserField.getText());
					centralUser = setUserField.getText();
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setTitle("Confirmation");
					alert1.setHeaderText(null);
					alert1.setContentText(centralUser + "is now the central user!");
					alert1.showAndWait();
				}
			}
			setUserField.clear();
		});
		set.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
		set.setStyle("-fx-background-color: #ffffff;");
		Pane setPane = new Pane(set);
		setPane.setPadding(new Insets(relativeWidth(19), 0, 0, relativeWidth(15)));
		setUser.getChildren().addAll(setUserLabel, setUserField, setPane);
		setUser.setSpacing(relativeWidth(19));

		Button clear = new Button("Clear");
		clear.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Clear Network Confirmation");
			alert.setHeaderText("Clear Social Network");
			alert.setContentText("Are you sure you want to clear the Social Network?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK)
				socialNetwork = new SocialNetwork();
		});
		clear.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
		clear.setStyle("-fx-background-color: #ffffff;");
		clear.setPrefSize(relativeWidth(200), relativeHeight(100));
		networkControl.getChildren().addAll(setUser, clear);

		// Adds content to the scene
		content.setSpacing(relativeWidth(150));
		content.setPadding(new Insets(relativeWidth(100), relativeWidth(610), relativeWidth(200), relativeWidth(590)));
		content.getChildren().addAll(setUser, im, export, clear);
		content.setStyle("-fx-background-color: #3490D1;");
		content.getChildren().addAll(importExport, networkControl);
		contentBox.getChildren().addAll(navBar, content);
		Scene commandScene = new Scene(contentBox);

		// Makes window size of screen
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