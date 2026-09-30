import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class GoalsController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Label yearGoalLabel;

    @FXML
    private Button setYearGoalButton;
 
    @FXML
    private ProgressBar yearProgressBar;

    @FXML
    private Label monthlyGoalLabel;

    @FXML
    private ProgressBar monthlyProgressBar;

    @FXML
    private Button checkYearGoalButton;

    @FXML
    private Label habit1Label;

    @FXML
    private ProgressBar habit1ProgressBar;

    @FXML
    private Label habit1RateLabel;

    @FXML
    private Label habit2Label;

    @FXML
    private ProgressBar habit2ProgressBar;

    @FXML
    private Label habit2RateLabel;

    @FXML
    private Label habit3Label;

    @FXML
    private ProgressBar habit3ProgressBar;

    @FXML
    private Label habit3RateLabel;

    @FXML
    private Button todayHabitsButton;
    @FXML
    private Button editMonthlyGoalButton;
    @FXML
    private Button checkMonthlyGoalButton;
    @FXML
    private Button backButton;


    // =========================
    // 画面を開いたとき
    // =========================

    @FXML
    public void initialize() {

        // =========================
        // Score
        // =========================

        int score = Database.loadScore();

        scoreLabel.setText(
                "Score: " + score + " XP"
        );


        // =========================
        // Year Goal
        // =========================

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

        // 今はYear Goalの達成率計算はまだ
        yearProgressBar.setProgress(0.0);


        // =========================
        // Monthly Goal
        // =========================

        String[] monthlyGoals =
                Database.loadMonthlyGoals();

        int currentMonth =
                java.time.LocalDate.now()
                        .getMonthValue();

        String currentMonthlyGoal =
                monthlyGoals[currentMonth - 1];

        if (currentMonthlyGoal != null
                && !currentMonthlyGoal.isEmpty()) {

            monthlyGoalLabel.setText(
                    currentMonthlyGoal
            );

        } else {

            monthlyGoalLabel.setText(
                    "No Monthly Goal"
            );
        }

        // 今はMonthly Goalの達成率計算はまだ
        monthlyProgressBar.setProgress(0.0);


        // =========================
        // Habits
        // =========================

        String[] habits =
                Database.loadHabits();

        if (habits[0] != null
                && !habits[0].isEmpty()) {

            habit1Label.setText(
                    habits[0]
            );

        } else {

            habit1Label.setText(
                    "Habit 1"
            );
        }


        if (habits[1] != null
                && !habits[1].isEmpty()) {

            habit2Label.setText(
                    habits[1]
            );

        } else {

            habit2Label.setText(
                    "Habit 2"
            );
        }


        if (habits[2] != null
                && !habits[2].isEmpty()) {

            habit3Label.setText(
                    habits[2]
            );

        } else {

            habit3Label.setText(
                    "Habit 3"
            );
        }


        // =========================
        // Habitの月間達成率
        // =========================

        java.time.LocalDate today =
                java.time.LocalDate.now();

        double[] rates =
                Database.loadHabitMonthlyRates(
                        today.getYear(),
                        today.getMonthValue()
                );


        // Habit 1

        habit1ProgressBar.setProgress(
                rates[0]
        );

        habit1RateLabel.setText(
                Math.round(rates[0] * 100) + "%"
        );


        // Habit 2

        habit2ProgressBar.setProgress(
                rates[1]
        );

        habit2RateLabel.setText(
                Math.round(rates[1] * 100) + "%"
        );


        // Habit 3

        habit3ProgressBar.setProgress(
                rates[2]
        );

        habit3RateLabel.setText(
                Math.round(rates[2] * 100) + "%"
        );
    }


    // =========================
    // Today's Habits
    // =========================
@FXML
private void handleTodayHabits()
        throws Exception {

    FXMLLoader loader =
            new FXMLLoader(
                    getClass().getResource(
                            "Habit.fxml"
                    )
            );

    Parent root =
            loader.load();

    Stage stage =
            (Stage) todayHabitsButton
                    .getScene()
                    .getWindow();

    stage.setScene(
            new Scene(root)
    );

    stage.setTitle(
            "Today's Habits"
    );
}
@FXML
private void handleCheckMonthlyGoal()
        throws Exception {

    FXMLLoader loader =
            new FXMLLoader(
                    getClass().getResource(
                            "MonthlyGoalCheck.fxml"
                    )
            );

    Parent root =
            loader.load();

    Stage stage =
            (Stage) checkMonthlyGoalButton
                    .getScene()
                    .getWindow();

    stage.setScene(
            new Scene(root)
    );

    stage.setTitle(
            "Monthly Goal Check"
    );
}
@FXML
private void handleCheckYearGoal() throws Exception {

    if (!Database.isYearGoalReady()) {

        System.out.println(
                "Year Goal is not ready yet."
        );

        return;
    }

    FXMLLoader loader =
            new FXMLLoader(
                    getClass().getResource(
                            "YearGoalCheck.fxml"
                    )
            );

    Scene scene =
            new Scene(loader.load());

    Stage stage =
            (Stage) checkYearGoalButton
                    .getScene()
                    .getWindow();

    stage.setScene(scene);

    stage.setTitle(
            "Year Goal Check"
    );
}
@FXML
private void handleSetYearGoal() throws Exception {

    FXMLLoader loader =
            new FXMLLoader(
                    getClass().getResource(
                            "YearGoalSetup.fxml"
                    )
            );

    Scene scene =
            new Scene(loader.load());

    Stage stage =
            (Stage) setYearGoalButton
                    .getScene()
                    .getWindow();

    stage.setScene(scene);

    stage.setTitle(
            "Set Year Goal"
    );
}
    // =========================
    // Back
    // =========================

    @FXML
    private void handleBack()
            throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "Main.fxml"
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
                "Goal Achievement App"
        );
    }
    @FXML
private void handleEditMonthlyGoal()
        throws Exception {

    FXMLLoader loader =
            new FXMLLoader(
                    getClass().getResource(
                            "MonthlyGoal.fxml"
                    )
            );

    Parent root =
            loader.load();

    Stage stage =
            (Stage) editMonthlyGoalButton
                    .getScene()
                    .getWindow();

    stage.setScene(
            new Scene(root)
    );

    stage.setTitle(
            "Monthly Goal"
    );
}
}