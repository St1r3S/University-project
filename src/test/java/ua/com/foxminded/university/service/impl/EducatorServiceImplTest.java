package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.dao.EducatorDao;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.DisciplineService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {EducatorServiceImpl.class})
class EducatorServiceImplTest {
    @Autowired
    EducatorServiceImpl educatorService;
    @MockBean
    EducatorDao educatorDao;
    @MockBean
    DisciplineService disciplineService;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> educatorService.findById(6L));
    }

    @Test
    void shouldVerifyFindAllFreeEducators() {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        AcademicYear academicYearId3 = new AcademicYear(3L, 3, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);
        Educator educatorId7 = new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT);
        Educator educatorId8 = new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT);
        List<Discipline> disciplines = List.of(
                new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5),
                new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6),
                new Discipline(3L, "OOP", specialismId1, academicYearId3, educatorId7)
        );
        List<Educator> educators = List.of(
                educatorId5,
                educatorId6,
                educatorId7,
                educatorId8
        );
        List<Educator> expected = List.of(
                educatorId8
        );

        when(disciplineService.findAll()).thenReturn(disciplines);
        when(educatorDao.findAll()).thenReturn(educators);
        List<Educator> actual = educatorService.findAllFreeEducators();

        assertEquals(expected, actual);
    }
}