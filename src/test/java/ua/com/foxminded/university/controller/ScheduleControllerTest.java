package ua.com.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.view.GroupView;
import ua.com.foxminded.university.service.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ScheduleController.class})
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LessonService lessonService;

    @MockBean
    DisciplineService disciplineService;
    @MockBean
    GroupService groupService;
    @MockBean
    RoomService roomService;
    @MockBean
    ScheduleDayService scheduleDayService;

    @MockBean
    SpecialismService specialismService;
    @MockBean
    EducatorService educatorService;
    @MockBean
    AcademicYearService academicYearService;

    @Test
    public void shouldClarifyProblemWithConstraint() throws Exception {
        Specialism specialism = new Specialism(1L, "Specialism");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.SPRING_SEMESTER);
        String groupName = "XX-01";
        when(groupService.findAll()).thenReturn(List.of(
                new Group(1L,
                        groupName,
                        specialism,
                        academicYear)));
        when(specialismService.findById(1L)).thenReturn(specialism); // do we need additional db trip instead group.getSpecialism() ?
        when(academicYearService.findById(1L)).thenReturn(academicYear); // same as above

        List<GroupView> expected = List.of(
                new GroupView(
                        1L,
                        groupName,
                        specialism.getSpecialismName(),
                        academicYear.getYearNumber(),
                        academicYear.getSemesterType()
                )
        );

        mockMvc.perform(get("/schedule/list/groups")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groups", expected));
    }
}
