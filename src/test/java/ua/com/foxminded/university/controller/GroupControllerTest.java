package ua.com.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.university.config.SecurityConfig;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.view.GroupView;
import ua.com.foxminded.university.service.AcademicYearService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.SpecialismService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {GroupController.class})
@Import(SecurityConfig.class)
public class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GroupService groupService;

    @MockBean
    SpecialismService specialismService;
    @MockBean
    AcademicYearService academicYearService;

    @MockBean
    UserDetailsService userDetailsService;

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowDisciplineForm() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);

        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );

        mockMvc.perform(get("/groups/showForm")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("specialismNames", List.of("122")))
                .andExpect(model().attribute("academicYears", List.of(1)))
                .andExpect(model().attribute("semesterTypes", List.of(SemesterType.FALL_SEMESTER)));
    }

    @Test
    @WithMockUser(authorities = "Student")
    public void shouldVerifyShowDisciplineFormBan() throws Exception {
        mockMvc.perform(get("/groups/showForm")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowGroupsList() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Group> groups = List.of(groupId1, groupId2);
        List<GroupView> groupViews = List.of(
                new GroupView(1L, groupId1.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()),
                new GroupView(2L, groupId2.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()));

        when(groupService.findAll()).thenReturn(
                groups
        );

        mockMvc.perform(get("/groups/list")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groups", groupViews));
    }

    @Test
    @WithMockUser(authorities = "Educator")
    public void shouldVerifyShowGroupsListBan() throws Exception {
        mockMvc.perform(get("/groups/list")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyAddGroup() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group("AI-193", specialismId1, academicYearId1);

        when(specialismService.findBySpecialismName(specialismId1.getSpecialismName())).thenReturn(
                specialismId1
        );
        when(academicYearService.findByYearNumberAndSemesterType(academicYearId1.getYearNumber(), academicYearId1.getSemesterType())).thenReturn(
                academicYearId1
        );

        mockMvc.perform(post("/groups/add")
                        .with(csrf())
                        .param("groupName", groupId1.getGroupName())
                        .param("specialismName", specialismId1.getSpecialismName())
                        .param("academicYearNumber", String.valueOf(academicYearId1.getYearNumber()))
                        .param("semesterType", String.valueOf(academicYearId1.getSemesterType()))
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(groupService, times(1)).save(groupId1);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowUpdateForm() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);

        when(groupService.findById(groupId1.getId())).thenReturn(
                groupId1
        );
        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );

        mockMvc.perform(get("/groups/edit/{id}", groupId1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("specialismNames", List.of("122")))
                .andExpect(model().attribute("academicYears", List.of(1)))
                .andExpect(model().attribute("semesterTypes", List.of(SemesterType.FALL_SEMESTER)));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyUpdateGroup() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Group> groups = List.of(groupId1, groupId2);
        List<GroupView> groupViews = List.of(
                new GroupView(1L, groupId1.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()),
                new GroupView(2L, groupId2.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()));

        when(specialismService.findBySpecialismName(specialismId1.getSpecialismName())).thenReturn(
                specialismId1
        );
        when(academicYearService.findByYearNumberAndSemesterType(academicYearId1.getYearNumber(), academicYearId1.getSemesterType())).thenReturn(
                academicYearId1
        );
        when(groupService.findAll()).thenReturn(
                groups
        );
        mockMvc.perform(post("/groups/update/{id}", groupId1.getId())
                        .with(csrf())
                        .param("groupName", groupId1.getGroupName())
                        .param("specialismName", specialismId1.getSpecialismName())
                        .param("academicYearNumber", String.valueOf(academicYearId1.getYearNumber()))
                        .param("semesterType", String.valueOf(academicYearId1.getSemesterType()))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groups", groupViews));
        verify(groupService, times(1)).save(groupId1);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyDelete() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        List<Group> groups = List.of(groupId1, groupId2);
        List<GroupView> groupViews = List.of(
                new GroupView(1L, groupId1.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()),
                new GroupView(2L, groupId2.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()));

        when(groupService.findById(groupId1.getId())).thenReturn(
                groupId1
        );
        doNothing().when(groupService).delete(groupId1);
        when(groupService.findAll()).thenReturn(
                groups
        );
        mockMvc.perform(get("/groups/delete/{id}", groupId1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groups", groupViews));
        verify(groupService, times(1)).delete(groupId1);
    }

    @Test
    @WithMockUser(authorities = "Staff")
    public void shouldVerifyDeleteBan() throws Exception {
        mockMvc.perform(get("/groups/delete/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
