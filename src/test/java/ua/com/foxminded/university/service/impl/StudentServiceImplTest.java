package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.dao.StudentDao;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {StudentServiceImpl.class})
class StudentServiceImplTest {
    @Autowired
    StudentServiceImpl studentService;
    @MockBean
    StudentDao studentDao;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> studentService.findById(3L));
    }
}