package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.repository.ScheduleDayRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {ScheduleDayServiceImpl.class})
class ScheduleDayServiceImplTest {

    @Autowired
    ScheduleDayServiceImpl scheduleDayService;
    @MockBean
    ScheduleDayRepository scheduleDayRepository;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> scheduleDayService.findById(1L));
    }
}