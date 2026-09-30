
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TodoController {

    @FXML
    private ListView<String> todoList;

    @FXML
    private TextField titleField;

    @FXML
    private ScrollPane monthScroll;

    @FXML
    private ScrollPane dayScroll;

    @FXML
    private ScrollPane hourScroll;

    @FXML
    private ScrollPane minuteScroll;

    @FXML
    private Button addTodoButton;

    @FXML
    private Button completeTodoButton;

    @FXML
    private Button backButton;


    // 現在選択されている月・日・時・分
    private int selectedMonth;
    private int selectedDay;
    private int selectedHour;
    private int selectedMinute;


    @FXML
    public void initialize() {

        // 初期値は今日
        LocalDate today = LocalDate.now();

        selectedMonth = today.getMonthValue();
        selectedDay = today.getDayOfMonth();

        // 初期時間
        selectedHour = 18;
        selectedMinute = 0;

        // スクロールを作る
        createMonthPicker();
        createDayPicker();
        createHourPicker();
        createMinutePicker();

        // DBからTodoを読み込む
        loadTodos();

        // 期限切れTodoを確認
        deleteExpiredTodos();
    }


    // =========================
    // Todo読み込み
    // =========================

    private void loadTodos() {

        todoList.getItems().clear();

        ArrayList<TodoItem> todos =
                Database.loadTodos();

        for (TodoItem todo : todos) {

            String status;

            if (todo.completed) {
                status = "✓ ";
            } else {
                status = "□ ";
            }

            String text =
                    status
                    + todo.title
                    + "   "
                    + "Due: "
                    + todo.dueDateTime;

            todoList.getItems().add(text);
        }

        System.out.println(
                "Todo読み込み完了: "
                + todos.size()
                + "件"
        );
    }


    // =========================
    // 月
    // =========================

    private void createMonthPicker() {

        VBox box = new VBox();
        box.setSpacing(5);

        for (int month = 1; month <= 12; month++) {

            Label label =
                    createPickerLabel(
                            String.format("%02d", month)
                    );

            final int value = month;

            label.setOnMouseClicked(event -> {

                selectedMonth = value;

                updatePickerSelection(
                        box,
                        label
                );

                System.out.println(
                        "選択した月: "
                        + selectedMonth
                );
            });

            box.getChildren().add(label);
        }

        monthScroll.setContent(box);

        // 今日の月を最初に選択
        Label todayLabel =
                (Label) box.getChildren()
                        .get(selectedMonth - 1);

        updatePickerSelection(
                box,
                todayLabel
        );

        monthScroll.setVvalue(
                (selectedMonth - 1) / 11.0
        );
    }


    // =========================
    // 日
    // =========================

    private void createDayPicker() {

        VBox box = new VBox();
        box.setSpacing(5);

        for (int day = 1; day <= 31; day++) {

            Label label =
                    createPickerLabel(
                            String.format("%02d", day)
                    );

            final int value = day;

            label.setOnMouseClicked(event -> {

                selectedDay = value;

                updatePickerSelection(
                        box,
                        label
                );

                System.out.println(
                        "選択した日: "
                        + selectedDay
                );
            });

            box.getChildren().add(label);
        }

        dayScroll.setContent(box);

        Label todayLabel =
                (Label) box.getChildren()
                        .get(selectedDay - 1);

        updatePickerSelection(
                box,
                todayLabel
        );

        dayScroll.setVvalue(
                (selectedDay - 1) / 30.0
        );
    }


    // =========================
    // 時
    // =========================

    private void createHourPicker() {

        VBox box = new VBox();
        box.setSpacing(5);

        for (int hour = 0; hour <= 23; hour++) {

            Label label =
                    createPickerLabel(
                            String.format("%02d", hour)
                    );

            final int value = hour;

            label.setOnMouseClicked(event -> {

                selectedHour = value;

                updatePickerSelection(
                        box,
                        label
                );

                System.out.println(
                        "選択した時間: "
                        + selectedHour
                );
            });

            box.getChildren().add(label);
        }

        hourScroll.setContent(box);

        Label defaultLabel =
                (Label) box.getChildren()
                        .get(selectedHour);

        updatePickerSelection(
                box,
                defaultLabel
        );

        hourScroll.setVvalue(
                selectedHour / 23.0
        );
    }


    // =========================
    // 分
    // =========================

    private void createMinutePicker() {

        VBox box = new VBox();
        box.setSpacing(5);

        for (int minute = 0; minute <= 59; minute++) {

            Label label =
                    createPickerLabel(
                            String.format("%02d", minute)
                    );

            final int value = minute;

            label.setOnMouseClicked(event -> {

                selectedMinute = value;

                updatePickerSelection(
                        box,
                        label
                );

                System.out.println(
                        "選択した分: "
                        + selectedMinute
                );
            });

            box.getChildren().add(label);
        }

        minuteScroll.setContent(box);

        Label defaultLabel =
                (Label) box.getChildren()
                        .get(selectedMinute);

        updatePickerSelection(
                box,
                defaultLabel
        );

        minuteScroll.setVvalue(
                selectedMinute / 59.0
        );
    }


    // =========================
    // PickerのLabel作成
    // =========================

    private Label createPickerLabel(String text) {

        Label label = new Label(text);

        label.setPrefWidth(80);
        label.setPrefHeight(30);

        label.setAlignment(
                javafx.geometry.Pos.CENTER
        );

        label.setStyle(
                "-fx-font-size: 18px;"
        );

        return label;
    }


    // =========================
    // 選択中の数字を表示
    // =========================

    private void updatePickerSelection(
            VBox box,
            Label selectedLabel
    ) {

        for (javafx.scene.Node node :
                box.getChildren()) {

            Label label =
                    (Label) node;

            label.setStyle(
                    "-fx-font-size: 18px;"
            );
        }

        selectedLabel.setStyle(
                "-fx-font-size: 22px;"
                + "-fx-font-weight: bold;"
        );
    }


    // =========================
    // Todo追加
    // =========================

    @FXML
    private void handleAddTodo() {

        String title =
                titleField.getText();

        if (title == null ||
            title.trim().isEmpty()) {

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Todo",
                    "Todoの名前を入力してください。"
            );

            return;
        }


        try {

            LocalDate selectedDate =
                    LocalDate.of(
                            LocalDate.now().getYear(),
                            selectedMonth,
                            selectedDay
                    );

            LocalTime selectedTime =
                    LocalTime.of(
                            selectedHour,
                            selectedMinute
                    );

            LocalDateTime dueDateTime =
                    LocalDateTime.of(
                            selectedDate,
                            selectedTime
                    );


            TodoItem todo =
                    new TodoItem();

            todo.title =
                    title.trim();

            todo.completed =
                    false;

            todo.dueDateTime =
                    dueDateTime;


            // DBに保存
            Database.saveTodo(todo);


            // 画面を更新
            loadTodos();


            // 入力欄を空にする
            titleField.clear();


            System.out.println(
                    "Todo追加: "
                    + todo.title
            );

            System.out.println(
                    "期限: "
                    + todo.dueDateTime
            );


            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Todo",
                    "Todoを追加しました！"
            );

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Todoを追加できませんでした。\n"
                    + e.getMessage()
            );
        }
    }


    // =========================
    // Todo完了
    // =========================

    @FXML
    private void handleCompleteTodo() {

        int selectedIndex =
                todoList
                        .getSelectionModel()
                        .getSelectedIndex();


        // 選択されていない
        if (selectedIndex == -1) {

            showMessage(
                    Alert.AlertType.INFORMATION,
                    "Todo",
                    "完了するTodoをクリックしてください。"
            );

            return;
        }


        // DBから最新データ取得
        ArrayList<TodoItem> todos =
                Database.loadTodos();


        if (selectedIndex >= todos.size()) {

            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Todoが見つかりません。"
            );

            return;
        }


        TodoItem selectedTodo =
                todos.get(selectedIndex);


        System.out.println(
                "選択したTodo: "
                + selectedTodo.title
        );

        System.out.println(
                "Todo ID: "
                + selectedTodo.id
        );


        // DBから削除
        Database.deleteTodo(
                selectedTodo.id
        );


        // XP +10
        int currentScore =
                Database.loadScore();

        Database.saveScore(
                currentScore + 10
        );


        // 画面から削除
        todoList
                .getItems()
                .remove(selectedIndex);


        showMessage(
                Alert.AlertType.INFORMATION,
                "Todo Completed",
                "🎉 Todo Completed!\n\n"
                + "+10 XP"
        );


        System.out.println(
                "Todo completed!"
        );

        System.out.println(
                "+10 XP"
        );
    }

// =========================
// 期限切れTodo削除
// =========================

private void deleteExpiredTodos() {

    ArrayList<TodoItem> todos =
            Database.loadTodos();

    LocalDateTime now =
            LocalDateTime.now();

    int expiredCount = 0;

    for (TodoItem todo : todos) {

        // 未完了 かつ 期限切れ
        if (!todo.completed &&
            todo.dueDateTime.isBefore(now)) {

            System.out.println(
                    "期限切れTodo: "
                    + todo.title
            );

            // DBから削除
            Database.deleteTodo(
                    todo.id
            );

            expiredCount++;
        }
    }

    // 期限切れTodoがあった場合だけScoreを減らす
    if (expiredCount > 0) {

        int currentScore =
                Database.loadScore();

        int penalty =
                expiredCount * 20;

        Database.saveScore(
                currentScore - penalty
        );

        System.out.println(
                "期限切れTodo: "
                + expiredCount
                + "件"
        );

        System.out.println(
                "Score -"
                + penalty
                + " XP"
        );
    }

    // 削除後のTodo一覧を表示
    loadTodos();
}



    // =========================
    // Back
    // =========================

    @FXML
    private void handleBack() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
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
                    "Goal Achievement"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // =========================
    // Alert
    // =========================

    private void showMessage(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert =
                new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
