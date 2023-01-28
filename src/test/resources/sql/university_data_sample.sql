INSERT INTO specialisms(specialism_name)
VALUES ('122');
INSERT INTO specialisms(specialism_name)
VALUES ('125');
INSERT INTO specialisms(specialism_name)
VALUES ('127');

INSERT INTO schedule_days(day_of_week, semester_type)
VALUES ('Monday', 'fall');
INSERT INTO schedule_days(day_of_week, semester_type)
VALUES ('Tuesday', 'fall');
INSERT INTO schedule_days(day_of_week, semester_type)
VALUES ('Wednesday', 'fall');

INSERT INTO rooms(room_number)
VALUES ('404');
INSERT INTO rooms(room_number)
VALUES ('505');
INSERT INTO rooms(room_number)
VALUES ('606');

INSERT INTO academic_years(year_number, semester_type)
VALUES (1, 'fall');
INSERT INTO academic_years(year_number, semester_type)
VALUES (2, 'fall');
INSERT INTO academic_years(year_number, semester_type)
VALUES (3, 'fall');

INSERT INTO groupss(group_name, specialism_id, academic_year_id)
VALUES ('AI-193', 1, 1);
INSERT INTO groupss(group_name, specialism_id, academic_year_id)
VALUES ('AI-194', 1, 1);
INSERT INTO groupss(group_name, specialism_id, academic_year_id)
VALUES ('AI-195', 1, 1);

INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email)
VALUES (1, 'maccas82', 'pass82', 'Admin', 'Michael', 'Maccas', '1982-06-22', 'maccas82@gmail.com');
INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email)
VALUES (1, 'sobs85', 'pass85', 'Staff', 'Jack', 'Sobs', '1985-04-17', 'sobs85@gmail.com');

INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email, group_id,
                  specialism_id, academic_year_id)
VALUES (2, 'johny05', 'pass05', 'Student', 'Alex', 'Johnson', '2002-05-05', 'a.johny@gmail.com', 1, 1, 1);
INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email, group_id,
                  specialism_id, academic_year_id)
VALUES (2, 'finn25', 'pass25', 'Student', 'Finn', 'Chikson', '2002-04-25', 'finn@gmail.com', 2, 1, 1);

INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email, academic_rank)
VALUES (3, 'grant78', 'pass78', 'Educator', 'John', 'Grant', '1978-03-28', 'grant@gmail.com', 'Professor');
INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email, academic_rank)
VALUES (3, 'jimm80', 'pass80', 'Educator', 'Alex', 'Jimson', '1980-03-24', 'alex@gmail.com', 'Docent');
INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email, academic_rank)
VALUES (3, 'spy74', 'pass74', 'Educator', 'Feder', 'Lops', '1974-05-25', 'spy@gmail.com', 'Docent');
INSERT INTO users(user_type, user_name, password_hash, user_role, first_name, last_name, birthday, email, academic_rank)
VALUES (3, 'kox55', 'pass55', 'Educator', 'Semen', 'Dowson', '1955-06-22', 'dowson@gmail.com', 'Docent');

INSERT INTO disciplines(discipline_name, specialism_id, academic_year_id, educator_id)
VALUES ('Math', 1, 1, 5);
INSERT INTO disciplines(discipline_name, specialism_id, academic_year_id, educator_id)
VALUES ('Physics', 1, 1, 6);
INSERT INTO disciplines(discipline_name, specialism_id, academic_year_id, educator_id)
VALUES ('OOP', 1, 3, 7);

INSERT INTO lessons(discipline_id, group_id, lesson_number, room_id, schedule_day_id)
VALUES (1, 1, 1, 1, 1);
INSERT INTO lessons(discipline_id, group_id, lesson_number, room_id, schedule_day_id)
VALUES (2, 1, 2, 2, 1);
INSERT INTO lessons(discipline_id, group_id, lesson_number, room_id, schedule_day_id)
VALUES (1, 2, 1, 3, 1);