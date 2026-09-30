import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDate;

public class StatisticsController {

    @FXML
    private LineChart<String, Number> achievementChart;

    @FXML
    private Button backButton;


    @FXML
    public void initialize() {

        XYChart.Series<String, Number> series =
                new XYChart.Series<>();

        series.setName("Daily Achievement");


        // 過去7日分
        for (int i = 6; i >= 0; i--) {

            LocalDate date =
                    LocalDate.now().minusDays(i);

            double achievementRate =
                    calculateAchievementRate(date);

            series.getData().add(
                    new XYChart.Data<>(
                            date.toString(),
                            achievementRate
                    )
            );
        }


        achievementChart.getData().add(series);

        // 縦軸を0～100%にする
        achievementChart
                .getYAxis()
                .setLabel("Achievement (%)");

        achievementChart
                .getXAxis()
                .setLabel("Date");
    }


    private double calculateAchievementRate(
            LocalDate date) {

        // =========================
        // Todoの達成率
        // =========================

        int[] todoData =
                Database.loadTodoDailyCount(date);

        int totalTodos =
                todoData[0];

        int completedTodos =
                todoData[1];

        double todoRate = 0.0;

        if (totalTodos > 0) {

            todoRate =
                    completedTodos
                    / (double) totalTodos
                    * 100.0;

        }


        // =========================
        // Habitの達成率
        // =========================

        boolean[] habitRecord =
                Database.loadHabitRecord(date);

        double habitRate = 0.0;

        if (habitRecord != null) {

            int completedHabits = 0;

            for (int i = 0; i < 3; i++) {

                if (habitRecord[i]) {
                    completedHabits++;
                }
            }

            habitRate =
                    completedHabits
                    / 3.0
                    * 100.0;
        }


        // =========================
        // Todo 50% + Habit 50%
        // =========================

        return todoRate * 0.5
                + habitRate * 0.5;
    }


    @FXML
    private void handleBack()
            throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Main.fxml"
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
                "Goal Achievement"
        );
    }
}