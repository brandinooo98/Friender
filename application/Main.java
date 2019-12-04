package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;

/**
 *
 */
public class Main extends Application {
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
    private Canvas graph;

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
        commands = new ArrayList<>();

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
        view.setMinWidth(navBar.getMinWidth() * 0.225);
        view.setMinHeight(navBar.getMinHeight());
        Button edit = new Button("Edit");
        edit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        edit.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
        edit.setOnAction(e -> friendManagement());
        edit.setMinWidth(navBar.getMinWidth() * 0.225);
        edit.setMinHeight(navBar.getMinHeight());
        Button friends = new Button("Friends");
        friends.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        friends.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
        friends.setOnAction(e -> userInformation());
        friends.setMinWidth(navBar.getMinWidth() * 0.225);
        friends.setMinHeight(navBar.getMinHeight());
        Button im = new Button("Import");
        im.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        im.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff;");
        im.setOnAction(e -> friendImportExport());
        im.setMinWidth(navBar.getMinWidth() * 0.225);
        im.setMinHeight(navBar.getMinHeight());
        Button exit = new Button();
        exit.setText("Exit");
        exit.setTextFill(Color.BLACK);
        exit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        exit.setStyle("-fx-border-color: #000000; -fx-background-color: #d93030;");
        exit.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Would you like to save before exiting?");
            alert.setContentText("Choose your option.");
            ButtonType buttonTypeOne = new ButtonType("Save");
            ButtonType buttonTypeTwo = new ButtonType("Exit without saving");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Command Export");
                dialog.setHeaderText("Insert the filename of the log file you wish to print commands to");
                dialog.setContentText("Please enter file name:");
                Optional<String> saveResult = dialog.showAndWait();
                saveResult.ifPresent(filename -> {
                    try {
                        socialNetwork.export(filename, commands);
                        Alert alert1 = new Alert(AlertType.INFORMATION);
                        alert1.setTitle("Confirmation");
                        alert1.setHeaderText(null);
                        alert1.setContentText(filename + " was successfully created!");
                        alert1.showAndWait();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                });
            } else if (result.get() == buttonTypeTwo) {
                Platform.exit();
                System.exit(0);
            }
        });
        exit.setMinWidth(navBar.getMinWidth() * 0.225);
        exit.setMinHeight(navBar.getMinHeight());
        Button[] buttons = {view, edit, friends, im, exit};

        navBar.getChildren().addAll(buttons);
        friendImportExport();
    }

    class CircleData {

        int x;
        int y;
        int r = 23;
        String username;

        CircleData(int x, int y, String username) {
            this.x = x;
            this.y = y;
            this.username = username;
        }
    }

    private boolean overlapCheck(ArrayList<CircleData> circles, int circlex, int circley) {
        for (CircleData circledata : circles) {
            int distx = (circlex - circledata.x) * (circlex - circledata.x);
            int disty = (circley - circledata.y) * (circley - circledata.y);
            int distSum = distx + disty;
            int sqrtDist = (int) Math.sqrt(distSum);

            if (sqrtDist <= 70) {
                return false;
            }
        }

        return true;
    }

    private int[] generateCoords(ArrayList<CircleData> circles) {
        Random rand = new Random();
        int circlex = rand.nextInt((int) graph.getWidth());
        int circley = rand.nextInt((int) graph.getHeight());
        int[] ret = new int[2];
        ret[0] = circlex;
        ret[1] = circley;

        if (!overlapCheck(circles, circlex, circley)) {
            ret = generateCoords(circles);
        }

        if (circley > relativeHeight(1200) || circley < relativeHeight(25)) {
            ret = generateCoords(circles);
        }

        if (circlex < relativeWidth(25) || circlex > relativeWidth(2400)) {
            ret = generateCoords(circles);
        }

        System.out.println("x: " + ret[0] + " y: " + ret[1]);
        return ret;
    }

    public void graphVisual() {
        // Initializes layout
        contentBox = new VBox();
        VBox wrapper = new VBox();
        wrapper.setMinHeight(primaryScreenBounds.getHeight() - 50);
        wrapper.setMinWidth(primaryScreenBounds.getWidth());

        // Graph visualization
        graph = new Canvas();
        graph.setWidth(primaryScreenBounds.getWidth());
        graph.setHeight(primaryScreenBounds.getHeight() - 50);
        GraphicsContext gc = graph.getGraphicsContext2D();

        ArrayList<CircleData> circles = new ArrayList<CircleData>();

        // Adding the verticies

        for (String user : socialNetwork.getAllUsers()) {

            StackPane pane = new StackPane();
            pane.setPrefSize(26, 26);

            Circle circle = new Circle(23);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITE);
            circle.setStrokeWidth(3);
            pane.getChildren().add(circle);

            Text text = new Text(user);
            pane.getChildren().add(text);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);

            int[] coords = generateCoords(circles);

            gc.drawImage(pane.snapshot(parameters, null), coords[0], coords[1]);

            circles.add(new CircleData(coords[0], coords[1], user));
        }

        // Adding all the edges

        // Adds content to scene
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(graph);
        wrapper.setStyle("-fx-background-color: #3490D1;");
        contentBox.getChildren().addAll(navBar, wrapper);

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
        BorderPane friendships = new BorderPane();
        VBox addFriendships = new VBox();
        VBox removeFriendships = new VBox();

        add.setPadding(new Insets(relativeHeight(100), relativeWidth(50), 0, relativeWidth(50)));
        remove.setPadding(new Insets(relativeWidth(50), relativeWidth(50), relativeWidth(100), relativeWidth(50)));
        friendships.setPadding(new Insets(relativeWidth(10), relativeWidth(50), relativeWidth(50), relativeWidth(50)));
        addFriendships
                .setPadding(new Insets(relativeWidth(100), relativeWidth(50), relativeWidth(50), relativeWidth(50)));
        removeFriendships
                .setPadding(new Insets(relativeWidth(100), relativeWidth(50), relativeWidth(50), relativeWidth(50)));

        // Add area creation
        Label addLabel = new Label("Add User: ");
        addLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
        addLabel.setPadding(new Insets(0, relativeWidth(78), 0, 0));

        TextField addArea = new TextField();
        addArea.setMaxHeight(0);
        addArea.setMinWidth(relativeWidth(500));
        addArea.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        Button addButton = new Button("Create");
        addButton.setOnAction(e -> {
            if (!isValidName(addArea.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Username Error");
                alert.setHeaderText(null);
                alert.setContentText(addArea.getText() + " is not a valid username, you may only use letters, digits, underscore and apostrophe characters");
                alert.showAndWait();
            } else if (socialNetwork.getAllUsers().contains(addArea.getText())) {
                Alert a = new Alert(AlertType.WARNING);
                a.setContentText("The user: " + addArea.getText() + " is already in the Social Network.");
                a.show();
                commands.add("a " + addArea.getText());
            } else {
                socialNetwork.addUser(addArea.getText());
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("The user: " + addArea.getText() + " has been added to the Social Network.");
                a.show();
                commands.add("a " + addArea.getText());
            }
            addArea.clear();
        });

        addButton.setMinHeight(relativeWidth(75));
        addButton.setMinWidth(relativeWidth(200));
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        Region filler1 = new Region();
        HBox.setHgrow(filler1, Priority.ALWAYS);

        add.getChildren().addAll(addLabel, addArea, filler1, addButton);

        // Remove area creation
        Label removeLabel = new Label("Remove User: ");
        removeLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        TextField removeArea = new TextField();
        removeArea.setMaxHeight(0);
        removeArea.setMinWidth(relativeWidth(500));
        removeArea.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            try {
                socialNetwork.removeUser(removeArea.getText());
                if (removeArea.getText().equals(centralUser))
                    centralUser = null;
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("The user: " + removeArea.getText() + " has been removed from the Social Network.");
                a.show();
                commands.add("r " + addArea.getText());
            } catch (UserNotFoundException e1) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("This user does not exist in the Social Network. No changes were made.");
                a.show();
                commands.add("r " + addArea.getText());
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

        friendshipButton.setOnAction(e -> {
            if (!isValidName(toArea.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Username Error");
                alert.setHeaderText(null);
                alert.setContentText(toArea.getText() + " is not a valid username, you may only use letters, digits, underscore and apostrophe characters");
                alert.showAndWait();
            } else if (!isValidName(fromArea.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Username Error");
                alert.setHeaderText(null);
                alert.setContentText(fromArea.getText() + " is not a valid username, you may only use letters, digits, underscore and apostrophe characters");
                alert.showAndWait();
            } else {
                socialNetwork.addFriend(toArea.getText(), fromArea.getText());
                commands.add("a " + toArea.getText() + " " + fromArea.getText());
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Confirmation");
                alert1.setHeaderText(null);
                alert1.setContentText(toArea.getText() + " and " + fromArea.getText() + " are now friends!");
                alert1.showAndWait();
                toArea.clear();
                fromArea.clear();
            }
        });

        friendshipButton.setMinHeight(relativeWidth(75));
        friendshipButton.setMinWidth(relativeWidth(200));
        friendshipButton.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        addFriendships.getChildren().addAll(friendsLabel, fromBox, toBox, friendshipButton);

        // Remove friendship creation
        Label friendsLabel2 = new Label("Remove Friendships: ");
        friendsLabel2.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        HBox fromBox2 = new HBox();
        fromBox2.setPadding(new Insets(relativeWidth(50), relativeWidth(50), 0, relativeWidth(50)));

        Label fromLabel2 = new Label("From: ");
        fromLabel2.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        TextField fromArea2 = new TextField();
        fromArea2.setMaxHeight(0);
        fromArea2.setMinWidth(relativeWidth(500));
        fromArea2.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        fromBox2.getChildren().addAll(fromLabel2, fromArea2);

        HBox toBox2 = new HBox();
        toBox2.setPadding(new Insets(relativeWidth(50), relativeWidth(50), relativeWidth(50), relativeWidth(50)));

        Label toLabel2 = new Label("To: ");
        toLabel2.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
        toLabel2.setPadding(new Insets(0, relativeWidth(49), 0, 0));

        TextField toArea2 = new TextField();
        toArea2.setMaxHeight(0);
        toArea2.setMinWidth(relativeWidth(500));
        toArea2.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        toBox2.getChildren().addAll(toLabel2, toArea2);

        Button friendshipButton2 = new Button("Remove");

        friendshipButton2.setOnAction(e -> {
            try {
                socialNetwork.removeFriend(toArea2.getText(), fromArea2.getText());
                commands.add("r " + toArea2.getText() + " " + fromArea2.getText());
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Confirmation");
                alert1.setHeaderText(null);
                alert1.setContentText(toArea2.getText() + " and " + fromArea2.getText() + " are no longer friends!");
                alert1.showAndWait();
            } catch (UserNotFoundException e1) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("One of the users was invalid. No changes were made.");
                a.show();
            }
            toArea2.clear();
            fromArea2.clear();
        });

        friendshipButton2.setMinHeight(relativeWidth(75));
        friendshipButton2.setMinWidth(relativeWidth(200));
        friendshipButton2.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));

        removeFriendships.getChildren().addAll(friendsLabel2, fromBox2, toBox2, friendshipButton2);

        friendships.setLeft(addFriendships);

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        friendships.setCenter(separator1);

        friendships.setRight(removeFriendships);

        // Adds content to scene
        Separator separator2 = new Separator();
        content.getChildren().addAll(add, remove, separator2, friendships);
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
     *
     */
    public void userInformation() {
        // Initializes layout
        contentBox = new VBox();
        contentBox.setStyle("-fx-background-color: #3490D1;");
        HBox content = new HBox(20);
        VBox friendList = new VBox(20);
        VBox friendSearch = new VBox(20);
        HBox currentUserArea = new HBox(20);

        // Friends list creation
        Label listLabel = new Label("Friends");
        listLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
        listLabel.setPadding(new Insets(0, 0, 0, relativeWidth(170)));
        ListView list = new ListView();
        list.setMinWidth(relativeWidth(500));
        if (centralUser != null) {
            try {
                for (User user : socialNetwork.getFriends(centralUser)) {
                    Text friend = new Text(user.username);
                    friend.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
                    list.getItems().add(friend);
                }
            } catch (UserNotFoundException e) {
            } // Should never occur
        } else {
            Text noUser = new Text("There is no central user");
            noUser.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(20)));
            list.getItems().add(noUser);
        }
        friendList.getChildren().addAll(listLabel, list);
        friendList.setPadding(new Insets(relativeHeight(200), 0, 0, relativeWidth(600)));
        // TODO FIX PADDING?

        // Current user creation
        Label userLabel = new Label("Current User: ");
        userLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
        Text userText = new Text(centralUser); // TODO This should be centralUser.username
        userText.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
        currentUserArea.getChildren().addAll(userLabel, userText);
        // TODO FIX PADDING?

        // Friend search creation
        Label searchLabel = new Label("Find Friend:");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(40)));
        searchLabel.setPadding(new Insets(relativeHeight(200), 0, 0, 0));
        TextField searchBar = new TextField();
        searchBar.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(30)));
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            try {
                if (!isValidName(searchBar.getText())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Username Error");
                    alert.setHeaderText(null);
                    alert.setContentText(searchBar.getText() + " is not a valid username, you may only use letters, digits, underscore and apostrophe characters");
                    alert.showAndWait();
                } else if (socialNetwork.getFriends(centralUser).contains(searchBar.getText())) {
                    centralUser = searchBar.getText();
                    commands.add("s " + centralUser);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText(centralUser + " is now the central user!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("User is not friends with " + centralUser);
                    alert.setContentText("Would you like to create a friendship with this user?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        commands.add("a " + centralUser + " " + searchBar.getText());
                        socialNetwork.addFriend(centralUser, searchBar.getText());
                        centralUser = searchBar.getText();
                        commands.add("s " + centralUser);
                        Alert alert1 = new Alert(AlertType.INFORMATION);
                        alert1.setTitle("Confirmation");
                        alert1.setHeaderText(null);
                        alert1.setContentText(searchBar.getText() + " is now friends with " + centralUser + " and is now the central user");
                        alert1.showAndWait();
                    }
                }
            } catch (UserNotFoundException ex) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Username was not found");
                alert.setContentText("Would you like to create a user with this username?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    socialNetwork.addUser(searchBar.getText());
                    centralUser = searchBar.getText();
                    commands.add("a " + centralUser);
                    commands.add("s " + centralUser);
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setHeaderText(null);
                    alert1.setContentText(centralUser + " is now the central user!");
                    alert1.showAndWait();
                }
            }
            userInformation();
        });
        searchButton.setFont(Font.font("Arial", FontWeight.BOLD, relativeWidth(30)));
        friendSearch.getChildren().addAll(currentUserArea, searchLabel, searchBar, searchButton);
        friendSearch.setPadding(new Insets(relativeHeight(200), 0, 0, relativeWidth(200)));
        // TODO PADDING

        // Adds content to the scene
        content.getChildren().addAll(friendSearch, friendList);
        contentBox.getChildren().addAll(navBar, content);
        // TODO COLOR / PADDING
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
     *
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
                    commands.addAll(socialNetwork.importCommands(filename));
                    commandParser(commands);
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setHeaderText(null);
                    alert1.setContentText(filename + " was successfully read!");
                    alert1.showAndWait();
                } catch (FileNotFoundException | UserNotFoundException ex) {
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
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setHeaderText(null);
                    alert1.setContentText(filename + " was successfully created!");
                    alert1.showAndWait();
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
            if (!isValidName(setUserField.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Username Error");
                alert.setHeaderText(null);
                alert.setContentText(setUserField.getText() + " is not a valid username, you may only use letters, digits, underscore and apostrophe characters");
                alert.showAndWait();
            } else if (socialNetwork.getAllUsers().contains(setUserField.getText())) {
                centralUser = setUserField.getText();
                commands.add("s " + centralUser);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText(centralUser + " is now the central user!");
                alert.showAndWait();
                setUserField.clear();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Username was not found");
                alert.setContentText("Would you like to create a user with this username?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    socialNetwork.addUser(setUserField.getText());
                    centralUser = setUserField.getText();
                    commands.add("s " + centralUser);
                    commands.add("a " + centralUser);
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setHeaderText(null);
                    alert1.setContentText(centralUser + "is now the central user!");
                    alert1.showAndWait();
                }
                setUserField.clear();
            }
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
            if (result.get() == ButtonType.OK) {
                socialNetwork = new SocialNetwork();
                centralUser = null;
            }
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
     * @param commands
     */
    private String commandParser(ArrayList<String> commands) throws UserNotFoundException {
        for (String command : commands) {
            String[] commandData = command.split(" ");
            for (String data : commandData) {
                if (!isValidName(data)) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Username Error");
                    alert.setHeaderText(null);
                    alert.setContentText(data + " is not a valid username, you may only use letters, digits, underscore and apostrophe characters");
                    alert.showAndWait();
                }
            }
            switch (commandData[0]) {
                case "a":
                    if (commandData.length == 3)
                        socialNetwork.addFriend(commandData[1], commandData[2]);
                    else
                        socialNetwork.addUser(commandData[1]);
                    break;
                case "r":
                    if (commandData.length == 3)
                        socialNetwork.removeFriend(commandData[1], commandData[2]);
                    else if (commandData.length == 1) {
                    } else
                        socialNetwork.removeUser(commandData[1]);
                    break;
                case "s":
                    centralUser = commandData[1];
            }
        }
        return null;
    }

    private boolean isValidName(String username) {
        boolean valid = true;
        char[] usernameCharacters = username.toCharArray(); // Stores username as an array of characters
        // Iterates through every character seeing if it is valid or not
        for (char c : usernameCharacters) {
            valid = ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')) || c == '\'' || c == '_';
            if (!valid)
                break;
        }
        return valid;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
