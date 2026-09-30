
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MonthlyGoalController {

    @FXML
    private Label monthLabel;

    @FXML
    private TextField goalField;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {

        LocalDate today =
                LocalDate.now();

        int month =
                today.getMonthValue();

        monthLabel.setText(
                today.getMonth()
                        .toString()
                        + " "
                        + today.getYear()
        );

        String[] monthlyGoals =
                Database.loadMonthlyGoals();

        String currentGoal =
                monthlyGoals[month - 1];

        if (currentGoal != null
                && !currentGoal.isEmpty()) {

            goalField.setText(
                    currentGoal
            );
        }
    }

    @FXML
    private void handleSave() throws Exception {

        String goal =
                goalField.getText().trim();

        if (goal.isEmpty()) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.WARNING
                    );

            alert.setTitle(
                    "Monthly Goal"
            );

            alert.setHeaderText(null);

            alert.setContentText(
                    "Please enter your monthly goal."
            );

            alert.showAndWait();

            return;
        }

        LocalDate today =
                LocalDate.now();

        int month =
                today.getMonthValue();

        String[] monthlyGoals =
                Database.loadMonthlyGoals();

        monthlyGoals[month - 1] =
                goal;

        Database.saveMonthlyGoals(
                monthlyGoals
        );

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Goals.fxml"
                        )
                );

        Parent root =
                loader.load();

        Stage stage =
                (Stage) saveButton
                        .getScene()
                        .getWindow();

        stage.setScene(
                new Scene(root)
        );

        stage.setTitle(
                "Goals & Habits"
        );
    }

    @FXML
    private void handleBack() throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Goals.fxml"
                        )
                );

        Parent root =
                loader.load();

        Stage stage =
                (Stage) backButton
                        .getScene()
                        .getWindow();

        stage.setScene(
                new Scene(root)
        );

        stage.setTitle(
                "Goals & Habits"
        );
    }
}
