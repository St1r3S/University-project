package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class DisciplineServiceImplTest {

    @Autowired
    DisciplineServiceImpl disciplineService;

    @Test
    void shouldTrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> disciplineService.findById(1L));
    }
}