package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.dao.DisciplineDao;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {DisciplineServiceImpl.class})
class DisciplineServiceImplTest {

    @Autowired
    DisciplineServiceImpl disciplineService;
    @MockBean
    DisciplineDao disciplineDao;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> disciplineService.findById(1L));
    }
}