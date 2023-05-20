package ua.com.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.university.config.SecurityConfig;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.view.StudentView;
import ua.com.foxminded.university.service.AcademicYearService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.SpecialismService;
import ua.com.foxminded.university.service.StudentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {StudentController.class})
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @MockBean
    GroupService groupService;
    @MockBean
    SpecialismService specialismService;
    @MockBean
    AcademicYearService academicYearService;

    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowStudentForm() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);

        when(groupService.findAll()).thenReturn(
                List.of(groupId1)
        );
        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );

        mockMvc.perform(get("/students/showForm")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())))
                .andExpect(model().attribute("groupNames", List.of(groupId1.getGroupName())))
                .andExpect(model().attribute("specialismNames", List.of(specialismId1.getSpecialismName())))
                .andExpect(model().attribute("academicYears", List.of(academicYearId1.getYearNumber())))
                .andExpect(model().attribute("semesterTypes", List.of(academicYearId1.getSemesterType())));
    }

    @Test
    @WithMockUser(authorities = "Staff")
    public void shouldVerifyShowStudentFormBan() throws Exception {
        mockMvc.perform(get("/students/showForm")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowStudentsList() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Student studentId3 = new Student(3L, "johny05", "$2a$12$9w3TyJIX5elVb0UL9Yw9beHx/IAAyNwPSpeXg31bUMi7e9vtGRKI.", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                groupId1, specialismId1, academicYearId1);
        Student studentId4 = new Student(4L, "finn25", "$2a$12$5qWSAljsACYml5KqBkalV.1U6DxC0VDTY9EcV4z4sOY0AS9/Fzqwq", UserRole.STUDENT, "Finn",
                "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                groupId2, specialismId1, academicYearId1);

        List<Student> students = List.of(
                studentId3, studentId4
        );
        List<StudentView> studentViews = List.of(
                new StudentView(3L, studentId3.getUserName(), studentId3.getPasswordHash(), studentId3.getUserRole(),
                        studentId3.getFirstName(), studentId3.getLastName(), studentId3.getBirthday(), studentId3.getEmail(),
                        groupId1.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()),
                new StudentView(4L, studentId4.getUserName(), studentId4.getPasswordHash(), studentId4.getUserRole(),
                        studentId4.getFirstName(), studentId4.getLastName(), studentId4.getBirthday(), studentId4.getEmail(),
                        groupId2.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType())
        );

        when(studentService.findAll()).thenReturn(
                students
        );

        mockMvc.perform(get("/students/list")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", studentViews));
    }

    @Test
    @WithMockUser(authorities = "Student")
    public void shouldVerifyShowStudentsListBan() throws Exception {
        mockMvc.perform(get("/students/list")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyAddStudent() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student studentId3 = new Student("johny05", "$2a$12$9w3TyJIX5elVb0UL9Yw9beHx/IAAyNwPSpeXg31bUMi7e9vtGRKI.", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                groupId1, specialismId1, academicYearId1);

        when(groupService.findByGroupName(groupId1.getGroupName())).thenReturn(
                groupId1
        );
        when(specialismService.findBySpecialismName(specialismId1.getSpecialismName())).thenReturn(
                specialismId1
        );
        when(academicYearService.findByYearNumberAndSemesterType(academicYearId1.getYearNumber(), academicYearId1.getSemesterType())).thenReturn(
                academicYearId1
        );
        when(passwordEncoder.encode(studentId3.getPasswordHash())).thenReturn(
                studentId3.getPasswordHash()
        );

        mockMvc.perform(post("/students/add")
                        .with(csrf())
                        .param("userRole", String.valueOf(studentId3.getUserRole()))
                        .param("userName", String.valueOf(studentId3.getUserName()))
                        .param("passwordHash", String.valueOf(studentId3.getPasswordHash()))
                        .param("firstName", String.valueOf(studentId3.getFirstName()))
                        .param("lastName", String.valueOf(studentId3.getLastName()))
                        .param("birthday", String.valueOf(studentId3.getBirthday()))
                        .param("email", String.valueOf(studentId3.getEmail()))
                        .param("groupName", String.valueOf(groupId1.getGroupName()))
                        .param("specialismName", String.valueOf(specialismId1.getSpecialismName()))
                        .param("academicYearNumber", String.valueOf(academicYearId1.getYearNumber()))
                        .param("semesterType", String.valueOf(academicYearId1.getSemesterType()))
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(studentService, times(1)).save(studentId3);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowUpdateForm() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Student studentId3 = new Student(3L, "johny05", "$2a$12$9w3TyJIX5elVb0UL9Yw9beHx/IAAyNwPSpeXg31bUMi7e9vtGRKI.", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                groupId1, specialismId1, academicYearId1);

        when(studentService.findById(studentId3.getId())).thenReturn(
                studentId3
        );

        when(groupService.findAll()).thenReturn(
                List.of(groupId1)
        );
        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );

        mockMvc.perform(get("/students/edit/{id}", studentId3.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())))
                .andExpect(model().attribute("groupNames", List.of(groupId1.getGroupName())))
                .andExpect(model().attribute("specialismNames", List.of(specialismId1.getSpecialismName())))
                .andExpect(model().attribute("academicYears", List.of(academicYearId1.getYearNumber())))
                .andExpect(model().attribute("semesterTypes", List.of(academicYearId1.getSemesterType())));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyUpdateStudent() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Student studentId3 = new Student(3L, "johny05", "$2a$12$9w3TyJIX5elVb0UL9Yw9beHx/IAAyNwPSpeXg31bUMi7e9vtGRKI.", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                groupId1, specialismId1, academicYearId1);
        Student studentId4 = new Student(4L, "finn25", "$2a$12$5qWSAljsACYml5KqBkalV.1U6DxC0VDTY9EcV4z4sOY0AS9/Fzqwq", UserRole.STUDENT, "Finn",
                "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                groupId2, specialismId1, academicYearId1);

        List<Student> students = List.of(
                studentId3, studentId4
        );
        List<StudentView> studentViews = List.of(
                new StudentView(3L, studentId3.getUserName(), studentId3.getPasswordHash(), studentId3.getUserRole(),
                        studentId3.getFirstName(), studentId3.getLastName(), studentId3.getBirthday(), studentId3.getEmail(),
                        groupId1.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()),
                new StudentView(4L, studentId4.getUserName(), studentId4.getPasswordHash(), studentId4.getUserRole(),
                        studentId4.getFirstName(), studentId4.getLastName(), studentId4.getBirthday(), studentId4.getEmail(),
                        groupId2.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType())
        );

        when(groupService.findByGroupName(groupId1.getGroupName())).thenReturn(
                groupId1
        );
        when(specialismService.findBySpecialismName(specialismId1.getSpecialismName())).thenReturn(
                specialismId1
        );
        when(academicYearService.findByYearNumberAndSemesterType(academicYearId1.getYearNumber(), academicYearId1.getSemesterType())).thenReturn(
                academicYearId1
        );
        when(studentService.findAll()).thenReturn(
                students
        );
        when(passwordEncoder.encode(studentId3.getPasswordHash())).thenReturn(
                studentId3.getPasswordHash()
        );

        mockMvc.perform(post("/students/update/{id}", studentId3.getId())
                        .with(csrf())
                        .param("userRole", String.valueOf(studentId3.getUserRole()))
                        .param("userName", String.valueOf(studentId3.getUserName()))
                        .param("passwordHash", String.valueOf(studentId3.getPasswordHash()))
                        .param("firstName", String.valueOf(studentId3.getFirstName()))
                        .param("lastName", String.valueOf(studentId3.getLastName()))
                        .param("birthday", String.valueOf(studentId3.getBirthday()))
                        .param("email", String.valueOf(studentId3.getEmail()))
                        .param("groupName", String.valueOf(groupId1.getGroupName()))
                        .param("specialismName", String.valueOf(specialismId1.getSpecialismName()))
                        .param("academicYearNumber", String.valueOf(academicYearId1.getYearNumber()))
                        .param("semesterType", String.valueOf(academicYearId1.getSemesterType()))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", studentViews));
        verify(studentService, times(1)).save(studentId3);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyDelete() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Student studentId3 = new Student(3L, "johny05", "$2a$12$9w3TyJIX5elVb0UL9Yw9beHx/IAAyNwPSpeXg31bUMi7e9vtGRKI.", UserRole.STUDENT, "Alex",
                "Johnson", LocalDate.parse("2002-05-05"), "a.johny@gmail.com",
                groupId1, specialismId1, academicYearId1);
        Student studentId4 = new Student(4L, "finn25", "$2a$12$5qWSAljsACYml5KqBkalV.1U6DxC0VDTY9EcV4z4sOY0AS9/Fzqwq", UserRole.STUDENT, "Finn",
                "Chikson", LocalDate.parse("2002-04-25"), "finn@gmail.com",
                groupId2, specialismId1, academicYearId1);

        List<Student> students = List.of(
                studentId3, studentId4
        );
        List<StudentView> studentViews = List.of(
                new StudentView(3L, studentId3.getUserName(), studentId3.getPasswordHash(), studentId3.getUserRole(),
                        studentId3.getFirstName(), studentId3.getLastName(), studentId3.getBirthday(), studentId3.getEmail(),
                        groupId1.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType()),
                new StudentView(4L, studentId4.getUserName(), studentId4.getPasswordHash(), studentId4.getUserRole(),
                        studentId4.getFirstName(), studentId4.getLastName(), studentId4.getBirthday(), studentId4.getEmail(),
                        groupId2.getGroupName(), specialismId1.getSpecialismName(), academicYearId1.getYearNumber(), academicYearId1.getSemesterType())
        );

        when(studentService.findAll()).thenReturn(
                students
        );
        doNothing().when(studentService).deleteById(studentId3.getId());

        mockMvc.perform(get("/students/delete/{id}", studentId3.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", studentViews));
        verify(studentService, times(1)).deleteById(studentId3.getId());
    }

    @Test
    @WithMockUser(authorities = "Educator")
    public void shouldVerifyDeleteBan() throws Exception {
        mockMvc.perform(get("/students/delete/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
