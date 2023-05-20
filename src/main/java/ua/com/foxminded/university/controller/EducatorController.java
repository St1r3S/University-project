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
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.service.EducatorService;

import java.util.Arrays;

@Controller
@RequestMapping("/educators")
public class EducatorController {

    public static final String USER_ROLES = "userRoles";
    public static final String ACADEMIC_RANKS = "academicRanks";
    public static final String EDUCATORS = "educators";
    public static final String EDUCATOR_EDUCATORS_LIST = "educator/educators-list";
    private final EducatorService educatorService;
    private final PasswordEncoder passwordEncoder;


    public EducatorController(EducatorService educatorService, PasswordEncoder passwordEncoder) {
        this.educatorService = educatorService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/showForm")
    public String showEducatorForm(Educator educator, Model model) {
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
        model.addAttribute(ACADEMIC_RANKS, Arrays.asList(AcademicRank.values()));

        return "educator/add-educator";
    }

    @GetMapping("/list")
    public String educatorsList(Model model) {
        model.addAttribute(EDUCATORS, this.educatorService.findAll());

        return EDUCATOR_EDUCATORS_LIST;
    }

    @PostMapping("/add")
    public String addEducator(@Valid Educator educator, BindingResult result, Model model) {
        educator.setPasswordHash(educator.getPasswordHash());
        if (result.hasErrors()) {
            model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
            model.addAttribute(ACADEMIC_RANKS, Arrays.asList(AcademicRank.values()));

            return "educator/add-educator";
        }
        educator.setPasswordHash(passwordEncoder.encode(educator.getPasswordHash()));
        this.educatorService.save(educator);

        return "redirect:list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Educator educator = this.educatorService.findById(id);

        model.addAttribute("educator", educator);
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
        model.addAttribute(ACADEMIC_RANKS, Arrays.asList(AcademicRank.values()));

        return "educator/update-educator";
    }

    @PostMapping("/update/{id}")
    public String updateEducator(@PathVariable("id") long id, @Valid Educator educator, BindingResult result, Model model) {
        if (result.hasErrors()) {
            educator.setId(id);
            return "educator/update-educator";
        }
        educator.setPasswordHash(passwordEncoder.encode(educator.getPasswordHash()));
        educatorService.save(educator);

        model.addAttribute(EDUCATORS, this.educatorService.findAll());
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
        model.addAttribute(ACADEMIC_RANKS, Arrays.asList(AcademicRank.values()));

        return EDUCATOR_EDUCATORS_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteEducator(@PathVariable("id") long id, Model model) {
        Educator educator = this.educatorService.findById(id);

        educatorService.delete(educator);
        model.addAttribute(EDUCATORS, this.educatorService.findAll());

        return EDUCATOR_EDUCATORS_LIST;

    }
}
