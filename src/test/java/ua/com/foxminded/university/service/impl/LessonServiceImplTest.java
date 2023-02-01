package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.dao.LessonDao;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {LessonServiceImpl.class})
class LessonServiceImplTest {
    @Autowired
    LessonServiceImpl lessonService;
    @MockBean
    LessonDao lessonDao;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> lessonService.findById(1L));
    }
}