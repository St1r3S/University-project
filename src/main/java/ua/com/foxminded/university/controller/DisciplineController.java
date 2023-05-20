package ua.com.foxminded.university.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Specialism;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.view.DisciplineView;
import ua.com.foxminded.university.service.AcademicYearService;
import ua.com.foxminded.university.service.DisciplineService;
import ua.com.foxminded.university.service.EducatorService;
import ua.com.foxminded.university.service.SpecialismService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/disciplines")
public class DisciplineController {

    public static final String SPECIALISM_NAMES = "specialismNames";
    public static final String ACADEMIC_YEARS = "academicYears";
    public static final String SEMESTER_TYPES = "semesterTypes";
    public static final String EDUCATORS = "educators";
    public static final String DISCIPLINES = "disciplines";
    public static final String DISCIPLINE_DISCIPLINES_LIST = "discipline/disciplines-list";
    private final DisciplineService disciplineService;

    private final SpecialismService specialismService;
    private final AcademicYearService academicYearService;
    private final EducatorService educatorService;


    public DisciplineController(DisciplineService disciplineService, SpecialismService specialismService, AcademicYearService academicYearService, EducatorService educatorService) {
        this.disciplineService = disciplineService;
        this.specialismService = specialismService;
        this.academicYearService = academicYearService;
        this.educatorService = educatorService;
    }

    @GetMapping("/showForm")
    public String showDisciplineForm(DisciplineView disciplineView, Model model) {
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute(EDUCATORS, this.educatorService.findAllFreeEducators());

        return "discipline/add-discipline";
    }

    @GetMapping("/list")
    public String disciplines(Model model) {
        model.addAttribute(DISCIPLINES, this.disciplineService.findAll()
                .stream()
                .map(discipline -> DisciplineView.disciplineToDisciplineView(
                        discipline,
                        discipline.getSpecialism(),
                        discipline.getAcademicYear(),
                        discipline.getEducator()
                ))
                .collect(Collectors.toList())
        );

        return DISCIPLINE_DISCIPLINES_LIST;
    }

    @PostMapping("/add")
    public String addDiscipline(@Valid DisciplineView disciplineView, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
            model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
            model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
            model.addAttribute(EDUCATORS, this.educatorService.findAllFreeEducators());

            return "discipline/add-discipline";
        }

        Discipline disciplineToSave = disciplineView.disciplineViewToDiscipline(
                specialismService.findBySpecialismName(disciplineView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(disciplineView.getAcademicYearNumber(), disciplineView.getSemesterType()),
                educatorService.findById(disciplineView.getEducator().getId())
        );

        this.disciplineService.save(disciplineToSave);

        return "redirect:list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Discipline discipline = this.disciplineService.findById(id);

        model.addAttribute("discipline",
                DisciplineView.disciplineToDisciplineView(
                        discipline,
                        discipline.getSpecialism(),
                        discipline.getAcademicYear(),
                        discipline.getEducator()
                )
        );
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute(EDUCATORS, this.educatorService.findAllFreeEducators());

        return "discipline/update-discipline";
    }

    @PostMapping("/update/{id}")
    public String updateDiscipline(@PathVariable("id") long id,
                                   @Valid DisciplineView disciplineView,
                                   BindingResult result,
                                   Model model) {
        Discipline disciplineToSave = disciplineView.disciplineViewToDiscipline(
                specialismService.findBySpecialismName(disciplineView.getSpecialismName()),
                academicYearService.findByYearNumberAndSemesterType(disciplineView.getAcademicYearNumber(), disciplineView.getSemesterType()),
                educatorService.findById(disciplineView.getEducator().getId())
        );

        if (result.hasErrors()) {
            disciplineView.setId(id);
            return "discipline/update-discipline";
        }

        this.disciplineService.save(disciplineToSave);

        model.addAttribute(DISCIPLINES, this.disciplineService.findAll()
                .stream()
                .map(discipline -> DisciplineView.disciplineToDisciplineView(
                        discipline,
                        discipline.getSpecialism(),
                        discipline.getAcademicYear(),
                        discipline.getEducator()
                ))
                .collect(Collectors.toList())
        );
        model.addAttribute(SPECIALISM_NAMES, this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute(ACADEMIC_YEARS, this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute(SEMESTER_TYPES, this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute(EDUCATORS, this.educatorService.findAllFreeEducators());

        return DISCIPLINE_DISCIPLINES_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteDiscipline(@PathVariable("id") long id, Model model) {
        Discipline disciplineToDelete = this.disciplineService.findById(id);

        this.disciplineService.delete(disciplineToDelete);

        model.addAttribute(DISCIPLINES, this.disciplineService.findAll()
                .stream()
                .map(discipline -> DisciplineView.disciplineToDisciplineView(
                        discipline,
                        discipline.getSpecialism(),
                        discipline.getAcademicYear(),
                        discipline.getEducator()
                ))
                .collect(Collectors.toList())
        );

        return DISCIPLINE_DISCIPLINES_LIST;

    }
}
