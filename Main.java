import java.util.Scanner;

public class Main {
    public static void main (String[] args){
boolean running=true;
AppData appData = new AppData();
appData.score = Database.loadScore();
appData.lastHabitCheckDate =
    Database.loadLastHabitCheckDate();
Scanner scanner = new Scanner(System.in);
    while(running){
System.out.println("===== Goal Achievement App =====");
System.out.println("score"+appData.score);
System.out.println("1. Todo");
System.out.println("2. Goals & Habits");
System.out.println("3. Statistics");
System.out.println("4. Exit"); 
System.out.print("Choose a number: ");
int number = scanner.nextInt();
scanner.nextLine();
switch (number) {
    case 1:
        Todo todo = new Todo();
        todo.start(scanner, appData);
        break;

    case 2:
    GoalService goalService = new GoalService();
    goalService.start(scanner,appData);
    break;

    case 3:
        System.out.println("Statistics");
        break;

    case 4:
        Database.saveScore(appData.score);
        System.out.println("Goodbye!");
        running=false;
        break;

    default:
        System.out.println(
         "Please enter a number from 1 to 4." );}
        }
        scanner.close();
}
}
