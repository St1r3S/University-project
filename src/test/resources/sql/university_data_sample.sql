INSERT INTO weekly_schedule(week_number)
VALUES (1);
INSERT INTO weekly_schedule(week_number)
VALUES (2);

INSERT INTO daily_schedule(date_of_schedule_cell, day_of_week, weekly_schedule_id)
VALUES ('2022-09-01', 'THURSDAY', 1);
INSERT INTO daily_schedule(date_of_schedule_cell, day_of_week, weekly_schedule_id)
VALUES ('2022-09-08', 'THURSDAY', 2);

INSERT INTO educator(first_name, last_name, birthday, email, weekly_schedule_id, user_role, educator_position)
VALUES ('John', 'Grant', '1978-03-28', 'grant@gmail.com', 1, 'EDUCATOR', 'Lecturer');
INSERT INTO educator(first_name, last_name, birthday, email, weekly_schedule_id, user_role, educator_position)
VALUES ('Alex', 'Test', '1980-03-28', 'alex@gmail.com', 1, 'EDUCATOR', 'Lecturer');

INSERT INTO specialism(specialism_name)
VALUES ('Computer science');
INSERT INTO specialism(specialism_name)
VALUES ('Medicine');

INSERT INTO discipline(discipline_name, educator_id)
VALUES ('OOP', 1);
INSERT INTO discipline(discipline_name, educator_id)
VALUES ('Physics', 1);

INSERT INTO room(room_number)
VALUES ('404');
INSERT INTO room(room_number)
VALUES ('777');

INSERT INTO lecture_number(number, time_start, time_end)
VALUES (1, '08:00:00', '09:35:00');
INSERT INTO lecture_number(number, time_start, time_end)
VALUES (2, '09:50:00', '11:25:00');

INSERT INTO student(first_name, last_name, birthday, email, weekly_schedule_id, user_role, group_name, specialism_id)
VALUES ('Alex', 'Johnson', '2002-05-05', 'a.johny@gmail.com', 1, 'STUDENT', 'NAI-196', 1);
INSERT INTO student(first_name, last_name, birthday, email, weekly_schedule_id, user_role, group_name, specialism_id)
VALUES ('Finn', 'Test', '2002-04-25', 'finn@gmail.com', 1, 'STUDENT', 'AI-195', 2);

INSERT INTO lecture(discipline_id, lecture_number_id, room_id, daily_schedule_id)
VALUES (1, 1, 1, 1);
INSERT INTO lecture(discipline_id, lecture_number_id, room_id, daily_schedule_id)
VALUES (2, 2, 2, 2);

INSERT INTO educator_specialism
VALUES (1, 1);
INSERT INTO educator_specialism
VALUES (2, 2);

INSERT INTO discipline_specialism
VALUES (1, 1);
INSERT INTO discipline_specialism
VALUES (2, 1);

INSERT INTO lecture_student
VALUES (1, 1);
INSERT INTO lecture_student
VALUES (2, 2);
