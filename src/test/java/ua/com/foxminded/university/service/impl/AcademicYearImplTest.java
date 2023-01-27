package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class AcademicYearImplTest {
    @Autowired
    AcademicYearServiceImpl academicYearService;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> academicYearService.findById(1L));
    }

    @Test
    void shouldVerifyGetSemesterNumber() {
        Integer expected = 6;
        Integer actual = academicYearService.getSemesterNumber(new AcademicYear(3, SemesterType.SPRING_SEMESTER));
        assertEquals(expected, actual);
    }
}
