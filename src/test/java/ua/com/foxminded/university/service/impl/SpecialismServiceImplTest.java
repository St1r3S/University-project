package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.dao.SpecialismDao;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {SpecialismServiceImpl.class})
class SpecialismServiceImplTest {
    @Autowired
    SpecialismServiceImpl specialismService;
    @MockBean
    SpecialismDao specialismDao;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> specialismService.findById(1L));
    }
}