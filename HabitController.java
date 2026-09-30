import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class HabitController {

    @FXML
    private CheckBox habit1CheckBox;

    @FXML
    private CheckBox habit2CheckBox;

    @FXML
    private CheckBox habit3CheckBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;


    // =========================
    // 画面を開いたとき
    // =========================

    @FXML
public void initialize() {

    // MySQLからHabitの名前を読み込む
    String[] habits = Database.loadHabits();

    habit1CheckBox.setText(
        habits[0] != null ? habits[0] : "Habit 1"
    );

    habit2CheckBox.setText(
        habits[1] != null ? habits[1] : "Habit 2"
    );

    habit3CheckBox.setText(
        habits[2] != null ? habits[2] : "Habit 3"
    );


    // =========================
    // 前日の未記録Habitを自動処理
    // =========================

    LocalDate today =
        LocalDate.now();

    LocalDate lastCheckDate =
        Database.loadLastHabitCheckDate();

    if (lastCheckDate != null) {

        LocalDate checkDate =
            lastCheckDate.plusDays(1);

        int penalty = 0;

        while (checkDate.isBefore(today)) {

            boolean[] record =
                Database.loadHabitRecord(checkDate);

            // その日の記録が存在しない
            if (record == null) {

                // 3つのHabitをすべて未達として記録
                Database.saveHabitRecord(
                    checkDate,
                    false,
                    false,
                    false
                );

                // 1Habit -20 XP × 3
                penalty -= 60;
            }

            checkDate =
                checkDate.plusDays(1);
        }


        // XPを減らす
        if (penalty != 0) {

            int currentScore =
                Database.loadScore();

            Database.saveScore(
                currentScore + penalty
            );

            System.out.println(
                "Missed Habit penalty: "
                + penalty
                + " XP"
            );
        }
    }
}
    // =========================
    // 今日のHabitを保存
    // =========================

   @FXML
private void handleSave() {

    LocalDate today =
        LocalDate.now();

    boolean habit1Completed =
        habit1CheckBox.isSelected();

    boolean habit2Completed =
        habit2CheckBox.isSelected();

    boolean habit3Completed =
        habit3CheckBox.isSelected();

    boolean[] oldRecord =
        Database.loadHabitRecord(today);

    int scoreChange = 0;

    if (oldRecord == null) {

        scoreChange +=
            habit1Completed ? 10 : -20;

        scoreChange +=
            habit2Completed ? 10 : -20;

        scoreChange +=
            habit3Completed ? 10 : -20;

    } else {

        if (oldRecord[0] != habit1Completed) {
            scoreChange +=
                habit1Completed ? 30 : -30;
        }

        if (oldRecord[1] != habit2Completed) {
            scoreChange +=
                habit2Completed ? 30 : -30;
        }

        if (oldRecord[2] != habit3Completed) {
            scoreChange +=
                habit3Completed ? 30 : -30;
        }
    }

    Database.saveHabitRecord(
        today,
        habit1Completed,
        habit2Completed,
        habit3Completed
    );

    if (scoreChange != 0) {

        int currentScore =
            Database.loadScore();

        Database.saveScore(
            currentScore + scoreChange
        );
    }

    System.out.println(
        "Habit XP: "
        + (scoreChange >= 0 ? "+" : "")
        + scoreChange
        + " XP"
    );
    Database.saveLastHabitCheckDate(today);
}

    // =========================
    // Main画面に戻る
    // =========================

   @FXML
private void handleBack() throws Exception {

    FXMLLoader loader =
        new FXMLLoader(
            getClass().getResource("Goals.fxml")
        );

    Parent root = loader.load();

    Stage stage =
        (Stage) backButton
            .getScene()
            .getWindow();

    stage.setScene(
        new Scene(root)
    );

    stage.setTitle(
        "Goal Achievement"
    );
} 
}