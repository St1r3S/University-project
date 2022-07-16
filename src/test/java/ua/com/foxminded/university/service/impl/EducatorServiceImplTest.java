package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.foxminded.university.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class EducatorServiceImplTest {
    @Autowired
    EducatorServiceImpl educatorService;

    @Test
    void shouldTrowNotFindException() {
        assertThrows(NotFoundException.class, () -> educatorService.findById(1L));
    }
}