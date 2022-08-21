package ua.com.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.model.misc.Specialism;
import ua.com.foxminded.university.model.schedule.WeeklySchedule;
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.view.StudentView;
import ua.com.foxminded.university.service.EducatorService;
import ua.com.foxminded.university.service.SpecialismService;
import ua.com.foxminded.university.service.WeeklyScheduleService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/students/")
public class EducatorController {

    private final EducatorService educatorService;
    private final WeeklyScheduleService weeklyScheduleService;
    private final SpecialismService specialismService;

    public EducatorController(EducatorService educatorService, WeeklyScheduleService weeklyScheduleService, SpecialismService specialismService) {
        this.educatorService = educatorService;
        this.weeklyScheduleService = weeklyScheduleService;
        this.specialismService = specialismService;
    }


    @GetMapping("showForm")
    public String showStudentForm(StudentView studentView, Model model) {
        model.addAttribute("weeklySchedules", this.weeklyScheduleService.findAll().stream().map(WeeklySchedule::getWeekNumber).collect(Collectors.toList()));
        model.addAttribute("specialisms", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        return "student/add-student";
    }

    @GetMapping("list")
    public String students(Model model) {
        model.addAttribute("students", this.educatorService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        weeklyScheduleService.findById(student.getWeeklyScheduleId()).getWeekNumber(),
                        specialismService.findById(student.getSpecialismId()).getSpecialismName()))
                .collect(Collectors.toList())
        );
        return "student/students-list";
    }

    @PostMapping("add")
    public String addStudent(StudentView studentView, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "student/add-student";
        }
        Student student = studentView.studentViewToStudent(
                weeklyScheduleService.findByWeekNumber(studentView.getScheduleWeek()).getId(),
                specialismService.findBySpecialismName(studentView.getSpecialism()).getId()
        );
        this.educatorService.save(student);
        return "redirect:list";
    }


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = this.educatorService.findById(id);

        model.addAttribute("student",
                StudentView.studentToStudentView(
                        student,
                        weeklyScheduleService.findById(student.getWeeklyScheduleId()).getWeekNumber(),
                        specialismService.findById(student.getSpecialismId()).getSpecialismName())
        );
        model.addAttribute("weeklySchedules", this.weeklyScheduleService.findAll().stream().map(WeeklySchedule::getWeekNumber).collect(Collectors.toList()));
        model.addAttribute("specialisms", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        return "student/update-student";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id, StudentView studentView, BindingResult result, Model model) {
        Student studentToSave = studentView.studentViewToStudent(
                weeklyScheduleService.findByWeekNumber(studentView.getScheduleWeek()).getId(),
                specialismService.findBySpecialismName(studentView.getSpecialism()).getId()
        );

        if (result.hasErrors()) {
            studentView.setId(id);
            return "student/update-student";
        }

        // update student
        educatorService.save(studentToSave);

        // get all students ( with update)
        model.addAttribute("students", this.educatorService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        weeklyScheduleService.findById(student.getWeeklyScheduleId()).getWeekNumber(),
                        specialismService.findById(student.getSpecialismId()).getSpecialismName()))
                .collect(Collectors.toList())
        );
        model.addAttribute("weeklySchedules", this.weeklyScheduleService.findAll());
        model.addAttribute("specialisms", this.specialismService.findAll());
        return "student/students-list";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {

        Student studentToDelete = this.educatorService.findById(id);

        this.educatorService.delete(studentToDelete);
        model.addAttribute("students", this.educatorService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        weeklyScheduleService.findById(student.getWeeklyScheduleId()).getWeekNumber(),
                        specialismService.findById(student.getSpecialismId()).getSpecialismName()))
                .collect(Collectors.toList())
        );
        return "student/students-list";

    }
}
