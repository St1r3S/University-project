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
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.EducatorService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {EducatorController.class})
@Import(SecurityConfig.class)
public class EducatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EducatorService educatorService;

    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowEducatorForm() throws Exception {
        mockMvc.perform(get("/educators/showForm")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())))
                .andExpect(model().attribute("academicRanks", Arrays.asList(AcademicRank.values())));
    }

    @Test
    @WithMockUser(authorities = "Student")
    public void shouldVerifyShowEducatorFormBan() throws Exception {
        mockMvc.perform(get("/educators/showForm")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowEducatorsList() throws Exception {
        Educator educatorId5 = new Educator(5L, "grant78", "$2a$12$bDYcmC4EYJb4uB.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "$2a$12$dd6IoxDwQceT.3iigUvwmOnNunAIfZBzqis4rccCs8X2uz8oDJbtK", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);

        List<Educator> educators = List.of(educatorId5, educatorId6);
        when(educatorService.findAll()).thenReturn(
                educators
        );

        mockMvc.perform(get("/educators/list")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("educators", educators));
    }

    @Test
    @WithMockUser(authorities = "Student")
    public void shouldVerifyShowEducatorsListBan() throws Exception {
        mockMvc.perform(get("/educators/list")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyAddEducator() throws Exception {
        Educator educatorId5 = new Educator("grant78", "$2a$12$bDYcmC4EYJb4uB.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        when(passwordEncoder.encode(educatorId5.getPasswordHash())).thenReturn(
                educatorId5.getPasswordHash()
        );

        mockMvc.perform(post("/educators/add")
                        .with(csrf())
                        .param("userRole", String.valueOf(educatorId5.getUserRole()))
                        .param("userName", String.valueOf(educatorId5.getUserName()))
                        .param("passwordHash", String.valueOf(educatorId5.getPasswordHash()))
                        .param("firstName", String.valueOf(educatorId5.getFirstName()))
                        .param("lastName", String.valueOf(educatorId5.getLastName()))
                        .param("birthday", String.valueOf(educatorId5.getBirthday()))
                        .param("email", String.valueOf(educatorId5.getEmail()))
                        .param("academicRank", String.valueOf(educatorId5.getAcademicRank()))
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(educatorService, times(1)).save(educatorId5);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowUpdateForm() throws Exception {
        Educator educatorId5 = new Educator(5L, "grant78", "$2a$12$bDYcmC4EYJb4uB.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        when(educatorService.findById(educatorId5.getId())).thenReturn(
                educatorId5
        );

        mockMvc.perform(get("/educators/edit/{id}", educatorId5.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("educator", educatorId5))
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())))
                .andExpect(model().attribute("academicRanks", Arrays.asList(AcademicRank.values())));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyUpdateEducator() throws Exception {
        Educator educatorId5 = new Educator(5L, "grant78", "$2a$12$bDYcmC4EYJb4uB.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        List<Educator> educators = List.of(educatorId5);
        when(educatorService.findAll()).thenReturn(
                educators
        );
        when(passwordEncoder.encode(educatorId5.getPasswordHash())).thenReturn(
                educatorId5.getPasswordHash()
        );

        mockMvc.perform(post("/educators/update/{id}", educatorId5.getId())
                        .with(csrf())
                        .param("userRole", String.valueOf(educatorId5.getUserRole()))
                        .param("userName", String.valueOf(educatorId5.getUserName()))
                        .param("passwordHash", String.valueOf(educatorId5.getPasswordHash()))
                        .param("firstName", String.valueOf(educatorId5.getFirstName()))
                        .param("lastName", String.valueOf(educatorId5.getLastName()))
                        .param("birthday", String.valueOf(educatorId5.getBirthday()))
                        .param("email", String.valueOf(educatorId5.getEmail()))
                        .param("academicRank", String.valueOf(educatorId5.getAcademicRank()))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("educators", educators))
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())))
                .andExpect(model().attribute("academicRanks", Arrays.asList(AcademicRank.values())));
        verify(educatorService, times(1)).save(educatorId5);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyDelete() throws Exception {
        Educator educatorId5 = new Educator(5L, "grant78", "$2a$12$bDYcmC4EYJb4uB.NLclvD.i0VFCFoJZgHphomz0A5QzTLegGcyzau", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);

        when(educatorService.findById(educatorId5.getId())).thenReturn(
                educatorId5
        );
        doNothing().when(educatorService).delete(educatorId5);

        mockMvc.perform(get("/educators/delete/{id}", educatorId5.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk());
        verify(educatorService, times(1)).delete(educatorId5);
    }

    @Test
    @WithMockUser(authorities = "Student")
    public void shouldVerifyDeleteBan() throws Exception {
        mockMvc.perform(get("/educators/delete/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
