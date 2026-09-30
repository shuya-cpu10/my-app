import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class YearGoalCheckController {

    @FXML
    private Label yearGoalLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Button backButton;


    @FXML
    public void initialize() {

        String yearGoal =
                Database.loadYearGoal();

        if (yearGoal != null
                && !yearGoal.isEmpty()) {

            yearGoalLabel.setText(
                    yearGoal
            );

        } else {

            yearGoalLabel.setText(
                    "No Year Goal"
            );
        }

        // すでに判定済みならボタンを押せない
        if (Database.isYearGoalChecked()) {

            yesButton.setDisable(true);
            noButton.setDisable(true);

            yearGoalLabel.setText(
                    yearGoalLabel.getText()
                    + "\n\nAlready checked."
            );
        }
    }


    @FXML
    private void handleYes() {

        // すでに判定済みなら何もしない
        if (Database.isYearGoalChecked()) {
            return;
        }

        // 判定結果を保存
        Database.saveYearGoalResult(true);

        // +1000 XP
        Database.addScore(1000);

        yesButton.setDisable(true);
        noButton.setDisable(true);

        yearGoalLabel.setText(
                yearGoalLabel.getText()
                + "\n\n🎉 Year Goal achieved!\n+1000 XP"
        );

        System.out.println(
                "Year Goal achieved! +1000 XP"
        );
    }


    @FXML
    private void handleNo() {

        // すでに判定済みなら何もしない
        if (Database.isYearGoalChecked()) {
            return;
        }

        // 判定結果を保存
        Database.saveYearGoalResult(false);

        yesButton.setDisable(true);
        noButton.setDisable(true);

        yearGoalLabel.setText(
                yearGoalLabel.getText()
                + "\n\nYear Goal was not achieved."
        );

        System.out.println(
                "Year Goal not achieved."
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