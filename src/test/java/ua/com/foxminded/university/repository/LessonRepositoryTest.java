package ua.com.foxminded.university.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.model.lesson.*;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.user.UserRole;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LessonRepositoryTest {
    @Autowired
    LessonRepository repository;

    @Test
    void shouldVerifyRepositoryInjected() {
        assertNotNull(repository);
    }

    @Test
    void shouldVerifyFindAllByDisciplineId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> expected = List.of(
                new Lesson(2L, disciplineId2, groupId1, LessonNumber.SECOND_LESSON, roomId2, scheduleDayId1)
        );

        List<Lesson> actual = repository.findAllByDisciplineId(2L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByGroupId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Room roomId3 = new Room(3L, "606");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> expected = List.of(
                new Lesson(3L, disciplineId1, groupId2, LessonNumber.FIRST_LESSON, roomId3, scheduleDayId1)
        );

        List<Lesson> actual = repository.findAllByGroupId(2L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByLessonNumber() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> expected = List.of(
                new Lesson(2L, disciplineId2, groupId1, LessonNumber.SECOND_LESSON, roomId2, scheduleDayId1)
        );

        List<Lesson> actual = repository.findAllByLessonNumber(LessonNumber.SECOND_LESSON);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByRoomId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> expected = List.of(
                new Lesson(2L, disciplineId2, groupId1, LessonNumber.SECOND_LESSON, roomId2, scheduleDayId1)
        );

        List<Lesson> actual = repository.findAllByRoomId(2L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllByScheduleDayId() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        Room roomId2 = new Room(2L, "505");
        Room roomId3 = new Room(3L, "606");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> expected = List.of(
                new Lesson(1L, disciplineId1, groupId1, LessonNumber.FIRST_LESSON, roomId1, scheduleDayId1),
                new Lesson(2L, disciplineId2, groupId1, LessonNumber.SECOND_LESSON, roomId2, scheduleDayId1),
                new Lesson(3L, disciplineId1, groupId2, LessonNumber.FIRST_LESSON, roomId3, scheduleDayId1)
        );

        List<Lesson> actual = repository.findAllByScheduleDayId(1L);

        assertEquals(expected, actual);
    }
}