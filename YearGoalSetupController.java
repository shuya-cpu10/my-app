import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class YearGoalSetupController {

    @FXML
    private TextField yearGoalField;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;


    @FXML
    private void handleSave() throws Exception {

        String yearGoal =
                yearGoalField.getText().trim();

        if (yearGoal.isEmpty()) {

            System.out.println(
                    "Please enter a Year Goal."
            );

            return;
        }

        LocalDate startDate =
                LocalDate.now();

        Database.saveYearGoal(
                yearGoal,
                startDate
        );

        System.out.println(
                "Year Goal saved!"
        );

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Goals.fxml"
                        )
                );

        Scene scene =
                new Scene(loader.load());

        Stage stage =
                (Stage) saveButton
                        .getScene()
                        .getWindow();

        stage.setScene(scene);

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

        Scene scene =
                new Scene(loader.load());

        Stage stage =
                (Stage) backButton
                        .getScene()
                        .getWindow();

        stage.setScene(scene);

        stage.setTitle(
                "Goals & Habits"
        );
    }
}