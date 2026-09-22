# Hospital Room Manager

A desktop application for managing hospital patients, rooms, staff accounts, and patient-to-staff assignments.

This application was developed collaboratively as a team project for the SE370 course.

## Features

* Patient account and record management
* Hospital room management
* Patient-to-staff assignments
* Login authentication
* Role checks for administrators, nurses, and doctors
* JavaFX user interface
* MySQL database integration

## Technologies

* Java
* JavaFX
* FXML
* Maven
* MySQL
* Jakarta Persistence
* Git and GitHub

## My Contributions

* Implemented login authentication logic for client accounts using phone numbers and passwords.
* Added role-checking logic for Administrator, Nurse, and Doctor accounts.
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
