# Goal Achievement App

A JavaFX desktop application designed to help users manage daily tasks, goals, and habits while tracking their progress through an XP-based scoring system.

---

## 🇯🇵 日本語

### 概要

Goal Achievement Appは、**Todo・目標・習慣を一つのアプリで管理し、日々の達成度をXP（経験値）として記録するデスクトップアプリケーション**です。

Javaを使ったアプリケーション開発の学習と、実際に使用できるポートフォリオの制作を目的として開発しました。

### 主な機能

#### Todo

* 日々のTodoを登録・管理
* 期限日時を設定
* Todoの完了状態を管理
* Todoを完了するとXPを獲得
* 期限切れの未完了Todoを自動処理

#### Goals & Habits

* 年間目標を設定
* 月間目標を設定
* 3つの習慣を登録
* 毎日の習慣達成状況を記録
* 習慣の達成率を表示
* 目標・習慣の達成度をスコアとして管理

#### Statistics

* Todoや習慣などの達成状況を記録
* 今後、統計データやグラフによる可視化を予定

### 使用技術

* Java 21
* JavaFX 21
* FXML
* SQLite
* JDBC
* Scene Builder
* Git / GitHub

### データベース

SQLiteを使用して、アプリケーション内のデータをローカルに保存しています。

主に以下のデータを管理しています。

* Todo
* 習慣の記録
* 月間目標の記録
* アプリケーションデータ

外部のデータベースサーバーを必要とせず、ローカル環境で動作します。

### 開発目的

このプロジェクトでは、Javaの基礎からデスクトップアプリケーション開発までを実践的に学ぶことを目的としました。

開発を通して以下の技術を学習・実践しました。

* オブジェクト指向プログラミング
* Javaのクラス設計
* JavaFXによるGUI開発
* FXMLによるUI設計
* イベント処理
* JDBCによるデータベース接続
* SQLiteによるデータ永続化
* アプリケーションのパッケージ化
* Git / GitHubによるバージョン管理

### 今後の改善予定

* より詳細な統計情報・グラフの追加
* XP・レベルシステムの拡張
* UI / UXの改善
* 習慣管理機能の拡張
* 目標達成状況のより詳細な可視化

---

## 🇺🇸 English

### Overview

Goal Achievement App is a **JavaFX desktop application that combines Todo management, goal setting, and habit tracking while recording daily achievements through an XP-based scoring system.**

The project was developed to strengthen my Java programming skills and create a practical portfolio project.

### Features

#### Todo

* Create and manage daily tasks
* Set due dates and times
* Track task completion
* Earn XP by completing tasks
* Automatically handle unfinished expired tasks

#### Goals & Habits

* Set a yearly goal
* Set monthly goals
* Create and track three daily habits
* Record daily habit completion
* Display habit achievement rates
* Track goal and habit achievement scores

#### Statistics

* Record productivity and achievement data
* Planned expansion with statistics and charts

### Technologies

* Java 21
* JavaFX 21
* FXML
* SQLite
* JDBC
* Scene Builder
* Git / GitHub

### Project Structure

```text
GoalAchievementApp/
├── MainUI.java
├── Main.java
├── MainController.java
├── Todo.java
├── TodoItem.java
├── TodoController.java
├── Goal.java
├── GoalService.java
├── GoalsController.java
├── HabitController.java
├── StatisticsController.java
├── Database.java
└── *.fxml
```

### Database

The application uses SQLite for local data storage.

The database stores application data such as:

* Todo items
* Habit records
* Monthly goal records
* Application data

No external database server is required to run the application.

### Development Purpose

I developed this project to strengthen my understanding of Java programming and desktop application development.

Through this project, I practiced:

* Object-oriented programming
* Java class design
* JavaFX application development
* FXML-based UI design
* Event handling
* Database integration with JDBC
* SQLite data persistence
* Application packaging and distribution
* Git / GitHub version control

### Future Improvements

Planned improvements include:

* More detailed statistics and charts
* Expanded XP and leveling systems
* Improved UI / UX
* Additional habit management features
* More detailed progress visualization

---

## Author

**Shuya Onodera**

GitHub: https://github.com/Shuya-cpu
