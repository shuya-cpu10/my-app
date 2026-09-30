import java.util.Scanner;
import java.time.LocalDate;
public class GoalService {
    Goal goal = new Goal();
   public void start(Scanner scanner, AppData appData) {

    // Year Goal
    String savedYearGoal = Database.loadYearGoal();

    if (savedYearGoal == null || savedYearGoal.isEmpty()) {
        setyearGoal(scanner);
    } else {
        goal.yearGoal = savedYearGoal;
        System.out.println("Saved Year Goal loaded.");
    }

    // Monthly Goals
    String[] savedMonthlyGoals = Database.loadMonthlyGoals();

    if (savedMonthlyGoals[0] == null) {
        setmonthlyGoals(scanner);
    } else {

        for (int i = 0; i < 12; i++) {
            goal.monthlyGoals[i] = savedMonthlyGoals[i];
        }

        System.out.println("Saved Monthly Goals loaded.");
    }
    // Habits
String[] savedHabits = Database.loadHabits();

if (savedHabits[0] == null) {

    setHabits(scanner);

} else {

    for (int i = 0; i < 3; i++) {
        goal.habits[i] = savedHabits[i];
    }

    System.out.println("Saved Habits loaded.");
}

    showGoals();

    checkYearGoal(scanner, appData);
    checkMonthlyGoal(scanner, appData);
    checkHabits(scanner, appData);
}
    public void setyearGoal(Scanner scanner) {
        System.out.println("Enter your yearly goal:\r\n" + //
                        ">");
        String input = scanner.nextLine();
        goal.yearGoal=input;
        goal.yearGoalStartDate=LocalDate.now();
        Database.saveYearGoal(
        goal.yearGoal,
        goal.yearGoalStartDate
    );
    }

    public void setmonthlyGoals(Scanner scanner) {
        System.out.println("Enter your monthly goal:\r\n" + //
                        ">");
        for(int i=0; i<12; i++){
        System.out.println("Enter month " + (i + 1) + " goal:");
        String input = scanner.nextLine();
        goal.monthlyGoals[i]=input;
        }
         Database.saveMonthlyGoals(goal.monthlyGoals);
    }   

    public void setHabits(Scanner scanner) {
         System.out.println("Enter your three habits in a month:\r\n" + //
                        ">");
         for(int i=0; i<3; i++){
            System.out.println("Enter habit"+(i+1)+":");
            String input =scanner.nextLine();
            goal.habits[i]=input;
         }
         Database.saveHabits(goal.habits);
    }

    public void showGoals() {
        System.out.println("===== Your Goals =====");

        System.out.println("Year Goal:");
        System.out.println(goal.yearGoal);
        System.out.println("Start Date:");
        System.out.println(goal.yearGoalStartDate);

        System.out.println();
        System.out.println("Monthly Goals:");
        for (int i = 0; i < 12; i++) {
            System.out.println((i + 1) + ". " + goal.monthlyGoals[i]);
        }

        System.out.println();
        System.out.println("Habits:");
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + goal.habits[i]);
        }
    }
    public void checkYearGoal(Scanner scanner, AppData appData) {
        LocalDate today = LocalDate.now();
    LocalDate deadline = goal.yearGoalStartDate.plusYears(1);
    if (today.isAfter(deadline)|| today.isEqual(deadline)) {
    System.out.println("Did you achieve your Year Goal?");
    System.out.println("1. Yes");
    System.out.println("2. No");
    int answer = scanner.nextInt();
    if (answer==1) {
    appData.score += 1000;
    System.out.println("Year Goal Achieved!");
    }else{
    System.out.println("Year Goal Not Achieved.");
      }
     }
    }
    public void checkMonthlyGoal(Scanner scanner, AppData appData) {

    LocalDate today = LocalDate.now();

    // 月の最初の日かチェック
    if (today.getDayOfMonth() == 1) {

        // 前月
        int previousMonth = today.getMonthValue() - 1;

        // 1月の場合は前年の12月
        if (previousMonth == 0) {
            previousMonth = 12;
        }

        System.out.println("Did you achieve your Month " + previousMonth + " Goal?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int answer = scanner.nextInt();
        scanner.nextLine();

        if (answer == 1) {
            appData.score += 100;
            System.out.println("Monthly Goal Achieved!");
            System.out.println("+100 points!");
        } else {
            appData.score-=100;
            System.out.println("Monthly Goal Not Achieved.");
            System.out.println("-100 points!");
        }
        }
        }

   public void checkHabits(Scanner scanner, AppData appData) {

    LocalDate today = LocalDate.now();

    // 今日すでにチェックしているか確認
    if (today.equals(appData.lastHabitCheckDate)) {
        System.out.println("Today's habits have already been checked.");
        return;
    }

    System.out.println("===== Today's Habits =====");

    for (int i = 0; i < 3; i++) {

        System.out.println((i + 1) + ". " + goal.habits[i]);
        System.out.println("Did you complete this habit?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int answer = scanner.nextInt();
        scanner.nextLine();

        if (answer == 1) {
            appData.score += 10;
            System.out.println("+10 points!");
        } else if (answer == 2) {
            appData.score -= 20;
            System.out.println("-20 points.");
        } else {
            System.out.println("Invalid choice.");
        }

        System.out.println();
    }

    // 今日チェックしたことを記録
    appData.lastHabitCheckDate = today;
Database.saveLastHabitCheckDate(today);
}  
}   






