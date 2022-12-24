package ua.com.foxminded.university.controller;

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
@RequestMapping("/educators/")
public class EducatorController {

    private final EducatorService educatorService;


    public EducatorController(EducatorService educatorService) {
        this.educatorService = educatorService;
    }


    @GetMapping("showForm")
    public String showEducatorForm(Educator educator, Model model) {
        model.addAttribute("userRoles", Arrays.asList(UserRole.values()));
        model.addAttribute("academicRanks", Arrays.asList(AcademicRank.values()));

        return "educator/add-educator";
    }

    @GetMapping("list")
    public String educators(Model model) {
        model.addAttribute("educators", this.educatorService.findAll());

        return "educator/educators-list";
    }

    @PostMapping("add")
    public String addEducator(Educator educator, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "educator/add-educator";
        }
        this.educatorService.save(educator);

        return "redirect:list";
    }


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Educator educator = this.educatorService.findById(id);

        model.addAttribute("educator", educator);
        model.addAttribute("userRoles", Arrays.asList(UserRole.values()));
        model.addAttribute("academicRanks", Arrays.asList(AcademicRank.values()));

        return "educator/update-educator";
    }

    @PostMapping("update/{id}")
    public String updateEducator(@PathVariable("id") long id, Educator educator, BindingResult result, Model model) {
        if (result.hasErrors()) {
            educator.setId(id);
            return "educator/update-educator";
        }

        educatorService.save(educator);

        model.addAttribute("educators", this.educatorService.findAll());
        model.addAttribute("userRoles", Arrays.asList(UserRole.values()));
        model.addAttribute("academicRanks", Arrays.asList(AcademicRank.values()));

        return "educator/educators-list";
    }

    @GetMapping("delete/{id}")
    public String deleteEducator(@PathVariable("id") long id, Model model) {
        Educator educator = this.educatorService.findById(id);

        educatorService.delete(educator);
        model.addAttribute("educators", this.educatorService.findAll());

        return "educator/educators-list";

    }
}
