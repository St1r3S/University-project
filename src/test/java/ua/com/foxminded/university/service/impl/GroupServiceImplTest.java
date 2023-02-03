package ua.com.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import ua.com.foxminded.university.repository.GroupRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {GroupServiceImpl.class})
class GroupServiceImplTest {
    @Autowired
    GroupServiceImpl groupService;
    @MockBean
    GroupRepository groupRepository;


    @Test
    void shouldThrowNotFindException() {
        assertThrows(EmptyResultDataAccessException.class, () -> groupService.findById(1L));
    }
}
