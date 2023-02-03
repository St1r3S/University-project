package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.repository.AcademicYearRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {AcademicYearServiceImpl.class})
class AcademicYearImplTest {
    @Autowired
    AcademicYearServiceImpl academicYearService;
    @MockBean
    AcademicYearRepository academicYearRepository;

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
