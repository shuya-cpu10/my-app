
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Button todoButton;

    @FXML
    private Button goalsButton;

    @FXML
    private Button statisticsButton;

    @FXML
    public void initialize() {

        int score = Database.loadScore();

        scoreLabel.setText(score + " XP");
    }

    @FXML
    private void handleTodo() throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource("Todo.fxml")
                );

        Scene scene =
                new Scene(loader.load());

        Stage stage =
                (Stage) todoButton
                        .getScene()
                        .getWindow();

        stage.setScene(scene);
        stage.setTitle("Todo");
    }

    @FXML
    private void handleGoals() throws Exception {

        String[] habits =
                Database.loadHabits();

        boolean habitsNotSet =
                habits[0] == null
                || habits[0].isEmpty();

        if (habitsNotSet) {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "HabitSetup.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    (Stage) goalsButton
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "Set Your Habits"
            );

            return;
        }

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Goals.fxml"
                        )
                );

        Scene scene =
                new Scene(loader.load());

        Stage stage =
                (Stage) goalsButton
                        .getScene()
                        .getWindow();

        stage.setScene(scene);
        stage.setTitle("Goals & Habits");
    }

  @FXML
private void handleStatistics() throws Exception {

    FXMLLoader loader =
            new FXMLLoader(
                    getClass().getResource(
                            "Statistics.fxml"
                    )
            );

    Scene scene =
            new Scene(loader.load());

    Stage stage =
            (Stage) statisticsButton
                    .getScene()
                    .getWindow();

    stage.setScene(scene);
    stage.setTitle("Statistics");
}
}
