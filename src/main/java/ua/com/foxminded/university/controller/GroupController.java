package ua.com.foxminded.university.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.view.GroupView;
import ua.com.foxminded.university.service.AcademicYearService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.SpecialismService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupController {

    public static final String SPECIALISM_NAMES = "specialismNames";
    public static final String ACADEMIC_YEARS = "academicYears";
    public static final String SEMESTER_TYPES = "semesterTypes";
    public static final String GROUPS = "groups";
    public static final String GROUP_GROUPS_LIST = "group/groups-list";
    private final GroupService groupService;

    private final SpecialismService specialismService;
    private final AcademicYearService academicYearService;


    public GroupController(GroupService groupService, SpecialismService specialismService, AcademicYearService academicYearService) {
        this.groupService = groupService;
        this.specialismService = specialismService;
        this.academicYearService = academicYearService;
    }

    @GetMapping("/showForm")
    public String showGroupForm(GroupView groupView, Model model) {
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        return "group/add-group";
    }

    @GetMapping("/list")
    public String groups(Model model) {
        model.addAttribute(GROUPS, this.groupService.findAll()
                .stream()
                .map(group -> GroupView.groupToGroupView(
                        group,
                        group.getSpecialism(),
                        group.getAcademicYear()
                ))
                .collect(Collectors.toList())
        );
        return GROUP_GROUPS_LIST;
    }

    @PostMapping("/add")
    public String addGroup(@Valid GroupView groupView, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
            model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
            model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));

            return "group/add-group";
        }
        Group group = groupView.groupViewToGroup(
                specialismService.findBySpecialismName(groupView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(groupView.getAcademicYearNumber(), groupView.getSemesterType())
        );
        this.groupService.save(group);
        return "redirect:list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Group group = this.groupService.findById(id);

        model.addAttribute("group",
                GroupView.groupToGroupView(
                        group,
                        group.getSpecialism(),
                        group.getAcademicYear()
                )
        );
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        return "group/update-group";
    }

    @PostMapping("/update/{id}")
    public String updateGroup(@PathVariable("id") long id, @Valid GroupView groupView, BindingResult result, Model model) {
        Group groupToSave = groupView.groupViewToGroup(
                specialismService.findBySpecialismName(groupView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(groupView.getAcademicYearNumber(), groupView.getSemesterType())
        );

        if (result.hasErrors()) {
            groupView.setId(id);
            return "group/update-group";
        }

        // update student
        this.groupService.save(groupToSave);

        // get all students ( with update)
        model.addAttribute(GROUPS, this.groupService.findAll()
                .stream()
                .map(group -> GroupView.groupToGroupView(
                        group,
                        group.getSpecialism(),
                        group.getAcademicYear()
                ))
                .collect(Collectors.toList())
        );
        return GROUP_GROUPS_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteGroup(@PathVariable("id") long id, Model model) {

        Group groupToDelete = this.groupService.findById(id);

        this.groupService.delete(groupToDelete);
        model.addAttribute(GROUPS, this.groupService.findAll()
                .stream()
                .map(group -> GroupView.groupToGroupView(
                        group,
                        group.getSpecialism(),
                        group.getAcademicYear()
                ))
                .collect(Collectors.toList())
        );
        return GROUP_GROUPS_LIST;

    }
}
