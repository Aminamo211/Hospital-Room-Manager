# Hospital Room Manager

A JavaFX desktop application for managing hospital patients, rooms, staff accounts, and patient-to-staff assignments.

This application began as a collaborative team project for the SE370 course. This repository is my personal fork, where I continued improving the database integration and user interface after the team project.

## Features

- Account creation and login
- Patient record management
- Hospital room management
- Patient-to-staff assignments
- Role checks for administrators, nurses, and doctors
- JavaFX graphical user interface
- MySQL database integration
- Environment-variable protection for the database password

## Technologies

- Java
- JavaFX
- FXML
- Maven
- MySQL
- JDBC
- Jakarta Persistence
- Git and GitHub

## My Contributions

During the team project:

- Implemented login authentication using phone numbers and passwords.
- Added role-checking logic for Administrator, Nurse, and Doctor accounts.
- Helped design the application’s security and login workflow.
- Collaborated with team members using Git branches, commits, and merge-conflict resolution.

After the team project, I independently improved my personal fork by:

- Connecting the JavaFX application to MySQL.
- Adding account-creation functionality.
- Adding patient-management and room-management screens.
- Creating JavaFX controllers and FXML layouts.
- Moving the database password into an environment variable.
- Correcting Java filenames and removing generated build files from Git tracking.

## Team Attribution

This repository is a personal fork of the original team project:

[Original SE370 Team Repository](https://github.com/Oliverrr20/SE370-Spring-2026-Team-3)

The original project was developed collaboratively. Its commit history has been preserved to credit all contributors.

## Project Structure

```text
Hospital-Room-Manager/
├── Code/
│   └── team3/
│       ├── pom.xml
│       └── src/
│           └── main/
│               ├── java/
│               │   ├── backend/
│               │   ├── hospital_room_manager/
│               │   └── module-info.java
│               ├── resources/
│               │   └── hospital_room_manager/
│               └── sqlScripts/
├── Journal/
├── Notes/
└── README.md
```

- `backend` contains the database, entity, and management classes.
- `hospital_room_manager` contains the JavaFX application and controllers.
- `resources` contains the FXML interface layouts.
- `sqlScripts` contains the MySQL initialization scripts.
- `pom.xml` contains the Maven configuration and dependencies.

## Prerequisites

Install the following before running the application:

- Java Development Kit (JDK)
- Maven
- MySQL Community Server
- IntelliJ IDEA or Visual Studio Code

## Database Setup

### 1. Start MySQL

Install and start [MySQL Community Server](https://dev.mysql.com/downloads/mysql/).

The MySQL root password is used only to administer MySQL. The application uses a separate account named `hospital_app`.

### 2. Open MySQL

On macOS, open Terminal and run:

```bash
/usr/local/mysql/bin/mysql -u root -p
```

Enter your MySQL root password when prompted.

Do not enter SQL commands directly into the regular Terminal prompt. Wait until you see:

```text
mysql>
```

### 3. Create the database and application user

At the `mysql>` prompt, run:

```sql
CREATE DATABASE IF NOT EXISTS hospitalroom;

CREATE USER IF NOT EXISTS 'hospital_app'@'localhost'
IDENTIFIED BY '<your-private-app-password>';

GRANT ALL PRIVILEGES
ON hospitalroom.*
TO 'hospital_app'@'localhost';

FLUSH PRIVILEGES;
```

Replace `<your-private-app-password>` with a password used only for your local application.

Do not put your real password in this README or commit it to GitHub.

### 4. Select the database

```sql
USE hospitalroom;
```

Confirm the selected database:

```sql
SELECT DATABASE();
```

The result should show:

```text
hospitalroom
```

### 5. Create the tables

Run the SQL initialization scripts located in:

```text
Code/team3/src/main/sqlScripts
```

Run each initialization script only once unless the script is designed to be safely repeated.

Check the created tables with:

```sql
SHOW TABLES;
```

## Database Password Configuration

The application reads the database password from an environment variable named:

```text
HOSPITAL_DB_PASSWORD
```

The value must be the password you created for the `hospital_app` MySQL user—not your Mac password and not necessarily your MySQL root password.

### IntelliJ IDEA

1. Open **Run → Edit Configurations**.
2. Select the application run configuration.
3. Find **Environment variables**.
4. Add:

   ```text
   HOSPITAL_DB_PASSWORD=your-private-app-password
   ```

5. Replace `your-private-app-password` with your actual local application password.
6. Click **Apply** and then **Run**.

Do not select **Store as project file**, because that could save the password inside the repository.

### Terminal

On macOS or Linux, set the variable for the current Terminal session:

```bash
export HOSPITAL_DB_PASSWORD='your-private-app-password'
```

Then run the application from that same Terminal window.

## Configuring SQLTools

Create a MySQL connection with these settings:

```text
Connection name: Hospital Room Manager
Server: localhost
Port: 3306
Database: hospitalroom
Username: hospital_app
Password mode: Ask on connect
```

When SQLTools asks for a password, enter the private password created for `hospital_app`.

Test the connection before saving it.

## Running with IntelliJ IDEA

1. Clone or download this repository.
2. Open the following directory as the IntelliJ project:

   ```text
   Code/team3
   ```

3. Right-click `pom.xml`.
4. Select **Add as Maven Project**.
5. Wait for IntelliJ to download and import the dependencies.
6. Configure `HOSPITAL_DB_PASSWORD` in the run configuration.
7. Open **View → Tool Windows → Maven**.
8. Expand **Plugins → javafx**.
9. Double-click `javafx:run`.

The JavaFX application entry point is:

```text
Code/team3/src/main/java/hospital_room_manager/App.java
```

## Running from the Terminal

Open Terminal in the repository and run:

```bash
cd Code/team3
export HOSPITAL_DB_PASSWORD='your-private-app-password'
mvn clean javafx:run
```

If `mvn` is not recognized, install Maven or use IntelliJ’s Maven tool window.

## Visual Studio Code Setup

Install these extensions:

- Extension Pack for Java
- SQLTools
- SQLTools MySQL/MariaDB/TiDB Driver

Open `Code/team3` as the project directory and allow Visual Studio Code to import the Maven project.

## Troubleshooting

### `JAVA_HOME` is not configured

On macOS, add the following to `~/.zshrc`:

```bash
export JAVA_HOME=$(/usr/libexec/java_home)
export PATH="$JAVA_HOME/bin:$PATH"
```

Restart Terminal and verify Java:

```bash
java -version
```

On Windows:

1. Open **Environment Variables**.
2. Create `JAVA_HOME` using your JDK installation path.
3. Add `%JAVA_HOME%\bin` to `Path`.
4. Restart the IDE and Terminal.

### `mvn: command not found`

Install Maven and verify it:

```bash
mvn -version
```

Alternatively, run the project through IntelliJ’s Maven tool window.

### Database connection failed

Confirm that:

- MySQL Server is running.
- The database is named `hospitalroom`.
- The MySQL user is `hospital_app`.
- `HOSPITAL_DB_PASSWORD` contains the correct application-user password.
- The application is connecting to `localhost` on port `3306`.
- The `hospital_app` user has permission to access `hospitalroom`.

Test the account directly:

```bash
/usr/local/mysql/bin/mysql -u hospital_app -p hospitalroom
```

Enter the application-user password when prompted.

### SQLTools cannot find a MySQL driver

Install the **SQLTools MySQL/MariaDB/TiDB Driver**, restart Visual Studio Code, and test the connection again.

### JavaFX native-access warning

A warning about restricted native access may appear when JavaFX starts. If the application opens normally, this is a warning rather than a database or login failure.

## Security Notes

- Never commit real passwords to GitHub.
- Do not place a password directly inside Java source code.
- Keep `HOSPITAL_DB_PASSWORD` in your local run configuration or Terminal environment.
- Use separate MySQL root and application accounts.
- Change any password immediately if it is accidentally published.

## Project Status

This is an academic team project that I continued improving in my personal fork. Additional testing and development may still be required for some features.* Added role-checking logic for Administrator, Nurse, and Doctor accounts.
* Helped design the application’s security and login workflow.
* Collaborated with team members through Git branches, commits, and merge-conflict resolution.


## Team Attribution

This repository is a personal fork of the original team project:

[Original SE370 Team Repository](https://github.com/Oliverrr20/SE370-Spring-2026-Team-3)

The project was developed collaboratively. The original commit history has been preserved to credit all contributors.

## Project Structure

```text
SE370-Spring-2026-Team-3/
├── Code/
│   └── team3/
│       ├── pom.xml
│       └── src/
│           └── main/
│               ├── java/
│               │   ├── backend/
│               │   └── hospital_room_manager/
│               └── resources/
├── Journal/
├── Notes/
└── README.md
```

* `backend` contains the entity and management classes.
* `hospital_room_manager` contains the JavaFX application and controllers.
* `resources` contains the FXML layouts and other application resources.
* `pom.xml` contains the Maven configuration and dependencies.

## Prerequisites

Install the following before running the application:

* Java Development Kit (JDK)
* Maven
* MySQL Community Server
* IntelliJ IDEA or Visual Studio Code

## Running with IntelliJ IDEA

1. Clone or download this repository.

2. Open the following directory as an IntelliJ project:

   ```text
   Code/team3
   ```

3. Right-click `pom.xml`.

4. Select **Add as Maven Project**.

5. Wait for IntelliJ to import the Maven dependencies.

6. Open **View → Tool Windows → Maven**.

7. Expand **Plugins → javafx**.

8. Double-click `javafx:run`.

The JavaFX application entry point is:

```text
Code/team3/src/main/java/hospital_room_manager/App.java
```

## Running from the Terminal

Open a terminal in the repository and enter:

```bash
cd Code/team3
mvn clean javafx:run
```

If `mvn` is not recognized, install Maven or use IntelliJ’s bundled Maven support.

## Visual Studio Code Setup

Install the following extensions:

* Extension Pack for Java
* SQLTools
* SQLTools MySQL/MariaDB/TiDB Driver

Open `Code/team3` as the project directory and allow Visual Studio Code to import the Maven project.

## Database Setup

1. Download and install [MySQL Community Server](https://dev.mysql.com/downloads/mysql/).

2. Choose a secure local password during installation.

3. Open the MySQL command-line client.

4. Create the application database:

   ```sql
   CREATE DATABASE se370team3;
   ```

5. Create a local application user:

   ```sql
   CREATE USER 'admin'@'localhost'
   IDENTIFIED BY '<your-local-password>';

   GRANT ALL PRIVILEGES
   ON se370team3.*
   TO 'admin'@'localhost';

   FLUSH PRIVILEGES;
   ```

6. Replace `<your-local-password>` with a password used only in your local development environment.

7. Run the project’s SQL initialization scripts in their documented order.

Do not commit real database passwords or other credentials to GitHub.

## Configuring SQLTools

Create a MySQL connection using:

```text
Connection name: Hospital Room Manager
Server: localhost
Port: 3306
Database: se370team3
Username: admin
Password mode: Ask on connect
```

Test the connection before saving it.

## Troubleshooting

### `JAVA_HOME` is not configured

On macOS, add the following to `~/.zshrc`:

```bash
export JAVA_HOME=$(/usr/libexec/java_home)
export PATH="$JAVA_HOME/bin:$PATH"
```

Restart the terminal and verify Java:

```bash
java -version
```

On Windows:

1. Open **Environment Variables**.
2. Create `JAVA_HOME` using the JDK installation path.
3. Add `%JAVA_HOME%\bin` to `Path`.
4. Restart the IDE and terminal.

### `mvn: command not found`

Install Maven and verify it with:

```bash
mvn -version
```

Alternatively, run the project through IntelliJ’s Maven tool window.

### SQLTools cannot find a MySQL driver

Install the **SQLTools MySQL/MariaDB/TiDB Driver**, restart Visual Studio Code, and test the connection again.

## Project Status

This is an academic team project and remains a work in progress. Some functionality may require additional configuration or development before it runs completely.


