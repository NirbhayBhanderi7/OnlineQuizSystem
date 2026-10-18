# Online Quiz System — Changes and Test Guide

## Implemented
- Refreshed the interface with a focused online-exam/MCQ theme: academic blue accents, multiple-choice answer markers, and clearer question/timer styling.
- Removed the Home navigation link for authenticated students; student navigation still includes Available Quizzes, Leaderboard, and Logout. The public/guest Home link remains unchanged.
- Removed the standalone Edit Questions action from the admin quiz list; question editing is now available at the bottom of the Edit Quiz page.
- Added a vertical question-number list at the top of Add Question, showing existing question numbers and the next question number.
- Added a required `Time per Question (minutes)` field on Create Quiz; it defaults to 1.
- Saved per-question time in `quizzes.time_per_question_minutes`.
- Automatically recalculated total quiz duration as `total_questions * time_per_question_minutes` whenever a question is added or the per-question time is changed.
- Added an admin question editor at `manageQuestions?quizId=...`, with a sequential clickable question navigator and dropdown to select any question in the quiz and edit its text, marks, four options, and correct answer. Display numbers are per-quiz sequence numbers (1 through the number of questions), not database IDs.
- Question edits update the existing question record and replace its options in a single database transaction; the question itself is not duplicated.
- Protected question-management routes with the admin authentication filter.
- Preserved the 30-minute normal session timeout, extended the inactivity timeout while a timed quiz is active so it does not expire before the quiz deadline, and restored 30 minutes after submission.
- Added server-side elapsed-time checking with a 15-second network/submission grace window. Submissions after that window are scored without accepting submitted answers.
- Added no-cache headers for protected student/admin pages and retained logout session invalidation.

## Database setup

### New installation
Import `Database/online_quiz_db.sql` into MySQL. **This setup script drops and recreates the project's tables**, so do not run it on a database whose data must be preserved.

### Existing installation
Run `Database/upgrade_time_per_question.sql` once against `online_quiz_db`. It adds the new column and recalculates existing quiz durations. Back up the database first. The migration is intended to run once; it will fail if the new column already exists.

## Manual functional test checklist
1. Start MySQL and Tomcat 8, deploy `OnlineQuizSystem.war`, and sign in as admin.
2. Create a quiz with `Time per Question = 2`; add 3 questions. The admin dashboard should show 6 minutes total and 2 minutes per question.
3. Open **Edit Quiz**, change per-question time to 3, save, and confirm total time recalculates to 9 minutes.
4. From the admin dashboard, click **Edit Quiz** for a quiz. Update its title/description/timing if needed, then use the **Edit Questions** button at the bottom of that page. Confirm the question navigator shows 1–5 for a five-question quiz, 1–10 for a ten-question quiz, and 1–30 for a thirty-question quiz. Select a number or dropdown item, edit the question/options/correct answer, save, reload, and confirm the values remain saved.
5. Sign out and sign in as a student. Confirm the quiz countdown starts at the calculated total duration.
6. Try opening `/dashboard`, `/quiz?quizId=...`, `/leaderboard`, `/result.jsp`, and `/submitQuiz` without a student session; each protected route should redirect to student login.
7. Try opening admin pages/actions as a student; they should redirect to admin login.
8. Sign out and use browser Back; protected content should not be served from cache.
9. Leave a quiz running past its deadline and submit; the server should not accept late answers beyond the 15-second grace period.

## Test status
Static source/schema checks are performed during packaging. A live Tomcat + MySQL end-to-end test was not run in this build environment because Maven, MySQL client/server, and Tomcat are not installed here. Complete the manual checklist in the target environment before production use.
