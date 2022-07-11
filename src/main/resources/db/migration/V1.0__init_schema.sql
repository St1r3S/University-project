create table weekly_schedule
(
    id          bigserial primary key,
    week_number int not null
);

create table educator
(
    id                 bigserial primary key,
    first_name         text                                   not null,
    last_name          text                                   not null,
    birthday           date                                   not null,
    email              text unique                            not null,
    weekly_schedule_id bigint references weekly_schedule (id) not null,
    user_role          text                                   not null,
    educator_position  text                                   not null
);

create table discipline
(
    id              bigserial primary key,
    discipline_name text                            not null,
    educator_id     bigint references educator (id) not null,
    unique (discipline_name, educator_id)
);

create table room
(
    id          bigserial primary key,
    room_number text unique not null
);

create table lecture_number
(
    id         bigserial primary key,
    number     int  not null,
    time_start time not null,
    time_end   time not null
);

create table specialism
(
    id              bigserial primary key,
    specialism_name text unique not null
);

create table daily_schedule
(
    id                 bigserial primary key,
    day_of_week        text                                   not null,
    weekly_schedule_id bigint references weekly_schedule (id) not null,
    unique (day_of_week, weekly_schedule_id)
);

create table educator_specialism
(
    educator_id   bigint references educator (id) on update cascade on delete cascade   not null,
    specialism_id bigint references specialism (id) on update cascade on delete cascade not null,
    constraint educator_specialism_pk primary key (educator_id, specialism_id)
);

create table discipline_specialism
(
    discipline_id bigint references discipline (id) on update cascade on delete cascade not null,
    specialism_id bigint references specialism (id) on update cascade on delete cascade not null,
    constraint discipline_specialism_pk primary key (discipline_id, specialism_id)
);

create table lecture
(
    id                bigserial primary key,
    discipline_id     bigint references weekly_schedule (id) not null,
    lecture_number_id bigint references lecture_number (id)  not null,
    room_id           bigint references room (id)            not null,
    daily_schedule_id bigint references daily_schedule (id)  not null
);

create table student
(
    id                 bigserial primary key,
    first_name         text                                   not null,
    last_name          text                                   not null,
    birthday           date                                   not null,
    email              text unique                            not null,
    weekly_schedule_id bigint references weekly_schedule (id) not null,
    user_role          text                                   not null,
    group_name         text                                   not null,
    specialism_id      bigint references specialism (id)      not null
);

create table lecture_student
(
    lecture_id bigint references lecture (id) on update cascade on delete cascade not null,
    student_id bigint references student (id) on update cascade on delete cascade not null,
    constraint lecture_student_pk primary key (lecture_id, student_id)
);