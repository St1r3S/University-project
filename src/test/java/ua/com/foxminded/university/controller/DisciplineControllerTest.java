package ua.com.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.view.DisciplineView;
import ua.com.foxminded.university.service.AcademicYearService;
import ua.com.foxminded.university.service.DisciplineService;
import ua.com.foxminded.university.service.EducatorService;
import ua.com.foxminded.university.service.SpecialismService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {DisciplineController.class})
public class DisciplineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DisciplineService disciplineService;
    @MockBean
    SpecialismService specialismService;
    @MockBean
    AcademicYearService academicYearService;
    @MockBean
    EducatorService educatorService;

    @Test
    public void shouldVerifyShowDisciplineForm() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);


        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );
        when(educatorService.findAllFreeEducators()).thenReturn(
                List.of(educatorId5, educatorId6)
        );

        mockMvc.perform(get("/disciplines/showForm")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("specialismNames", List.of("122")))
                .andExpect(model().attribute("academicYears", List.of(1)))
                .andExpect(model().attribute("semesterTypes", List.of(SemesterType.FALL_SEMESTER)))
                .andExpect(model().attribute("educators", List.of(educatorId5, educatorId6)));
    }

    @Test
    public void shouldVerifyShowDisciplinesList() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        List<DisciplineView> disciplineViews = List.of(
                new DisciplineView(1L, disciplineId1.getDisciplineName(), specialismId1.getSpecialismName(),
                        academicYearId1.getYearNumber(), academicYearId1.getSemesterType(), educatorId5),
                new DisciplineView(2L, disciplineId2.getDisciplineName(), specialismId1.getSpecialismName(),
                        academicYearId1.getYearNumber(), academicYearId1.getSemesterType(), educatorId6)
        );

        when(disciplineService.findAll()).thenReturn(
                List.of(disciplineId1, disciplineId2)
        );

        mockMvc.perform(get("/disciplines/list")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("disciplines", disciplineViews));
    }

    @Test
    public void shouldVerifyAddDiscipline() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline("Math", specialismId1, academicYearId1, educatorId5);

        when(specialismService.findBySpecialismName(specialismId1.getSpecialismName())).thenReturn(
                specialismId1
        );
        when(academicYearService.findByYearNumberAndSemesterType(academicYearId1.getYearNumber(), academicYearId1.getSemesterType())).thenReturn(
                academicYearId1
        );
        when(educatorService.findById(educatorId5.getId())).thenReturn(
                educatorId5
        );

        mockMvc.perform(post("/disciplines/add")
                        .param("disciplineName", disciplineId1.getDisciplineName())
                        .param("specialismName", specialismId1.getSpecialismName())
                        .param("academicYearNumber", String.valueOf(academicYearId1.getYearNumber()))
                        .param("semesterType", String.valueOf(academicYearId1.getSemesterType()))
                        .param("educator.id", String.valueOf(educatorId5.getId()))
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(disciplineService, times(1)).save(disciplineId1);
    }

    @Test
    public void shouldVerifyShowUpdateForm() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);


        when(disciplineService.findById(disciplineId1.getId())).thenReturn(
                disciplineId1
        );
        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );
        when(educatorService.findAllFreeEducators()).thenReturn(
                List.of(educatorId5)
        );

        mockMvc.perform(get("/disciplines/edit/{id}", disciplineId1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("specialismNames", List.of("122")))
                .andExpect(model().attribute("academicYears", List.of(1)))
                .andExpect(model().attribute("semesterTypes", List.of(SemesterType.FALL_SEMESTER)))
                .andExpect(model().attribute("educators", List.of(educatorId5)));
    }

    @Test
    public void shouldVerifyUpdateDiscipline() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);

        when(specialismService.findBySpecialismName(specialismId1.getSpecialismName())).thenReturn(
                specialismId1
        );
        when(academicYearService.findByYearNumberAndSemesterType(academicYearId1.getYearNumber(), academicYearId1.getSemesterType())).thenReturn(
                academicYearId1
        );
        when(educatorService.findById(educatorId5.getId())).thenReturn(
                educatorId5
        );
        when(specialismService.findAll()).thenReturn(
                List.of(specialismId1)
        );
        when(academicYearService.findAll()).thenReturn(
                List.of(academicYearId1)
        );
        when(educatorService.findAllFreeEducators()).thenReturn(
                List.of(educatorId5)
        );

        mockMvc.perform(post("/disciplines/update/{id}", disciplineId1.getId())
                        .param("disciplineName", disciplineId1.getDisciplineName())
                        .param("specialismName", specialismId1.getSpecialismName())
                        .param("academicYearNumber", String.valueOf(academicYearId1.getYearNumber()))
                        .param("semesterType", String.valueOf(academicYearId1.getSemesterType()))
                        .param("educator.id", String.valueOf(educatorId5.getId()))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("specialismNames", List.of("122")))
                .andExpect(model().attribute("academicYears", List.of(1)))
                .andExpect(model().attribute("semesterTypes", List.of(SemesterType.FALL_SEMESTER)))
                .andExpect(model().attribute("educators", List.of(educatorId5)));
        verify(disciplineService, times(1)).save(disciplineId1);
    }

    @Test
    public void shouldVerifyDelete() throws Exception {
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        when(disciplineService.findById(disciplineId1.getId())).thenReturn(
                disciplineId1
        );
        doNothing().when(disciplineService).delete(disciplineId1);

        mockMvc.perform(get("/disciplines/delete/{id}", disciplineId1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("disciplines", Collections.EMPTY_LIST));
        verify(disciplineService, times(1)).delete(disciplineId1);
    }
}
