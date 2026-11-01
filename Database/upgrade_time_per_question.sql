USE online_quiz_db;

ALTER TABLE quizzes
    ADD COLUMN time_per_question_minutes INT NOT NULL DEFAULT 1;

UPDATE quizzes
SET duration = total_questions * time_per_question_minutes;
