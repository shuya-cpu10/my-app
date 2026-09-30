
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Database {

    private static final String DB_FOLDER =
        System.getenv("APPDATA") + "\\GoalAchievementApp";

private static final String DB_PATH =
        DB_FOLDER + "\\goal_app.db";

private static final String URL =
        "jdbc:sqlite:" + DB_PATH;

    // =========================
    // Database初期化
    // =========================

    static {
        System.out.println("=== Database static開始 ===");
        initializeDatabase();
        System.out.println("=== Database static終了 ===");
    }
private static void initializeDatabase() {

    try {

        // DB保存用フォルダを作る
        // フォルダがすでに存在していてもエラーにならない
        Files.createDirectories(Paths.get(DB_FOLDER));

        String createAppData =
            "CREATE TABLE IF NOT EXISTS app_data (" +
            "id INTEGER PRIMARY KEY," +
            "score INTEGER NOT NULL DEFAULT 0," +
            "last_habit_check_date TEXT," +
            "year_goal TEXT," +
            "year_goal_start_date TEXT," +
            "monthly_goal_1 TEXT," +
            "monthly_goal_2 TEXT," +
            "monthly_goal_3 TEXT," +
            "monthly_goal_4 TEXT," +
            "monthly_goal_5 TEXT," +
            "monthly_goal_6 TEXT," +
            "monthly_goal_7 TEXT," +
            "monthly_goal_8 TEXT," +
            "monthly_goal_9 TEXT," +
            "monthly_goal_10 TEXT," +
            "monthly_goal_11 TEXT," +
            "monthly_goal_12 TEXT," +
            "habit_1 TEXT," +
            "habit_2 TEXT," +
            "habit_3 TEXT," +
            "year_goal_checked INTEGER NOT NULL DEFAULT 0," +
            "year_goal_achieved INTEGER NOT NULL DEFAULT 0" +
            ")";

        String createTodos =
            "CREATE TABLE IF NOT EXISTS todos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT NOT NULL," +
            "completed INTEGER NOT NULL DEFAULT 0," +
            "due_date_time TEXT NOT NULL" +
            ")";

        String createHabitRecords =
            "CREATE TABLE IF NOT EXISTS habit_records (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "record_date TEXT NOT NULL UNIQUE," +
            "habit_1_completed INTEGER NOT NULL DEFAULT 0," +
            "habit_2_completed INTEGER NOT NULL DEFAULT 0," +
            "habit_3_completed INTEGER NOT NULL DEFAULT 0" +
            ")";

        String createMonthlyGoalRecords =
            "CREATE TABLE IF NOT EXISTS monthlygoalrecords (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "goal_year INTEGER NOT NULL," +
            "goal_month INTEGER NOT NULL," +
            "achieved INTEGER NOT NULL DEFAULT 0," +
            "checked INTEGER NOT NULL DEFAULT 0," +
            "UNIQUE(goal_year, goal_month)" +
            ")";

        String insertAppData =
            "INSERT OR IGNORE INTO app_data " +
            "(id, score, year_goal_checked, year_goal_achieved) " +
            "VALUES (1, 0, 0, 0)";

        try (Connection connection =
                DriverManager.getConnection(URL)) {

            // =========================
            // ① テーブルを作る
            // =========================

            try (PreparedStatement statement =
                    connection.prepareStatement(createAppData)) {

                statement.executeUpdate();
                System.out.println("app_data table OK");
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(createTodos)) {

                statement.executeUpdate();
                System.out.println("todos table OK");
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(createHabitRecords)) {

                statement.executeUpdate();
                System.out.println("habit_records table OK");
            }

            try (PreparedStatement statement =
                    connection.prepareStatement(createMonthlyGoalRecords)) {

                statement.executeUpdate();
                System.out.println("monthlygoalrecords table OK");
            }

            // =========================
            // ② app_dataの初期データを入れる
            // =========================

            try (PreparedStatement statement =
                    connection.prepareStatement(insertAppData)) {

                statement.executeUpdate();
                System.out.println("app_data record OK");
            }

            System.out.println("SQLite database initialized.");

        }

    } catch (Exception e) {

        System.out.println("=== DATABASE INITIALIZE ERROR ===");
        e.printStackTrace();
    }
}


    // =========================
    // Score
    // =========================

    public static int loadScore() {

        String sql =
            "SELECT score FROM app_data WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {
                return result.getInt("score");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static void saveScore(int score) {

        String sql =
            "UPDATE app_data SET score = ? WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, score);

            int rows =
                statement.executeUpdate();

            System.out.println(
                "保存したscore: " + score
            );

            System.out.println(
                "更新された行数: " + rows
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addScore(int amount) {

        String sql =
            "UPDATE app_data " +
            "SET score = score + ? " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, amount);

            statement.executeUpdate();

            System.out.println(
                "Score updated: " + amount
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================
    // Habit Check Date
    // =========================

    public static LocalDate loadLastHabitCheckDate() {

        String sql =
            "SELECT last_habit_check_date " +
            "FROM app_data WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {

                String dateText =
                    result.getString(
                        "last_habit_check_date"
                    );

                if (dateText != null &&
                    !dateText.isEmpty()) {

                    return LocalDate.parse(dateText);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void saveLastHabitCheckDate(
        LocalDate date) {

        String sql =
            "UPDATE app_data " +
            "SET last_habit_check_date = ? " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                date.toString()
            );

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================
    // Year Goal
    // =========================

    public static void saveYearGoal(
        String yearGoal,
        LocalDate startDate) {

        String sql =
            "UPDATE app_data " +
            "SET year_goal = ?, " +
            "year_goal_start_date = ? " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, yearGoal);
            statement.setString(
                2,
                startDate.toString()
            );

            statement.executeUpdate();

            System.out.println(
                "Year Goal saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String loadYearGoal() {

        String sql =
            "SELECT year_goal " +
            "FROM app_data " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {
                return result.getString(
                    "year_goal"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static boolean isYearGoalReady() {

        String sql =
            "SELECT year_goal_start_date " +
            "FROM app_data " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {

                String dateText =
                    result.getString(
                        "year_goal_start_date"
                    );

                if (dateText == null ||
                    dateText.isEmpty()) {

                    return false;
                }

                LocalDate start =
                    LocalDate.parse(dateText);

                return !LocalDate.now().isBefore(
                    start.plusYears(1)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean isYearGoalChecked() {

        String sql =
            "SELECT year_goal_checked " +
            "FROM app_data " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {

                return result.getBoolean(
                    "year_goal_checked"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public static void saveYearGoalResult(
        boolean achieved) {

        String sql =
            "UPDATE app_data " +
            "SET year_goal_checked = 1, " +
            "year_goal_achieved = ? " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setBoolean(
                1,
                achieved
            );

            statement.executeUpdate();

            System.out.println(
                "Year Goal result saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================
    // Monthly Goals
    // =========================

    public static void saveMonthlyGoals(
        String[] monthlyGoals) {

        String sql =
            "UPDATE app_data SET " +
            "monthly_goal_1 = ?, " +
            "monthly_goal_2 = ?, " +
            "monthly_goal_3 = ?, " +
            "monthly_goal_4 = ?, " +
            "monthly_goal_5 = ?, " +
            "monthly_goal_6 = ?, " +
            "monthly_goal_7 = ?, " +
            "monthly_goal_8 = ?, " +
            "monthly_goal_9 = ?, " +
            "monthly_goal_10 = ?, " +
            "monthly_goal_11 = ?, " +
            "monthly_goal_12 = ? " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            for (int i = 0; i < 12; i++) {
                statement.setString(
                    i + 1,
                    monthlyGoals[i]
                );
            }

            statement.executeUpdate();

            System.out.println(
                "Monthly Goals saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String[] loadMonthlyGoals() {

        String sql =
            "SELECT " +
            "monthly_goal_1, " +
            "monthly_goal_2, " +
            "monthly_goal_3, " +
            "monthly_goal_4, " +
            "monthly_goal_5, " +
            "monthly_goal_6, " +
            "monthly_goal_7, " +
            "monthly_goal_8, " +
            "monthly_goal_9, " +
            "monthly_goal_10, " +
            "monthly_goal_11, " +
            "monthly_goal_12 " +
            "FROM app_data WHERE id = 1";

        String[] monthlyGoals =
            new String[12];

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {

                for (int i = 0; i < 12; i++) {

                    monthlyGoals[i] =
                        result.getString(
                            "monthly_goal_" + (i + 1)
                        );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return monthlyGoals;
    }


    // =========================
    // Habits
    // =========================

    public static void saveHabits(
        String[] habits) {

        String sql =
            "UPDATE app_data SET " +
            "habit_1 = ?, " +
            "habit_2 = ?, " +
            "habit_3 = ? " +
            "WHERE id = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            for (int i = 0; i < 3; i++) {

                statement.setString(
                    i + 1,
                    habits[i]
                );
            }

            statement.executeUpdate();

            System.out.println(
                "Habits saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String[] loadHabits() {

        String sql =
            "SELECT habit_1, habit_2, habit_3 " +
            "FROM app_data WHERE id = 1";

        String[] habits =
            new String[3];

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            if (result.next()) {

                for (int i = 0; i < 3; i++) {

                    habits[i] =
                        result.getString(
                            "habit_" + (i + 1)
                        );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return habits;
    }


    // =========================
    // Todo
    // =========================

    public static void saveTodo(
        TodoItem todo) {

        String sql =
            "INSERT INTO todos " +
            "(title, completed, due_date_time) " +
            "VALUES (?, ?, ?)";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                todo.title
            );

            statement.setBoolean(
                2,
                todo.completed
            );

            statement.setString(
                3,
                todo.dueDateTime.toString()
            );

            statement.executeUpdate();

            System.out.println(
                "Todo saved."
            );

        } catch (Exception e) {

            System.out.println(
                "=== TODO SAVE ERROR ==="
            );

            e.printStackTrace();
        }
    }


    public static ArrayList<TodoItem> loadTodos() {

        String sql =
            "SELECT id, title, completed, due_date_time " +
            "FROM todos ORDER BY id";

        ArrayList<TodoItem> todos =
            new ArrayList<>();

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet result =
                statement.executeQuery()
        ) {

            while (result.next()) {

                TodoItem todo =
                    new TodoItem();

                todo.id =
                    result.getInt("id");

                todo.title =
                    result.getString("title");

                todo.completed =
                    result.getBoolean("completed");

                String dueDateText =
                    result.getString(
                        "due_date_time"
                    );

                if (dueDateText != null) {

                    todo.dueDateTime =
                        java.time.LocalDateTime.parse(
                            dueDateText.replace(
                                " ",
                                "T"
                            )
                        );
                }

                todos.add(todo);
            }

        } catch (Exception e) {

            System.out.println(
                "=== TODO LOAD ERROR ==="
            );

            e.printStackTrace();
        }

        return todos;
    }


    public static void completeTodo(
        int id) {

        String sql =
            "UPDATE todos " +
            "SET completed = 1 " +
            "WHERE id = ?";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println(
                "Todo completed and saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean deleteTodo(
        int id) {

        String sql =
            "DELETE FROM todos WHERE id = ?";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            int rows =
                statement.executeUpdate();

            System.out.println(
                "Todo deleted. ID = " + id
            );

            System.out.println(
                "削除された行数 = " + rows
            );

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    public static int[] loadTodoDailyCount(
        LocalDate date) {

        String sql =
            "SELECT " +
            "COUNT(*) AS total, " +
            "SUM(" +
            "CASE WHEN completed = 1 " +
            "THEN 1 ELSE 0 END" +
            ") AS completed " +
            "FROM todos " +
            "WHERE DATE(due_date_time) = ?";

        int[] result =
            new int[2];

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                date.toString()
            );

            ResultSet rs =
                statement.executeQuery();

            if (rs.next()) {

                result[0] =
                    rs.getInt("total");

                result[1] =
                    rs.getInt("completed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    // =========================
    // Habit Records
    // =========================

    public static void saveHabitRecord(
        LocalDate date,
        boolean habit1Completed,
        boolean habit2Completed,
        boolean habit3Completed) {

        String sql =
            "INSERT INTO habit_records " +
            "(record_date, habit_1_completed, " +
            "habit_2_completed, habit_3_completed) " +
            "VALUES (?, ?, ?, ?) " +
            "ON CONFLICT(record_date) DO UPDATE SET " +
            "habit_1_completed = excluded.habit_1_completed, " +
            "habit_2_completed = excluded.habit_2_completed, " +
            "habit_3_completed = excluded.habit_3_completed";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                date.toString()
            );

            statement.setBoolean(
                2,
                habit1Completed
            );

            statement.setBoolean(
                3,
                habit2Completed
            );

            statement.setBoolean(
                4,
                habit3Completed
            );

            statement.executeUpdate();

            System.out.println(
                "Habit record saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean[] loadHabitRecord(
        LocalDate date) {

        String sql =
            "SELECT habit_1_completed, " +
            "habit_2_completed, " +
            "habit_3_completed " +
            "FROM habit_records " +
            "WHERE record_date = ?";

        boolean[] completed =
            new boolean[3];

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                date.toString()
            );

            ResultSet result =
                statement.executeQuery();

            if (result.next()) {

                completed[0] =
                    result.getBoolean(
                        "habit_1_completed"
                    );

                completed[1] =
                    result.getBoolean(
                        "habit_2_completed"
                    );

                completed[2] =
                    result.getBoolean(
                        "habit_3_completed"
                    );

            } else {

                return null;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return completed;
    }


    public static double[] loadHabitMonthlyRates(
        int year,
        int month) {

        String sql =
            "SELECT " +
            "SUM(habit_1_completed) AS habit1, " +
            "SUM(habit_2_completed) AS habit2, " +
            "SUM(habit_3_completed) AS habit3 " +
            "FROM habit_records " +
            "WHERE strftime('%Y', record_date) = ? " +
            "AND strftime('%m', record_date) = ?";

        double[] rates =
            new double[3];

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                String.format(
                    "%04d",
                    year
                )
            );

            statement.setString(
                2,
                String.format(
                    "%02d",
                    month
                )
            );

            ResultSet result =
                statement.executeQuery();

            if (result.next()) {

                int habit1 =
                    result.getInt("habit1");

                int habit2 =
                    result.getInt("habit2");

                int habit3 =
                    result.getInt("habit3");

                int days =
                    java.time.YearMonth
                        .of(year, month)
                        .lengthOfMonth();

                rates[0] =
                    (double) habit1 / days;

                rates[1] =
                    (double) habit2 / days;

                rates[2] =
                    (double) habit3 / days;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rates;
    }


    // =========================
    // Monthly Goal Records
    // =========================

    public static void saveMonthlyGoalRecord(
        int year,
        int month,
        boolean achieved) {

        String sql =
            "INSERT INTO monthlygoalrecords " +
            "(goal_year, goal_month, achieved, checked) " +
            "VALUES (?, ?, ?, ?) " +
            "ON CONFLICT(goal_year, goal_month) " +
            "DO UPDATE SET " +
            "achieved = excluded.achieved, " +
            "checked = 1";

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, year);
            statement.setInt(2, month);
            statement.setBoolean(3, achieved);
            statement.setBoolean(4, true);

            statement.executeUpdate();

            System.out.println(
                "Monthly goal result saved."
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean[] loadMonthlyGoalRecord(
        int year,
        int month) {

        String sql =
            "SELECT achieved, checked " +
            "FROM monthlygoalrecords " +
            "WHERE goal_year = ? " +
            "AND goal_month = ?";

        boolean[] record =
            new boolean[2];

        try (
            Connection connection =
                DriverManager.getConnection(URL);

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, year);
            statement.setInt(2, month);

            ResultSet result =
                statement.executeQuery();

            if (result.next()) {

                record[0] =
                    result.getBoolean(
                        "achieved"
                    );

                record[1] =
                    result.getBoolean(
                        "checked"
                    );

                return record;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
