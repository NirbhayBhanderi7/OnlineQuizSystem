ONLINE QUIZ SYSTEM - APACHE NETBEANS / TOMCAT 8 VERSION

This ZIP is a Maven Web Application and is designed to open directly in Apache NetBeans.

REQUIREMENTS
- Apache NetBeans (Maven support enabled; current NetBeans versions include it)
- JDK 8 or newer
- Apache Tomcat 8.x
- MySQL / XAMPP

HOW TO OPEN
1. Extract this ZIP completely.
2. Open Apache NetBeans.
3. File -> Open Project.
4. Select the OnlineQuizSystem folder that contains pom.xml.
5. NetBeans will recognize it as a Maven project.
6. Right-click the project -> Clean and Build.
7. Configure Tomcat 8.x as the server and run.

DATABASE
1. Start MySQL in XAMPP.
2. Open Database/online_quiz_db.sql in phpMyAdmin/MySQL and run it.
3. Edit src/main/java/com/onlinequiz/util/DBConnection.java.
4. Change USER and PASSWORD to your MySQL credentials.

DEFAULT ADMIN
Username: admin
Password: admin123

IMPORTANT
This application uses javax.servlet.* and is configured for Tomcat 8.x.
Do not use Tomcat 9/10/11 with this exact project. It is intended for Tomcat 8.x.

MAVEN
The MySQL JDBC driver and Javax Servlet API are declared in pom.xml, so you do NOT need to manually add a connector JAR in NetBeans. Maven downloads them when you build the project.


DATABASE NOTE
For a default XAMPP MySQL installation, DBConnection.java is configured with USER=root and an EMPTY password. If your MySQL root account has a password, change PASSWORD accordingly.


MAIN ROUTES
- Home: /index.jsp
- Student Login: /login.jsp
- Student Registration: /register.jsp
- Student Dashboard: /dashboard
- Quiz: /quiz?quizId=1
- Leaderboard: /leaderboard?quizId=1
- Admin Login: /admin/login.jsp
- Admin Dashboard: /adminDashboard
- Create Quiz: /admin/createQuiz.jsp
- Add Question: /admin/addQuestion.jsp?quizId=1
- Students: /viewStudents
- Results: /viewResults

CRUD / DATABASE PRACTICAL
- CREATE/INSERT: Student registration and quiz/question creation
- READ: Student, quiz, question and result retrieval
- UPDATE: Quiz title/description/duration and automatic question-count update
- DELETE: Student and quiz deletion (with database foreign-key cascades)
- JDBC test: com.onlinequiz.util.JDBCConnectionTest

IMPORTANT FOR THE ADMIN DASHBOARD
Always open /adminDashboard after login. Do not open /admin/dashboard.jsp directly; the servlet loads the dashboard attributes before forwarding to the JSP.
