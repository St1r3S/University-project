package ua.com.foxminded.university.controller;

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
@RequestMapping("/disciplines/")
public class DisciplineController {

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

    @GetMapping("showForm")
    public String showDisciplineForm(DisciplineView disciplineView, Model model) {
        model.addAttribute("specialismNames", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute("academicYears", this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute("semesterTypes", this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute("educators", this.educatorService.findAllFreeEducators());

        return "discipline/add-discipline";
    }

    @GetMapping("list")
    public String disciplines(Model model) {
        model.addAttribute("disciplines", this.disciplineService.findAll()
                .stream()
                .map(discipline -> DisciplineView.disciplineToDisciplineView(
                        discipline,
                        specialismService.findById(discipline.getSpecialism().getId()),
                        academicYearService.findById(discipline.getAcademicYear().getId()),
                        educatorService.findById(discipline.getEducator().getId())))
                .collect(Collectors.toList())
        );

        return "discipline/disciplines-list";
    }

    @PostMapping("add")
    public String addDiscipline(DisciplineView disciplineView, BindingResult result, Model model) {
        if (result.hasErrors()) {
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


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Discipline discipline = this.disciplineService.findById(id);

        model.addAttribute("discipline",
                DisciplineView.disciplineToDisciplineView(
                        discipline,
                        specialismService.findById(discipline.getSpecialism().getId()),
                        academicYearService.findById(discipline.getAcademicYear().getId()),
                        educatorService.findById(discipline.getEducator().getId())
                )
        );
        model.addAttribute("specialismNames", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute("academicYears", this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute("semesterTypes", this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute("educators", this.educatorService.findAllFreeEducators());

        return "discipline/update-discipline";
    }

    @PostMapping("update/{id}")
    public String updateDiscipline(@PathVariable("id") long id,
                                   DisciplineView disciplineView,
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

        model.addAttribute("disciplines", this.disciplineService.findAll()
                .stream()
                .map(discipline -> DisciplineView.disciplineToDisciplineView(
                        discipline,
                        specialismService.findById(discipline.getSpecialism().getId()),
                        academicYearService.findById(discipline.getAcademicYear().getId()),
                        educatorService.findById(discipline.getEducator().getId())))
                .collect(Collectors.toList())
        );
        model.addAttribute("specialismNames", this.specialismService.findAll().stream().map(Specialism::getSpecialismName).collect(Collectors.toList()));
        model.addAttribute("academicYears", this.academicYearService.findAll().stream().map(AcademicYear::getYearNumber).distinct().collect(Collectors.toList()));
        model.addAttribute("semesterTypes", this.academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        model.addAttribute("educators", this.educatorService.findAllFreeEducators());

        return "discipline/disciplines-list";
    }

    @GetMapping("delete/{id}")
    public String deleteDiscipline(@PathVariable("id") long id, Model model) {
        Discipline disciplineToDelete = this.disciplineService.findById(id);

        this.disciplineService.delete(disciplineToDelete);

        model.addAttribute("disciplines", this.disciplineService.findAll()
                .stream()
                .map(discipline -> DisciplineView.disciplineToDisciplineView(
                        discipline,
                        specialismService.findById(discipline.getSpecialism().getId()),
                        academicYearService.findById(discipline.getAcademicYear().getId()),
                        educatorService.findById(discipline.getEducator().getId())))
                .collect(Collectors.toList())
        );

        return "discipline/disciplines-list";

    }
}
