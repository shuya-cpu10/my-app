
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HabitSetupController {

    @FXML
    private TextField habit1Field;

    @FXML
    private TextField habit2Field;

    @FXML
    private TextField habit3Field;

    @FXML
    private Button saveButton;

    @FXML
    private void handleSave() throws Exception {

        String habit1 =
                habit1Field.getText().trim();

        String habit2 =
                habit2Field.getText().trim();

        String habit3 =
                habit3Field.getText().trim();

        if (habit1.isEmpty()
                || habit2.isEmpty()
                || habit3.isEmpty()) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Habit Setup");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Please enter all 3 habits."
            );

            alert.showAndWait();

            return;
        }

        String[] habits = {
                habit1,
                habit2,
                habit3
        };

        Database.saveHabits(habits);

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Goals.fxml"
                        )
                );

        Parent root = loader.load();

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
}
