create table specialisms
(
    id              bigserial primary key,
    specialism_name text unique not null
);

create table schedule_days
(
    id            bigserial primary key,
    day_of_week   text not null,
    semester_type text not null,
    unique (day_of_week, semester_type)
);

create table rooms
(
    id          bigserial primary key,
    room_number text unique not null
);

create table academic_years
(
    id            bigserial primary key,
    year_number   bigint not null,
    semester_type text   not null,
    unique (year_number, semester_type)
);

create table groupss
(
    id               bigserial primary key,
    group_name       text unique                                                               not null,
    specialism_id    bigint references specialisms (id) on update cascade on delete cascade    not null,
    academic_year_id bigint references academic_years (id) on update cascade on delete cascade not null,
    unique (group_name, specialism_id, academic_year_id)
);

create table users
(
    id               bigserial primary key,
    user_type        bigint      not null,
    user_name        text unique not null,
    password_hash    text        not null,
    user_role        text        not null,
    first_name       text        not null,
    last_name        text        not null,
    birthday         date        not null,
    email            text unique not null,
    academic_rank    text,
    group_id         bigint references groupss (id) on update cascade on delete cascade,
    specialism_id    bigint references specialisms (id) on update cascade on delete cascade,
    academic_year_id bigint references academic_years (id) on update cascade on delete cascade
);

create table disciplines
(
    id               bigserial primary key,
    discipline_name  text                                                                      not null,
    specialism_id    bigint references specialisms (id) on update cascade on delete cascade    not null,
    academic_year_id bigint references academic_years (id) on update cascade on delete cascade not null,
    educator_id      bigint references users (id) on update cascade on delete cascade unique   not null,
    unique (discipline_name, specialism_id, academic_year_id, educator_id)
);

create table lessons
(
    id              bigserial primary key,
    discipline_id   bigint references disciplines (id) on update cascade on delete cascade   not null,
    group_id        bigint references groupss (id) on update cascade on delete cascade       not null,
    lesson_number   bigint                                                                   not null,
    room_id         bigint references rooms (id) on update cascade on delete cascade         not null,
    schedule_day_id bigint references schedule_days (id) on update cascade on delete cascade not null,
    unique (lesson_number, room_id, schedule_day_id)
);