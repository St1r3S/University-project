package ua.com.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.university.model.lesson.Discipline;
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.model.lesson.Room;
import ua.com.foxminded.university.model.schedule.*;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.view.GroupView;
import ua.com.foxminded.university.model.view.LessonView;
import ua.com.foxminded.university.service.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final LessonService lessonService;

    private final DisciplineService disciplineService;
    private final GroupService groupService;
    private final RoomService roomService;
    private final ScheduleDayService scheduleDayService;

    private final SpecialismService specialismService;
    private final EducatorService educatorService;
    private final AcademicYearService academicYearService;


    public ScheduleController(LessonService lessonService, DisciplineService disciplineService, GroupService groupService, RoomService roomService, ScheduleDayService scheduleDayService, SpecialismService specialismService, EducatorService educatorService, AcademicYearService academicYearService) {
        this.lessonService = lessonService;
        this.disciplineService = disciplineService;
        this.groupService = groupService;
        this.roomService = roomService;
        this.scheduleDayService = scheduleDayService;
        this.specialismService = specialismService;
        this.educatorService = educatorService;
        this.academicYearService = academicYearService;
    }

    @GetMapping("/{scheduleType}/edit/{contextId}/{semesterType}/showForm")
    public String showLessonForm(@PathVariable("scheduleType") String scheduleType,
                                 @PathVariable("contextId") long contextId,
                                 @PathVariable("semesterType") String semesterType,
                                 LessonNumber lessonNumber,
                                 DayOfWeek dayOfWeek,
                                 LessonView lessonView,
                                 Model model) {
        model.addAttribute("disciplineNames", this.disciplineService.findAll().stream().map(Discipline::getDisciplineName).collect(Collectors.toList()));
        model.addAttribute("groupNames", this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute("lessonNumber", lessonNumber);
        model.addAttribute("scheduleDay", scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType)));
        model.addAttribute("roomNumbers", this.roomService.findAllFreeRooms(lessonNumber, scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType))).stream().map(Room::getRoomNumber).collect(Collectors.toList()));

        return "schedule/add-lesson";
    }

    @PostMapping("/{scheduleType}/edit/{contextId}/{semesterType}/add")
    public String addLesson(@PathVariable("scheduleType") String scheduleType,
                            @PathVariable("contextId") long contextId,
                            @PathVariable("semesterType") String semesterType,
                            LessonView lessonView,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "redirect:/schedule/" + scheduleType + "/edit/" + contextId + "/" + semesterType;
        }
        Lesson lessonToSave;
        switch (scheduleType) {
            case "groups":
                lessonToSave = lessonView.lessonViewToLesson(
                        disciplineService.findByDisciplineName(lessonView.getDisciplineName()),
                        groupService.findById(contextId),
                        roomService.findByRoomNumber(lessonView.getRoomNumber())
                );
                break;
            case "educators":
                lessonToSave = lessonView.lessonViewToLesson(
                        disciplineService.findByDisciplineName(lessonView.getDisciplineName()),
                        groupService.findByGroupName(lessonView.getGroupName()),
                        roomService.findByRoomNumber(lessonView.getRoomNumber())
                );
                break;
            case "rooms":
                lessonToSave = lessonView.lessonViewToLesson(
                        disciplineService.findByDisciplineName(lessonView.getDisciplineName()),
                        groupService.findByGroupName(lessonView.getGroupName()),
                        roomService.findById(contextId)
                );
                break;
            default:
                throw new NullPointerException("There's no such schedule type!");
        }


        this.lessonService.save(lessonToSave);

        return "redirect:/schedule/" + scheduleType + "/edit/" + contextId + "/" + semesterType;
    }


    @GetMapping("/list/{scheduleListType}")
    public String showScheduleLists(@PathVariable("scheduleListType") String scheduleListType, Model model) {
        model.addAttribute("scheduleListTypes", Arrays.asList(ScheduleListType.values()));

        switch (scheduleListType) {
            case "groups":
                model.addAttribute("groups", this.groupService.findAll()
                        .stream()
                        .map(group -> GroupView.groupToGroupView(
                                group,
                                specialismService.findById(group.getSpecialismId()),
                                academicYearService.findById(group.getAcademicYearId())))
                        .collect(Collectors.toList())
                );
                break;
            case "educators":
                model.addAttribute("educators", this.educatorService.findAll());
                break;
            case "rooms":
                model.addAttribute("rooms", this.roomService.findAll());
                break;
        }
        return "schedule/choose-schedule";
    }

    @GetMapping("/{scheduleType}/edit/{contextId}/{semesterType}")
    public String showSchedule(@PathVariable("scheduleType") String scheduleType,
                               @PathVariable("contextId") long contextId,
                               @PathVariable("semesterType") String semesterType,
                               Model model) {
        makeModelToShowSchedule(scheduleType, contextId, semesterType, model);
        return "schedule/show-schedule";
    }

    @GetMapping("/{scheduleType}/edit/{contextId}/{semesterType}/edit/{id}")
    public String showUpdateForm(@PathVariable("scheduleType") String scheduleType,
                                 @PathVariable("contextId") long contextId,
                                 @PathVariable("semesterType") String semesterType,
                                 @PathVariable("id") long idToEdit,
                                 Model model) {
        Lesson lesson = this.lessonService.findById(idToEdit);

        LessonView lessonView = LessonView.lessonToLessonView(
                lesson,
                disciplineService.findById(lesson.getDisciplineId()),
                educatorService.findById(disciplineService.findById(lesson.getDisciplineId()).getEducatorId()),
                groupService.findById(lesson.getGroupId()),
                roomService.findById(lesson.getRoomId()),
                scheduleDayService.findById(lesson.getScheduleDayId())
        );

        model.addAttribute("lesson",
                lessonView
        );
        model.addAttribute("disciplineNames", this.disciplineService.findAll().stream().map(Discipline::getDisciplineName).collect(Collectors.toList()));
        model.addAttribute("groupNames", this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute("lessonNumbers", Arrays.asList(LessonNumber.values()));
        model.addAttribute("roomNumbers", this.roomService.findAllFreeRooms(lessonView.getLessonNumber(), lessonView.getScheduleDay()).stream().map(Room::getRoomNumber).collect(Collectors.toList()));

        return "schedule/update-lesson";
    }

    @PostMapping("/{scheduleType}/edit/{contextId}/{semesterType}/update/{id}")
    public String updateLesson(@PathVariable("scheduleType") String scheduleType,
                               @PathVariable("contextId") long contextId,
                               @PathVariable("semesterType") String semesterType,
                               @PathVariable("id") long idToUpdate,
                               LessonView lessonView,
                               BindingResult result,
                               Model model) {
        Lesson lessonToSave = lessonView.lessonViewToLesson(
                disciplineService.findByDisciplineName(lessonView.getDisciplineName()),
                groupService.findByGroupName(lessonView.getGroupName()),
                roomService.findByRoomNumber(lessonView.getRoomNumber())
        );

        if (result.hasErrors()) {
            lessonView.setId(idToUpdate);
            return "schedule/update-lesson";
        }

        this.lessonService.save(lessonToSave);

        return "redirect:/schedule/" + scheduleType + "/edit/" + contextId + "/" + semesterType;
    }

    @GetMapping("/{scheduleType}/edit/{contextId}/{semesterType}/delete/{id}")
    public String deleteLesson(@PathVariable("scheduleType") String scheduleType,
                               @PathVariable("contextId") long contextId,
                               @PathVariable("semesterType") String semesterType,
                               @PathVariable("id") long idToDelete,
                               Model model) {
        Lesson lessonToDelete = lessonService.findById(idToDelete);
        lessonService.delete(lessonToDelete);
        return "redirect:/schedule/" + scheduleType + "/edit/" + contextId + "/" + semesterType;
    }


    private void makeModelToShowSchedule(String scheduleType,
                                         long contextId,
                                         String semesterType,
                                         Model model) {
        model.addAttribute("scheduleListTypes", Arrays.asList(ScheduleListType.values()));
        model.addAttribute("semesterTypes", academicYearService.findAll().stream().map(AcademicYear::getSemesterType).distinct().collect(Collectors.toList()));
        List<LessonView> lessons;
        Map<LessonNumber, List<LessonView>> mappedLessons = new LinkedHashMap<>();
        switch (scheduleType) {
            case "groups":
                lessons = lessonService.findAllByGroupId(contextId)
                        .stream()
                        .filter(lesson -> scheduleDayService.findBySemesterType(SemesterType.get(semesterType))
                                .stream()
                                .map(ScheduleDay::getId)
                                .collect(Collectors.toList())
                                .contains(lesson.getScheduleDayId()))
                        .map(lesson -> LessonView.lessonToLessonView(
                                lesson,
                                disciplineService.findById(lesson.getDisciplineId()),
                                educatorService.findById(disciplineService.findById(lesson.getDisciplineId()).getEducatorId()),
                                groupService.findById(lesson.getGroupId()),
                                roomService.findById(lesson.getRoomId()),
                                scheduleDayService.findById(lesson.getScheduleDayId())))
                        .collect(Collectors.toList());
                break;
            case "educators":
                lessons = lessonService.findAllByDisciplineId(disciplineService.findByEducatorId(contextId).getId())
                        .stream()
                        .filter(lesson -> scheduleDayService.findBySemesterType(SemesterType.get(semesterType))
                                .stream()
                                .map(ScheduleDay::getId)
                                .collect(Collectors.toList())
                                .contains(lesson.getScheduleDayId()))
                        .map(lesson -> LessonView.lessonToLessonView(
                                lesson,
                                disciplineService.findById(lesson.getDisciplineId()),
                                educatorService.findById(disciplineService.findById(lesson.getDisciplineId()).getEducatorId()),
                                groupService.findById(lesson.getGroupId()),
                                roomService.findById(lesson.getRoomId()),
                                scheduleDayService.findById(lesson.getScheduleDayId())))
                        .collect(Collectors.toList());
                break;
            case "rooms":
                lessons = lessonService.findAllByRoomId(contextId)
                        .stream()
                        .filter(lesson -> scheduleDayService.findBySemesterType(SemesterType.get(semesterType))
                                .stream()
                                .map(ScheduleDay::getId)
                                .collect(Collectors.toList())
                                .contains(lesson.getScheduleDayId()))
                        .map(lesson -> LessonView.lessonToLessonView(
                                lesson,
                                disciplineService.findById(lesson.getDisciplineId()),
                                educatorService.findById(disciplineService.findById(lesson.getDisciplineId()).getEducatorId()),
                                groupService.findById(lesson.getGroupId()),
                                roomService.findById(lesson.getRoomId()),
                                scheduleDayService.findById(lesson.getScheduleDayId())))
                        .collect(Collectors.toList());
                break;
            default:
                throw new NullPointerException("There's no such schedule type!");
        }
        for (LessonNumber lessonNumber : LessonNumber.values()) {
            List<LessonView> tempLessons = lessons.stream()
                    .filter(lesson -> lesson.getLessonNumber().equals(lessonNumber))
                    .collect(Collectors.toList());
            while (tempLessons.size() != DayOfWeek.values().length) {
                tempLessons.add(new LessonView());
            }
            for (DayOfWeek day : DayOfWeek.values()) {
                for (LessonView tempLesson : tempLessons) {
                    if (!Objects.isNull(tempLesson.getScheduleDay())) {
                        if (tempLesson.getScheduleDay().getDayOfWeek().equals(day) &&
                                tempLessons.indexOf(tempLesson) != day.ordinal()) {
                            Collections.swap(tempLessons, tempLessons.indexOf(tempLesson), day.ordinal());
                            break;
                        }
                    } else {
                        if (!tempLessons.stream().filter(lessonView -> !Objects.isNull(lessonView.getScheduleDay())).map(lessonView -> lessonView.getScheduleDay().getDayOfWeek()).collect(Collectors.toList()).contains(day)) {
                            tempLesson.setScheduleDay(scheduleDayService.findByDayOfWeekAndSemesterType(day, SemesterType.get(semesterType)));
                            tempLesson.setLessonNumber(lessonNumber);
                            break;
                        }
                    }
                }
            }
            mappedLessons.putIfAbsent(
                    lessonNumber,
                    tempLessons
            );
        }
        model.addAttribute("mappedLessons", mappedLessons);
    }
}
