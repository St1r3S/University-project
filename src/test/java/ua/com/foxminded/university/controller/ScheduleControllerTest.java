package ua.com.foxminded.university.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.university.model.lesson.*;
import ua.com.foxminded.university.model.schedule.AcademicYear;
import ua.com.foxminded.university.model.schedule.DayOfWeek;
import ua.com.foxminded.university.model.schedule.ScheduleDay;
import ua.com.foxminded.university.model.schedule.SemesterType;
import ua.com.foxminded.university.model.user.AcademicRank;
import ua.com.foxminded.university.model.user.Educator;
import ua.com.foxminded.university.model.user.Group;
import ua.com.foxminded.university.model.user.UserRole;
import ua.com.foxminded.university.model.view.GroupView;
import ua.com.foxminded.university.model.view.LessonView;
import ua.com.foxminded.university.service.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ScheduleController.class})
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LessonService lessonService;

    @MockBean
    DisciplineService disciplineService;
    @MockBean
    GroupService groupService;
    @MockBean
    RoomService roomService;
    @MockBean
    ScheduleDayService scheduleDayService;

    @MockBean
    EducatorService educatorService;
    @MockBean
    AcademicYearService academicYearService;

    @Test
    public void shouldVerifyShowLessonForm() throws Exception {
        String scheduleType = "groups";
        Long contextId = 1L;
        String semesterType = "fall";
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<String> expectedDisciplineNames = List.of(disciplineId1.getDisciplineName(), disciplineId2.getDisciplineName());
        List<String> expectedGroupNames = List.of(groupId1.getGroupName(), groupId2.getGroupName());


        when(disciplineService.findAll()).thenReturn(
                List.of(disciplineId1, disciplineId2)
        );
        when(groupService.findAll()).thenReturn(
                List.of(groupId1, groupId2)
        );
        when(scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType))).thenReturn(
                scheduleDayId1
        );
        when(roomService.findAllFreeRooms(lessonNumber, scheduleDayId1)).thenReturn(
                List.of(roomId2)
        );

        mockMvc.perform(get("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/showForm", scheduleType, contextId, semesterType)
                        .param("lessonNumber", String.valueOf(lessonNumber))
                        .param("dayOfWeek", String.valueOf(dayOfWeek))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("disciplineNames", expectedDisciplineNames))
                .andExpect(model().attribute("groupNames", expectedGroupNames))
                .andExpect(model().attribute("lessonNumber", lessonNumber))
                .andExpect(model().attribute("scheduleDay", scheduleDayId1))
                .andExpect(model().attribute("roomNumbers", List.of(roomId2.getRoomNumber())));
    }

    @Test
    public void shouldVerifyAddLessonToGroup() throws Exception {
        String scheduleType = "groups";
        Long contextId = 1L;
        String semesterType = "fall";
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, null, null);
        Lesson lesson = new Lesson(disciplineId1, groupId1, lessonNumber, roomId1, scheduleDayId1);

        when(disciplineService.findByDisciplineName(disciplineId1.getDisciplineName())).thenReturn(
                disciplineId1
        );
        when(groupService.findById(contextId)).thenReturn(
                groupId1
        );
        when(roomService.findByRoomNumber(roomId1.getRoomNumber())).thenReturn(
                roomId1
        );

        mockMvc.perform(post("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/add", scheduleType, contextId, semesterType)
                        .param("groupName", groupId1.getGroupName())
                        .param("disciplineName", disciplineId1.getDisciplineName())
                        .param("lessonNumber", String.valueOf(lessonNumber))
                        .param("scheduleDay.id", String.valueOf(scheduleDayId1.getId()))
                        .param("roomNumber", roomId1.getRoomNumber())
                        .contentType("application/json")
                )
                .andExpect(status().is3xxRedirection());
        verify(lessonService, times(1)).save(lesson);
    }

    @Test
    public void shouldVerifyAddLessonToEducator() throws Exception {
        String scheduleType = "educators";
        Long contextId = 1L;
        String semesterType = "fall";
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");

        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, null, null);
        Lesson lesson = new Lesson(disciplineId1, groupId1, lessonNumber, roomId1, scheduleDayId1);

        when(disciplineService.findByDisciplineName(disciplineId1.getDisciplineName())).thenReturn(
                disciplineId1
        );
        when(groupService.findByGroupName(groupId1.getGroupName())).thenReturn(
                groupId1
        );
        when(roomService.findByRoomNumber(roomId1.getRoomNumber())).thenReturn(
                roomId1
        );

        mockMvc.perform(post("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/add", scheduleType, contextId, semesterType)
                        .param("groupName", groupId1.getGroupName())
                        .param("disciplineName", disciplineId1.getDisciplineName())
                        .param("lessonNumber", String.valueOf(lessonNumber))
                        .param("scheduleDay.id", String.valueOf(scheduleDayId1.getId()))
                        .param("roomNumber", roomId1.getRoomNumber())
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(lessonService, times(1)).save(lesson);
    }

    @Test
    public void shouldVerifyAddLessonToRoom() throws Exception {
        String scheduleType = "rooms";
        Long contextId = 1L;
        String semesterType = "fall";
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, null, null);
        Lesson lesson = new Lesson(disciplineId1, groupId1, lessonNumber, roomId1, scheduleDayId1);

        when(disciplineService.findByDisciplineName(disciplineId1.getDisciplineName())).thenReturn(
                disciplineId1
        );
        when(groupService.findByGroupName(groupId1.getGroupName())).thenReturn(
                groupId1
        );
        when(roomService.findById(contextId)).thenReturn(
                roomId1
        );

        mockMvc.perform(post("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/add", scheduleType, contextId, semesterType)
                        .param("groupName", groupId1.getGroupName())
                        .param("disciplineName", disciplineId1.getDisciplineName())
                        .param("lessonNumber", String.valueOf(lessonNumber))
                        .param("scheduleDay.id", String.valueOf(scheduleDayId1.getId()))
                        .param("roomNumber", roomId1.getRoomNumber())
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(lessonService, times(1)).save(lesson);
    }

    @Test
    public void shouldVerifyShowGroupsScheduleList() throws Exception {
        Specialism specialism = new Specialism(1L, "Specialism");
        AcademicYear academicYear = new AcademicYear(1L, 1, SemesterType.SPRING_SEMESTER);
        String groupName = "XX-01";
        when(groupService.findAll()).thenReturn(List.of(
                new Group(1L,
                        groupName,
                        specialism,
                        academicYear)));

        List<GroupView> expectedGroups = List.of(
                new GroupView(
                        1L,
                        groupName,
                        specialism.getSpecialismName(),
                        academicYear.getYearNumber(),
                        academicYear.getSemesterType()
                )
        );

        mockMvc.perform(get("/schedule/list/groups")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("groups", expectedGroups));
    }

    @Test
    public void shouldVerifyShowEducatorsScheduleList() throws Exception {
        List<Educator> expectedEducators = List.of(
                new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                        "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR),
                new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                        "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT),
                new Educator(7L, "spy74", "pass74", UserRole.EDUCATOR, "Feder",
                        "Lops", LocalDate.parse("1974-05-25"), "spy@gmail.com", AcademicRank.DOCENT),
                new Educator(8L, "kox55", "pass55", UserRole.EDUCATOR, "Semen",
                        "Dowson", LocalDate.parse("1955-06-22"), "dowson@gmail.com", AcademicRank.DOCENT)
        );
        when(educatorService.findAll()).thenReturn(expectedEducators);

        mockMvc.perform(get("/schedule/list/educators")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("educators", expectedEducators));
    }

    @Test
    public void shouldVerifyShowRoomsScheduleList() throws Exception {
        List<Room> expectedRooms = List.of(
                new Room(1L, "404"),
                new Room(2L, "505"),
                new Room(3L, "606")
        );
        when(roomService.findAll()).thenReturn(expectedRooms);

        mockMvc.perform(get("/schedule/list/rooms")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("rooms", expectedRooms));
    }

    @Test
    public void shouldVerifyShowUpdateForm() throws Exception {
        String scheduleType = "groups";
        Long contextId = 1L;
        String semesterType = "fall";
        Long idToEdit = 1L;
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Educator educatorId6 = new Educator(6L, "jimm80", "pass80", UserRole.EDUCATOR, "Alex",
                "Jimson", LocalDate.parse("1980-03-24"), "alex@gmail.com", AcademicRank.DOCENT);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Discipline disciplineId2 = new Discipline(2L, "Physics", specialismId1, academicYearId1, educatorId6);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Group groupId2 = new Group(2L, "AI-194", specialismId1, academicYearId1);
        Room roomId2 = new Room(2L, "505");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);

        List<String> expectedDisciplineNames = List.of(disciplineId1.getDisciplineName(), disciplineId2.getDisciplineName());
        List<String> expectedGroupNames = List.of(groupId1.getGroupName(), groupId2.getGroupName());
        Lesson lesson = new Lesson(1L, disciplineId1, groupId1, lessonNumber, roomId2, scheduleDayId1);
        LessonView lessonView = new LessonView(1L, disciplineId1.getDisciplineName(), educatorId5,
                groupId1.getGroupName(), lessonNumber, roomId2.getRoomNumber(), scheduleDayId1);

        when(lessonService.findById(idToEdit)).thenReturn(
                lesson
        );
        when(disciplineService.findAll()).thenReturn(
                List.of(disciplineId1, disciplineId2)
        );
        when(groupService.findAll()).thenReturn(
                List.of(groupId1, groupId2)
        );
        when(scheduleDayService.findByDayOfWeekAndSemesterType(dayOfWeek, SemesterType.get(semesterType))).thenReturn(
                scheduleDayId1
        );
        when(roomService.findAllFreeRooms(lessonNumber, scheduleDayId1)).thenReturn(
                List.of(roomId2)
        );

        mockMvc.perform(get("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/edit/{id}", scheduleType, contextId, semesterType, idToEdit)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("lesson", lessonView))
                .andExpect(model().attribute("disciplineNames", expectedDisciplineNames))
                .andExpect(model().attribute("groupNames", expectedGroupNames))
                .andExpect(model().attribute("lessonNumbers", Arrays.asList(LessonNumber.values())))
                .andExpect(model().attribute("roomNumbers", List.of(roomId2.getRoomNumber())));
    }

    @Test
    public void shouldVerifyUpdateLesson() throws Exception {
        String scheduleType = "rooms";
        Long contextId = 1L;
        String semesterType = "fall";
        Long idToUpdate = 1L;
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, null, null);
        Lesson lesson = new Lesson(1L, disciplineId1, groupId1, lessonNumber, roomId1, scheduleDayId1);

        when(disciplineService.findByDisciplineName(disciplineId1.getDisciplineName())).thenReturn(
                disciplineId1
        );
        when(groupService.findByGroupName(groupId1.getGroupName())).thenReturn(
                groupId1
        );
        when(roomService.findByRoomNumber(roomId1.getRoomNumber())).thenReturn(
                roomId1
        );

        mockMvc.perform(post("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/update/{id}", scheduleType, contextId, semesterType, idToUpdate)
                        .param("groupName", groupId1.getGroupName())
                        .param("disciplineName", disciplineId1.getDisciplineName())
                        .param("lessonNumber", String.valueOf(lessonNumber))
                        .param("scheduleDay.id", String.valueOf(scheduleDayId1.getId()))
                        .param("roomNumber", roomId1.getRoomNumber())
                        .contentType("application/json")
                )
                .andExpect(status().is3xxRedirection());
        verify(lessonService, times(1)).save(lesson);
    }

    @Test
    public void shouldVerifyDelete() throws Exception {
        String scheduleType = "rooms";
        Long contextId = 1L;
        String semesterType = "fall";
        Long idToDelete = 1L;
        LessonNumber lessonNumber = LessonNumber.FIRST_LESSON;
        Specialism specialismId1 = new Specialism(1L, "122");
        AcademicYear academicYearId1 = new AcademicYear(1L, 1, SemesterType.FALL_SEMESTER);
        Educator educatorId5 = new Educator(5L, "grant78", "pass78", UserRole.EDUCATOR, "John",
                "Grant", LocalDate.parse("1978-03-28"), "grant@gmail.com", AcademicRank.PROFESSOR);
        Discipline disciplineId1 = new Discipline(1L, "Math", specialismId1, academicYearId1, educatorId5);
        Group groupId1 = new Group(1L, "AI-193", specialismId1, academicYearId1);
        Room roomId1 = new Room(1L, "404");
        ScheduleDay scheduleDayId1 = new ScheduleDay(1L, DayOfWeek.MONDAY, SemesterType.FALL_SEMESTER);
        Lesson lesson = new Lesson(1L, disciplineId1, groupId1, lessonNumber, roomId1, scheduleDayId1);

        when(lessonService.findById(idToDelete)).thenReturn(
                lesson
        );
        doNothing().when(lessonService).delete(lesson);
        mockMvc.perform(get("/schedule/{scheduleType}/edit/{contextId}/{semesterType}/delete/{id}", scheduleType, contextId, semesterType, idToDelete)
                        .contentType("application/json"))
                .andExpect(status().is3xxRedirection());
        verify(lessonService, times(1)).delete(lesson);
    }
}
