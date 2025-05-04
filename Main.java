import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private BorderPane root;
    private VBox contentArea;
    private Timeline pomodoroTimeline;
    private int timeRemaining = 25 * 60; // 25 minutes

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        contentArea = new VBox(10);
        contentArea.setStyle("-fx-padding: 20;");

        HBox navBar = createNavBar();

        root.setTop(navBar);
        root.setCenter(contentArea);

        loadHome();

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("Style.css");

        primaryStage.setTitle("todoPointer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createNavBar() {
        Button homeBtn = new Button("Home");
        Button aboutBtn = new Button("About");
        Button contactBtn = new Button("Contact");
        Button todoBtn = new Button("ToDo List");
        Button pomoBtn = new Button("Pomodoro Timer");

        homeBtn.setOnAction(e -> loadHome());
        aboutBtn.setOnAction(e -> loadAbout());
        contactBtn.setOnAction(e -> loadContact());
        todoBtn.setOnAction(e -> loadTodoList());
        pomoBtn.setOnAction(e -> loadPomodoro());

        HBox nav = new HBox(10, homeBtn, aboutBtn, contactBtn, todoBtn, pomoBtn);
        nav.setStyle("-fx-background-color: #222; -fx-padding: 15;");
        nav.getChildren().forEach(btn -> btn.setStyle("-fx-text-fill: white; -fx-background-color: #444;"));

        return nav;
    }

    private void loadHome() {
        contentArea.getChildren().setAll(
            new Text("Welcome to our app "),
            new Text("we want you to test our products before using them."),
            new Text("because we obey your choice.")
        );
    }

    private void loadAbout() {
        contentArea.getChildren().setAll(
            new Text("About This App"),
            new Text("This is our app which provides a ToDo list to you."),
            new Text("This ToDo list also includes a Pomodoro timer.")
        );
    }

    private void loadContact() {
        contentArea.getChildren().setAll(
            new Text("Contact Us"),
            new Text("If you have any problem,"),
            new Text("then contact us on the given email."),
            new Text("Email: info@example.com")
        );
    }

    private void loadTodoList() {
        VBox todoBox = new VBox(10);
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter new task");
        Button addBtn = new Button("Add Task");
        ListView<String> taskList = new ListView<>();

        addBtn.setOnAction(e -> {
            String task = taskInput.getText().trim();
            if (!task.isEmpty()) {
                taskList.getItems().add(task);
                taskInput.clear();
            }
        });

        Button removeBtn = new Button("Remove Selected");
        removeBtn.setOnAction(e -> {
            String selected = taskList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                taskList.getItems().remove(selected);
            }
        });

        todoBox.getChildren().addAll(new Text("ToDo List"), taskInput, addBtn, taskList, removeBtn);
        contentArea.getChildren().setAll(todoBox);
    }

    private void loadPomodoro() {
        VBox pomoBox = new VBox(10);
        Label timerLabel = new Label(formatTime(timeRemaining));
        Button startBtn = new Button("Start");
        Button stopBtn = new Button("Stop");

        startBtn.setOnAction(e -> {
            if (pomodoroTimeline != null) pomodoroTimeline.stop();
            pomodoroTimeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                timeRemaining--;
                timerLabel.setText(formatTime(timeRemaining));
                if (timeRemaining <= 0) {
                    pomodoroTimeline.stop();
                    timerLabel.setText("Time's up!");
                }
            }));
            pomodoroTimeline.setCycleCount(Timeline.INDEFINITE);
            pomodoroTimeline.play();
        });

        stopBtn.setOnAction(e -> {
            if (pomodoroTimeline != null) pomodoroTimeline.stop();
        });

        pomoBox.getChildren().addAll(new Text("Pomodoro Timer"), timerLabel, startBtn, stopBtn);
        contentArea.getChildren().setAll(pomoBox);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
