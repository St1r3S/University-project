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
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {StaffController.class})
@Import(SecurityConfig.class)
public class StaffControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowStaffForm() throws Exception {
        mockMvc.perform(get("/staff/showForm")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())));
    }

    @Test
    @WithMockUser(authorities = "Student")
    public void shouldVerifyShowStaffFormBan() throws Exception {
        mockMvc.perform(get("/staff/showForm")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowStaffsList() throws Exception {
        User userId1 = new User(1L, "maccas82", "$2a$12$aWPsk16Ub7M15nZOH7sDgeMhP/WjqWXPQBSw8uo7tZpTuiUQVEFly", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        User userId2 = new User(2L, "sobs85", "$2a$12$Ine2DS.L6N026UvCItB8GuIIBIL1Z1/3fhFZq/UANsRsGGsdbxZ/O", UserRole.STAFF, "Jack",
                "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com");
        List<User> users = List.of(userId1, userId2);

        when(userService.findAll()).thenReturn(
                users
        );

        mockMvc.perform(get("/staff/list")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("staff", users));
    }

    @Test
    @WithMockUser(authorities = "Staff")
    public void shouldVerifyShowStaffsListBan() throws Exception {
        mockMvc.perform(get("/staff/list")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyAddStaff() throws Exception {
        User userId1 = new User("maccas82", "$2a$12$aWPsk16Ub7M15nZOH7sDgeMhP/WjqWXPQBSw8uo7tZpTuiUQVEFly", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        when(passwordEncoder.encode(userId1.getPasswordHash())).thenReturn(
                userId1.getPasswordHash()
        );
        mockMvc.perform(post("/staff/add")
                        .with(csrf())
                        .param("userRole", String.valueOf(userId1.getUserRole()))
                        .param("userName", String.valueOf(userId1.getUserName()))
                        .param("passwordHash", String.valueOf(userId1.getPasswordHash()))
                        .param("firstName", String.valueOf(userId1.getFirstName()))
                        .param("lastName", String.valueOf(userId1.getLastName()))
                        .param("birthday", String.valueOf(userId1.getBirthday()))
                        .param("email", String.valueOf(userId1.getEmail()))
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(userService, times(1)).save(userId1);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyShowUpdateForm() throws Exception {
        User userId1 = new User(1L, "maccas82", "$2a$12$aWPsk16Ub7M15nZOH7sDgeMhP/WjqWXPQBSw8uo7tZpTuiUQVEFly", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");

        when(userService.findById(userId1.getId())).thenReturn(
                userId1
        );

        mockMvc.perform(get("/staff/edit/{id}", userId1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("staffMember", userId1))
                .andExpect(model().attribute("userRoles", Arrays.asList(UserRole.values())));
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyUpdateStaff() throws Exception {
        User userId1 = new User(1L, "maccas82", "$2a$12$aWPsk16Ub7M15nZOH7sDgeMhP/WjqWXPQBSw8uo7tZpTuiUQVEFly", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        User userId2 = new User(2L, "sobs85", "$2a$12$Ine2DS.L6N026UvCItB8GuIIBIL1Z1/3fhFZq/UANsRsGGsdbxZ/O", UserRole.STAFF, "Jack",
                "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com");
        List<User> users = List.of(userId1, userId2);

        when(userService.findAll()).thenReturn(
                users
        );
        when(passwordEncoder.encode(userId1.getPasswordHash())).thenReturn(
                userId1.getPasswordHash()
        );
        mockMvc.perform(post("/staff/update/{id}", userId1.getId())
                        .with(csrf())
                        .param("userRole", String.valueOf(userId1.getUserRole()))
                        .param("userName", String.valueOf(userId1.getUserName()))
                        .param("passwordHash", String.valueOf(userId1.getPasswordHash()))
                        .param("firstName", String.valueOf(userId1.getFirstName()))
                        .param("lastName", String.valueOf(userId1.getLastName()))
                        .param("birthday", String.valueOf(userId1.getBirthday()))
                        .param("email", String.valueOf(userId1.getEmail()))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("staff", users));
        verify(userService, times(1)).save(userId1);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void shouldVerifyDelete() throws Exception {
        User userId1 = new User(1L, "maccas82", "$2a$12$aWPsk16Ub7M15nZOH7sDgeMhP/WjqWXPQBSw8uo7tZpTuiUQVEFly", UserRole.ADMIN, "Michael",
                "Maccas", LocalDate.parse("1982-06-22"), "maccas82@gmail.com");
        User userId2 = new User(2L, "sobs85", "$2a$12$Ine2DS.L6N026UvCItB8GuIIBIL1Z1/3fhFZq/UANsRsGGsdbxZ/O", UserRole.STAFF, "Jack",
                "Sobs", LocalDate.parse("1985-04-17"), "sobs85@gmail.com");
        List<User> users = List.of(userId1, userId2);

        when(userService.findAll()).thenReturn(
                users
        );
        when(userService.findById(userId1.getId())).thenReturn(
                userId1
        );
        doNothing().when(userService).delete(userId1);

        mockMvc.perform(get("/staff/delete/{id}", userId1.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("staff", users));
        verify(userService, times(1)).delete(userId1);
    }

    @Test
    @WithMockUser(authorities = "Staff")
    public void shouldVerifyDeleteBan() throws Exception {
        mockMvc.perform(get("/staff/delete/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
