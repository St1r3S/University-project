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
class GroupServiceImplTest {
    @Autowired
    GroupServiceImpl groupService;

    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> groupService.findById(1L));
    }
}