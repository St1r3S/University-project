package ua.com.foxminded.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.university.dao.LessonDao;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        HibernateLessonDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/university_data_clean.sql", "/sql/university_data_sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HibernateLessonDaoTest {
    @Autowired
    LessonDao dao;

    @Test
    void shouldVerifyDaoInjected() {
        assertNotNull(dao);
    }

    @Test
    void shouldVerifyCreate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId2 = new ScheduleDay(2L, DayOfWeek.TUESDAY, SemesterType.FALL_SEMESTER);

        Lesson expected = new Lesson(disciplineId2, groupId2, LessonNumber.SECOND_LESSON, roomId1, scheduleDayId2);

        dao.save(expected);
        Optional<Lesson> actual = dao.findById(4L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyUpdate() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        Lesson expected = new Lesson(1L, disciplineId1, groupId1, LessonNumber.FIFTH_LESSON, roomId1, scheduleDayId1);

        dao.save(expected);
        Optional<Lesson> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyFindById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        Lesson expected = new Lesson(1L, disciplineId1, groupId1, LessonNumber.FIRST_LESSON, roomId1, scheduleDayId1);

        dao.save(expected);
        Optional<Lesson> actual = dao.findById(1L);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void shouldVerifyIsExistById() {
        assertTrue(dao.existsById(1L));
    }

    @Test
    void shouldVerifyFindAll() {
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

        List<Lesson> actual = dao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyFindAllById() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> expected = List.of(
                new Lesson(1L, disciplineId1, groupId1, LessonNumber.FIRST_LESSON, roomId1, scheduleDayId1),
                new Lesson(2L, disciplineId2, groupId1, LessonNumber.SECOND_LESSON, roomId2, scheduleDayId1)
        );

        List<Lesson> actual = dao.findAllById(List.of(1L, 2L));

        assertEquals(expected, actual);
    }

    @Test
    void shouldVerifyCount() {
        long actual = dao.count();
        assertEquals(3L, actual);
    }

    @Test
    void shouldVerifyDeleteById() {
        assertDoesNotThrow(() -> dao.deleteById(1L));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteByEntity() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        Lesson entity = new Lesson(1L, disciplineId1, groupId1, LessonNumber.FIRST_LESSON, roomId1, scheduleDayId1);

        assertDoesNotThrow(() -> dao.delete(entity));
        assertFalse(dao.findById(1L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllById() {
        assertDoesNotThrow(() -> dao.deleteAllById(List.of(1L, 2L)));
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAllByEntities() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<Lesson> entities = List.of(
                new Lesson(1L, disciplineId1, groupId1, LessonNumber.FIRST_LESSON, roomId1, scheduleDayId1),
                new Lesson(2L, disciplineId2, groupId1, LessonNumber.SECOND_LESSON, roomId2, scheduleDayId1)
        );

        assertDoesNotThrow(() -> dao.deleteAll(entities));
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
    }

    @Test
    void shouldVerifyDeleteAll() {
        assertDoesNotThrow(() -> dao.deleteAll());
        assertFalse(dao.findById(1L).isPresent());
        assertFalse(dao.findById(2L).isPresent());
        assertFalse(dao.findById(3L).isPresent());
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

        List<Lesson> actual = dao.findAllByDisciplineId(2L);

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

        List<Lesson> actual = dao.findAllByGroupId(2L);

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

        List<Lesson> actual = dao.findAllByLessonNumber(LessonNumber.SECOND_LESSON);

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

        List<Lesson> actual = dao.findAllByRoomId(2L);

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

        List<Lesson> actual = dao.findAllByScheduleDayId(1L);

        assertEquals(expected, actual);
    }
}