import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MonthlyGoalCheckController {

    @FXML
    private Label monthLabel;

    @FXML
    private Label goalLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Button backButton;


    @FXML
    public void initialize() {

        LocalDate today =
                LocalDate.now();

        monthLabel.setText(
                today.getMonth()
                        .toString()
                        + " "
                        + today.getYear()
        );

        String[] monthlyGoals =
                Database.loadMonthlyGoals();

        String goal =
                monthlyGoals[
                    today.getMonthValue() - 1
                ];

        if (goal != null && !goal.isEmpty()) {

            goalLabel.setText(goal);

        } else {

            goalLabel.setText(
                    "No Monthly Goal"
            );
        }

        // すでに今月の判定が済んでいたら
        // YES / NO を押せないようにする
        boolean[] record =
                Database.loadMonthlyGoalRecord(
                        today.getYear(),
                        today.getMonthValue()
                );

        if (record != null && record[1]) {

            yesButton.setDisable(true);
            noButton.setDisable(true);

            goalLabel.setText(
                    goalLabel.getText()
                    + "\n\nAlready checked."
            );
        }
    }


    @FXML
    private void handleYes() {

        LocalDate today =
                LocalDate.now();

        boolean[] record =
                Database.loadMonthlyGoalRecord(
                        today.getYear(),
                        today.getMonthValue()
                );

        // すでに判定済みなら何もしない
        if (record != null && record[1]) {
            return;
        }

        Database.saveMonthlyGoalRecord(
                today.getYear(),
                today.getMonthValue(),
                true
        );

        int currentScore =
                Database.loadScore();

        Database.saveScore(
                currentScore + 100
        );

        yesButton.setDisable(true);
        noButton.setDisable(true);

        goalLabel.setText(
                goalLabel.getText()
                + "\n\n🎉 Monthly Goal achieved!"
                + "\n+100 XP"
        );

        System.out.println(
                "Monthly Goal +100 XP"
        );
    }


    @FXML
    private void handleNo() {

        LocalDate today =
                LocalDate.now();

        boolean[] record =
                Database.loadMonthlyGoalRecord(
                        today.getYear(),
                        today.getMonthValue()
                );

        // すでに判定済みなら何もしない
        if (record != null && record[1]) {
            return;
        }

        Database.saveMonthlyGoalRecord(
                today.getYear(),
                today.getMonthValue(),
                false
        );

        yesButton.setDisable(true);
        noButton.setDisable(true);

        goalLabel.setText(
                goalLabel.getText()
                + "\n\nMonthly Goal not achieved."
        );

        System.out.println(
                "Monthly Goal not achieved."
        );
    }


    @FXML
    private void handleBack()
            throws Exception {

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