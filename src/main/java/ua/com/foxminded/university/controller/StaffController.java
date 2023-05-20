package ua.com.foxminded.university.controller;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.UserService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/staff")
public class StaffController {
    public static final String USER_ROLES = "userRoles";
    public static final String STAFF = "staff";
    public static final String STAFF_STAFF_LIST = "staff/staff-list";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public StaffController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/showForm")
    public String showStaffMemberForm(User staffMember, Model model) {
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));

        return "staff/add-staff-member";
    }

    @GetMapping("/list")
    public String staff(Model model) {
        model.addAttribute(STAFF, this.userService.findAll().stream().filter(s -> s.getUserRole() == UserRole.STAFF || s.getUserRole() == UserRole.ADMIN).collect(Collectors.toList()));

        return STAFF_STAFF_LIST;
    }

    @PostMapping("/add")
    public String addStaffMember(@Valid User staffMember, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));

            return "staff/add-staff-member";
        }
        staffMember.setPasswordHash(passwordEncoder.encode(staffMember.getPasswordHash()));
        this.userService.save(staffMember);

        return "redirect:list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User staffMember = this.userService.findById(id);

        model.addAttribute("staffMember", staffMember);
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));

        return "staff/update-staff-member";
    }

    @PostMapping("/update/{id}")
    public String updateStaffMember(@PathVariable("id") long id, @Valid User staffMember, BindingResult result, Model model) {
        if (result.hasErrors()) {
            staffMember.setId(id);
            return "staff/update-staff-member";
        }
        staffMember.setPasswordHash(passwordEncoder.encode(staffMember.getPasswordHash()));
        userService.save(staffMember);

        model.addAttribute(STAFF, this.userService.findAll().stream().filter(s -> s.getUserRole() == UserRole.STAFF || s.getUserRole() == UserRole.ADMIN).collect(Collectors.toList()));

        return STAFF_STAFF_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteStaffMember(@PathVariable("id") long id, Model model) {
        User staffMember = this.userService.findById(id);

        userService.delete(staffMember);
        model.addAttribute(STAFF, this.userService.findAll().stream().filter(s -> s.getUserRole() == UserRole.STAFF || s.getUserRole() == UserRole.ADMIN).collect(Collectors.toList()));

        return STAFF_STAFF_LIST;
    }
}
