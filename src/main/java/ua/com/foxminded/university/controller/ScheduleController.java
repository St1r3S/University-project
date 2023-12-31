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
import ua.com.foxminded.university.model.lesson.Lesson;
import ua.com.foxminded.university.model.lesson.LessonNumber;
import ua.com.foxminded.university.model.lesson.Room;
import ua.com.foxminded.university.model.schedule.*;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.user.User;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.view.GroupView;
import ua.com.foxminded.university.model.view.LessonView;
import ua.com.foxminded.university.service.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    public static final String DISCIPLINE_NAMES = "disciplineNames";
    public static final String GROUP_NAMES = "groupNames";
    public static final String LESSON_NUMBER = "lessonNumber";
    public static final String SCHEDULE_DAY = "scheduleDay";
    public static final String ROOM_NUMBERS = "roomNumbers";
    public static final String DAY_OF_WEEK = "dayOfWeek";
    public static final String EDIT = "/edit/";
    public static final String GROUPS = "groups";
    public static final String EDUCATORS = "educators";
    public static final String ROOMS = "rooms";
    public static final String REDIRECT_SCHEDULE = "redirect:/schedule/";
    private final LessonService lessonService;

    private final DisciplineService disciplineService;
    private final GroupService groupService;
    private final RoomService roomService;
    private final ScheduleDayService scheduleDayService;

    private final EducatorService educatorService;
    private final AcademicYearService academicYearService;

    private final StudentService studentService;
    private final UserService userService;

    public ScheduleController(LessonService lessonService, DisciplineService disciplineService, GroupService groupService, RoomService roomService, ScheduleDayService scheduleDayService, EducatorService educatorService, AcademicYearService academicYearService, StudentService studentService, UserService userService) {
        this.lessonService = lessonService;
        this.disciplineService = disciplineService;
        this.groupService = groupService;
        this.roomService = roomService;
        this.scheduleDayService = scheduleDayService;
        this.educatorService = educatorService;
        this.academicYearService = academicYearService;
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping("/{scheduleType}/edit/{contextId}/{semesterType}/showForm")
    public String showLessonForm(@PathVariable("scheduleType") String scheduleType,
                                 @PathVariable("contextId") long contextId,
                                 @PathVariable("semesterType") String semesterType,
                                 LessonNumber lessonNumber,
                                 DayOfWeek dayOfWeek,
                                 LessonView lessonView,
                                 Model model) {
        model.addAttribute(DISCIPLINE_NAMES, this.disciplineService.findAll().stream().map(Discipline::getDisciplineName).collect(Collectors.toList()));
        model.addAttribute(GROUP_NAMES, this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute(LESSON_NUMBER, lessonNumber);
        model.addAttribute(SCHEDULE_DAY, scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType)));
        model.addAttribute(ROOM_NUMBERS, this.roomService.findAllFreeRooms(lessonNumber, scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType))).stream().map(Room::getRoomNumber).collect(Collectors.toList()));
        model.addAttribute(DAY_OF_WEEK, dayOfWeek);

        return "schedule/add-lesson";
    }

    @PostMapping("/{scheduleType}/edit/{contextId}/{semesterType}/add")
    public String addLesson(@PathVariable("scheduleType") String scheduleType,
                            @PathVariable("contextId") long contextId,
                            @PathVariable("semesterType") String semesterType,
                            @Valid LessonView lessonView,
                            LessonNumber lessonNumber,
                            DayOfWeek dayOfWeek,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute(DISCIPLINE_NAMES, this.disciplineService.findAll().stream().map(Discipline::getDisciplineName).collect(Collectors.toList()));
            model.addAttribute(GROUP_NAMES, this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
            model.addAttribute(LESSON_NUMBER, lessonNumber);
            model.addAttribute(SCHEDULE_DAY, scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType)));
            model.addAttribute(ROOM_NUMBERS, this.roomService.findAllFreeRooms(lessonNumber, scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType))).stream().map(Room::getRoomNumber).collect(Collectors.toList()));
            model.addAttribute(DAY_OF_WEEK, dayOfWeek);

            return "/schedule/" + scheduleType + EDIT + contextId + "/" + semesterType + "/add";
        }
        Lesson lessonToSave;
        switch (scheduleType) {
            case GROUPS:
                lessonToSave = lessonView.lessonViewToLesson(
                        disciplineService.findByDisciplineName(lessonView.getDisciplineName()),
                        groupService.findById(contextId),
                        roomService.findByRoomNumber(lessonView.getRoomNumber())
                );
                break;
            case EDUCATORS:
                lessonToSave = lessonView.lessonViewToLesson(
                        disciplineService.findByDisciplineName(lessonView.getDisciplineName()),
                        groupService.findByGroupName(lessonView.getGroupName()),
                        roomService.findByRoomNumber(lessonView.getRoomNumber())
                );
                break;
            case ROOMS:
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

        return REDIRECT_SCHEDULE + scheduleType + EDIT + contextId + "/" + semesterType;
    }


    @GetMapping("/list/{scheduleListType}")
    public String showScheduleLists(@PathVariable("scheduleListType") String scheduleListType, Model model) {
        model.addAttribute("scheduleListTypes", Arrays.asList(ScheduleListType.values()));

        switch (scheduleListType) {
            case GROUPS:
                model.addAttribute(GROUPS, this.groupService.findAll()
                        .stream()
                        .map(group -> GroupView.groupToGroupView(
                                group,
                                group.getSpecialism(),
                                group.getAcademicYear()
                        ))
                        .collect(Collectors.toList())
                );
                break;
            case EDUCATORS:
                model.addAttribute(EDUCATORS, this.educatorService.findAll());
                break;
            case ROOMS:
                model.addAttribute(ROOMS, this.roomService.findAll());
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

    @GetMapping("/{semesterType}")
    public String showScheduleByUser(@PathVariable("semesterType") String semesterType,
                                     Model model,
                                     Principal principal) {
        User user = userService.findByUsername(principal.getName());
        String scheduleType;
        long contextId;
        if (user.getUserRole() == UserRole.EDUCATOR) {
            scheduleType = EDUCATORS;
            contextId = user.getId();
        } else if (user.getUserRole() == UserRole.STUDENT) {
            scheduleType = GROUPS;
            contextId = studentService.findById(user.getId()).getGroup().getId();
        } else {
            throw new IllegalArgumentException("Invalid user type!");
        }
        model.addAttribute("scheduleType", scheduleType);
        model.addAttribute("contextId", contextId);

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
                lesson.getDiscipline(),
                lesson.getDiscipline().getEducator(),
                lesson.getGroup(),
                lesson.getRoom(),
                lesson.getScheduleDay()
        );

        model.addAttribute("lesson",
                lessonView
        );
        model.addAttribute(DISCIPLINE_NAMES, this.disciplineService.findAll().stream().map(Discipline::getDisciplineName).collect(Collectors.toList()));
        model.addAttribute(GROUP_NAMES, this.groupService.findAll().stream().map(Group::getGroupName).collect(Collectors.toList()));
        model.addAttribute("lessonNumbers", Arrays.asList(LessonNumber.values()));
        model.addAttribute(ROOM_NUMBERS, this.roomService.findAllFreeRooms(lessonView.getLessonNumber(), lessonView.getScheduleDay()).stream().map(Room::getRoomNumber).collect(Collectors.toList()));

        return "schedule/update-lesson";
    }

    @PostMapping("/{scheduleType}/edit/{contextId}/{semesterType}/update/{id}")
    public String updateLesson(@PathVariable("scheduleType") String scheduleType,
                               @PathVariable("contextId") long contextId,
                               @PathVariable("semesterType") String semesterType,
                               @PathVariable("id") long idToUpdate,
                               @Valid LessonView lessonView,
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

        return REDIRECT_SCHEDULE + scheduleType + EDIT + contextId + "/" + semesterType;
    }

    @GetMapping("/{scheduleType}/edit/{contextId}/{semesterType}/delete/{id}")
    public String deleteLesson(@PathVariable("scheduleType") String scheduleType,
                               @PathVariable("contextId") long contextId,
                               @PathVariable("semesterType") String semesterType,
                               @PathVariable("id") long idToDelete,
                               Model model) {
        Lesson lessonToDelete = lessonService.findById(idToDelete);
        lessonService.delete(lessonToDelete);
        return REDIRECT_SCHEDULE + scheduleType + EDIT + contextId + "/" + semesterType;
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
            case GROUPS:
                lessons = lessonService.findAllByGroupId(contextId)
                        .stream()
                        .filter(lesson -> scheduleDayService.findBySemesterType(SemesterType.get(semesterType))
                                .stream()
                                .map(ScheduleDay::getId)
                                .collect(Collectors.toList())
                                .contains(lesson.getScheduleDay().getId()))
                        .map(lesson -> LessonView.lessonToLessonView(
                                lesson,
                                lesson.getDiscipline(),
                                lesson.getDiscipline().getEducator(),
                                lesson.getGroup(),
                                lesson.getRoom(),
                                lesson.getScheduleDay()
                        ))
                        .collect(Collectors.toList());
                break;
            case EDUCATORS:
                lessons = lessonService.findAllByDisciplineId(disciplineService.findByEducatorId(contextId).getId())
                        .stream()
                        .filter(lesson -> scheduleDayService.findBySemesterType(SemesterType.get(semesterType))
                                .stream()
                                .map(ScheduleDay::getId)
                                .collect(Collectors.toList())
                                .contains(lesson.getScheduleDay().getId()))
                        .map(lesson -> LessonView.lessonToLessonView(
                                lesson,
                                lesson.getDiscipline(),
                                lesson.getDiscipline().getEducator(),
                                lesson.getGroup(),
                                lesson.getRoom(),
                                lesson.getScheduleDay()
                        ))
                        .collect(Collectors.toList());
                break;
            case ROOMS:
                lessons = lessonService.findAllByRoomId(contextId)
                        .stream()
                        .filter(lesson -> scheduleDayService.findBySemesterType(SemesterType.get(semesterType))
                                .stream()
                                .map(ScheduleDay::getId)
                                .collect(Collectors.toList())
                                .contains(lesson.getScheduleDay().getId()))
                        .map(lesson -> LessonView.lessonToLessonView(
                                lesson,
                                lesson.getDiscipline(),
                                lesson.getDiscipline().getEducator(),
                                lesson.getGroup(),
                                lesson.getRoom(),
                                lesson.getScheduleDay()
                        ))
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
