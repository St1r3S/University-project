package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.repository.LessonRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {LessonServiceImpl.class})
class LessonServiceImplTest {
    @Autowired
    LessonServiceImpl lessonService;
    @MockBean
    LessonRepository lessonRepository;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> lessonService.findById(1L));
    }
}