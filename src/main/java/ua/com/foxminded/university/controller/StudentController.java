package ua.com.foxminded.university.controller;

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
import ua.com.foxminded.university.model.user.Student;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.view.StudentView;
import ua.com.foxminded.university.service.AcademicYearService;
import ua.com.foxminded.university.service.GroupService;
import ua.com.foxminded.university.service.SpecialismService;
import ua.com.foxminded.university.service.StudentService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students/")
public class StudentController {

    private final StudentService studentService;

    private final GroupService groupService;
    private final SpecialismService specialismService;
    private final AcademicYearService academicYearService;


    public StudentController(StudentService studentService, GroupService groupService, SpecialismService specialismService, AcademicYearService academicYearService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.specialismService = specialismService;
        this.academicYearService = academicYearService;
    }

    @GetMapping("showForm")
    public String showStudentForm(StudentView studentView, Model model) {
        model.addAttribute("userRoles", Arrays.asList(UserRole.values()));
        model.addAttribute("groupNames", this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute("specialismNames", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute("academicYears", this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute("semesterTypes", this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        return "student/add-student";
    }

    @GetMapping("list")
    public String students(Model model) {
        model.addAttribute("students", this.studentService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        groupService.findById(student.getGroupId()),
                        specialismService.findById(student.getSpecialismId()),
                        academicYearService.findById(student.getAcademicYearId())))
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
                groupService.findByGroupName(studentView.getGroupName()),
                specialismService.findBySpecialismName(studentView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(studentView.getAcademicYearNumber(), studentView.getSemesterType())
        );
        this.studentService.save(student);
        return "redirect:list";
    }


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = this.studentService.findById(id);

        model.addAttribute("student",
                StudentView.studentToStudentView(
                        student,
                        groupService.findById(student.getGroupId()),
                        specialismService.findById(student.getSpecialismId()),
                        academicYearService.findById(student.getAcademicYearId())
                )
        );
        model.addAttribute("groupNames", this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute("specialismNames", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute("academicYears", this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute("semesterTypes", this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute("userRoles", Arrays.asList(UserRole.values()));
        return "student/update-student";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id, StudentView studentView, BindingResult result, Model model) {
        Student studentToSave = studentView.studentViewToStudent(
                groupService.findByGroupName(studentView.getGroupName()),
                specialismService.findBySpecialismName(studentView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(studentView.getAcademicYearNumber(), studentView.getSemesterType())
        );

        if (result.hasErrors()) {
            studentView.setId(id);
            return "student/update-student";
        }

        // update student
        studentService.save(studentToSave);

        // get all students ( with update)
        model.addAttribute("students", this.studentService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        groupService.findById(student.getGroupId()),
                        specialismService.findById(student.getSpecialismId()),
                        academicYearService.findById(student.getAcademicYearId())))
                .collect(Collectors.toList())
        );
        return "student/students-list";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {

        this.studentService.deleteById(id);
        model.addAttribute("students", this.studentService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        groupService.findById(student.getGroupId()),
                        specialismService.findById(student.getSpecialismId()),
                        academicYearService.findById(student.getAcademicYearId())))
                .collect(Collectors.toList())
        );
        return "student/students-list";

    }
}
