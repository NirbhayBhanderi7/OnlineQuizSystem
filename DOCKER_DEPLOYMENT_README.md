# Deploying OnlineQuizSystem with Docker (Render)

This project uses JSP/Servlets with the `javax.servlet` API. The Dockerfile therefore uses Tomcat 9 rather than Tomcat 10/11.

## What the Dockerfile does
1. Builds `OnlineQuizSystem.war` using Maven and Java 8.
2. Copies the WAR to Tomcat's ROOT application so the app opens at the service root URL.
3. Runs Tomcat on port 8080 (Render detects the exposed web service port).

## Before deployment
- Push the project folder containing `pom.xml`, `src`, `Dockerfile`, and `.dockerignore` to GitHub.
- Create a MySQL database that your hosted app can reach.
- Import the schema from `Database/online_quiz_db.sql` into a NEW database or carefully merge the schema into an existing database. Do not blindly import over an existing database: SQL scripts can contain DROP TABLE statements.
- Add these environment variables in your hosting dashboard:
  - `DB_URL`: full JDBC URL, e.g. `jdbc:mysql://YOUR_DB_HOST:3306/YOUR_DB_NAME?useSSL=true&serverTimezone=UTC`
  - `DB_USER`: database username
  - `DB_PASSWORD`: database password

The database host must allow connections from the application host. Use the exact JDBC URL and SSL requirements supplied by your database provider.

## Render steps
1. Push the project to GitHub.
2. In Render, choose New > Web Service and connect the repository.
3. Select Docker as the runtime/environment if asked.
4. Set the Dockerfile path to the path of this Dockerfile (normally `./Dockerfile` if the project root is the repository root).
5. Add the three environment variables above.
6. Deploy, then inspect the build and runtime logs.

## Important
- This Dockerfile is a deployment starting point; it has not been tested against your hosting account or remote database.
- Render's free web services can sleep when idle. Database hosting and free-plan availability/limits are separate and can change.
- Never commit real database passwords to GitHub.
