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
@RequestMapping("/students")
public class StudentController {

    public static final String USER_ROLES = "userRoles";
    public static final String GROUP_NAMES = "groupNames";
    public static final String SPECIALISM_NAMES = "specialismNames";
    public static final String ACADEMIC_YEARS = "academicYears";
    public static final String SEMESTER_TYPES = "semesterTypes";
    public static final String STUDENTS = "students";
    public static final String STUDENT_STUDENTS_LIST = "student/students-list";
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;

    private final GroupService groupService;
    private final SpecialismService specialismService;
    private final AcademicYearService academicYearService;


    public StudentController(StudentService studentService, GroupService groupService, SpecialismService specialismService, AcademicYearService academicYearService, PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
        this.groupService = groupService;
        this.specialismService = specialismService;
        this.academicYearService = academicYearService;
    }

    @GetMapping("/showForm")
    public String showStudentForm(StudentView studentView, Model model) {
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
        model.addAttribute(GROUP_NAMES, this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        return "student/add-student";
    }

    @GetMapping("/list")
    public String students(Model model) {
        model.addAttribute(STUDENTS, this.studentService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        student.getGroup(),
                        student.getSpecialism(),
                        student.getAcademicYear()
                ))
                .collect(Collectors.toList())
        );
        return STUDENT_STUDENTS_LIST;
    }

    @PostMapping("/add")
    public String addStudent(@Valid StudentView studentView, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
            model.addAttribute(GROUP_NAMES, this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
            model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
            model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
            model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
            return "student/add-student";
        }
        Student student = studentView.studentViewToStudent(
                groupService.findByGroupName(studentView.getGroupName()),
                specialismService.findBySpecialismName(studentView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(studentView.getAcademicYearNumber(), studentView.getSemesterType())
        );
        student.setPasswordHash(passwordEncoder.encode(student.getPasswordHash()));
        this.studentService.save(student);
        return "redirect:list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = this.studentService.findById(id);

        model.addAttribute("student",
                StudentView.studentToStudentView(
                        student,
                        student.getGroup(),
                        student.getSpecialism(),
                        student.getAcademicYear()
                )
        );
        model.addAttribute(GROUP_NAMES, this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute(USER_ROLES, Arrays.asList(UserRole.values()));
        return "student/update-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid StudentView studentView, BindingResult result, Model model) {
        Student studentToSave = studentView.studentViewToStudent(
                groupService.findByGroupName(studentView.getGroupName()),
                specialismService.findBySpecialismName(studentView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(studentView.getAcademicYearNumber(), studentView.getSemesterType())
        );

        if (result.hasErrors()) {
            studentView.setId(id);
            return "student/update-student";
        }
        studentToSave.setPasswordHash(passwordEncoder.encode(studentToSave.getPasswordHash()));
        // update student
        studentService.save(studentToSave);

        // get all students ( with update)
        model.addAttribute(STUDENTS, this.studentService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        student.getGroup(),
                        student.getSpecialism(),
                        student.getAcademicYear()
                ))
                .collect(Collectors.toList())
        );
        return STUDENT_STUDENTS_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {

        this.studentService.deleteById(id);
        model.addAttribute(STUDENTS, this.studentService.findAll()
                .stream()
                .map(student -> StudentView.studentToStudentView(
                        student,
                        student.getGroup(),
                        student.getSpecialism(),
                        student.getAcademicYear()
                ))
                .collect(Collectors.toList())
        );
        return STUDENT_STUDENTS_LIST;

    }
}
