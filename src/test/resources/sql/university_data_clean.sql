TRUNCATE TABLE specialisms CASCADE;
TRUNCATE TABLE schedule_days CASCADE;
TRUNCATE TABLE rooms CASCADE;
TRUNCATE TABLE academic_years CASCADE;
TRUNCATE TABLE disciplines CASCADE;
TRUNCATE TABLE groupss CASCADE;
TRUNCATE TABLE lessons CASCADE;
TRUNCATE TABLE users CASCADE;
ALTER SEQUENCE specialisms_id_seq RESTART;
ALTER SEQUENCE schedule_days_id_seq RESTART;
ALTER SEQUENCE rooms_id_seq RESTART;
ALTER SEQUENCE academic_years_id_seq RESTART;
ALTER SEQUENCE disciplines_id_seq RESTART;
ALTER SEQUENCE groupss_id_seq RESTART;
ALTER SEQUENCE lessons_id_seq RESTART;
ALTER SEQUENCE users_id_seq RESTART;
