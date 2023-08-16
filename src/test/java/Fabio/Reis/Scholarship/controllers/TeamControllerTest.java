package Fabio.Reis.Scholarship.controllers;


import Fabio.Reis.Scholarship.model.Commons.IdsList;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamRequestDTO;
import Fabio.Reis.Scholarship.model.teamEntity.teamDTO.TeamsToStatus;
import Fabio.Reis.Scholarship.services.teamService.TeamServiceImpl;
import Fabio.Reis.Scholarship.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamServiceImpl teamServiceImpl;

    String TEAM = "team/team.Json";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    void getAllClasses() throws Exception {
        InternalDTO internal1 = new InternalDTO();
        InternalDTO internal2 = new InternalDTO();
        StudentDTO student1 = new StudentDTO();
        StudentDTO student2 = new StudentDTO();

        Set<InternalDTO> coordinators = new HashSet<>();
        coordinators.add(internal1);
        coordinators.add(internal2);

        Set<StudentDTO> students = new HashSet<>();
        students.add(student1);
        students.add(student2);

        List<TeamDTO> classes = new ArrayList<>();
        classes.add(new TeamDTO(1L, "Class A", "Learning A", "Status A", coordinators,coordinators,coordinators, students));
        classes.add(new TeamDTO(2L, "Class B", "Learning B", "Status B", coordinators, coordinators,coordinators, students));
        TeamsToStatus teamsToStatus = new TeamsToStatus();
        teamsToStatus.setWaintingClasses(classes);
        ResponseEntity<TeamsToStatus> responseEntity = new ResponseEntity<>(teamsToStatus, HttpStatus.OK);
        when(teamServiceImpl.getClasses()).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/classes")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void getClassById() throws Exception {
        InternalDTO internal1 = new InternalDTO();
        InternalDTO internal2 = new InternalDTO();
        StudentDTO student1 = new StudentDTO();
        StudentDTO student2 = new StudentDTO();

        Set<InternalDTO> coordinators = new HashSet<>();
        coordinators.add(internal1);
        coordinators.add(internal2);

        Set<StudentDTO> students = new HashSet<>();
        students.add(student1);
        students.add(student2);

        Long classId = 1L;
        TeamDTO team = new TeamDTO(1L, "Class A", "Learning A", "Status A", coordinators,coordinators,coordinators, students);
        ResponseEntity responseEntity = new ResponseEntity<>(team, HttpStatus.OK);
        when(teamServiceImpl.getClassById(classId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/classes/{id}", classId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Class A"));
    }

    @Test
    void getClassInstructors() throws Exception {
        InternalDTO instructor1 = new InternalDTO();
        InternalDTO instructor2 = new InternalDTO();

        Set<InternalDTO> instructors = new HashSet<>();
        instructors.add(instructor1);
        instructors.add(instructor2);

        Long classId = 1L;
        ResponseEntity responseEntity = new ResponseEntity<>(instructors, HttpStatus.OK);
        when(teamServiceImpl.getClassInstructors(classId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/classes/{id}/instructors", classId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void getScrumMasters() throws Exception {
        InternalDTO scrumMaster1 = new InternalDTO();
        InternalDTO scrumMaster2 = new InternalDTO();

        Set<InternalDTO> scrumMasters = new HashSet<>();
        scrumMasters.add(scrumMaster1);
        scrumMasters.add(scrumMaster2);

        Long classId = 1L;
        ResponseEntity responseEntity = new ResponseEntity<>(scrumMasters, HttpStatus.OK);
        when(teamServiceImpl.getScrumMasters(classId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/classes/{id}/scrum-masters", classId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void getCoordinators() throws Exception {
        InternalDTO coordinator1 = new InternalDTO();
        InternalDTO coordinator2 = new InternalDTO();

        Set<InternalDTO> coordinators = new HashSet<>();
        coordinators.add(coordinator1);
        coordinators.add(coordinator2);

        Long classId = 1L;
        ResponseEntity responseEntity = new ResponseEntity<>(coordinators, HttpStatus.OK);
        when(teamServiceImpl.getCoordinators(classId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/classes/{id}/coordinators", classId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void getStudents() throws Exception {
        StudentDTO student1 = new StudentDTO();
        StudentDTO student2 = new StudentDTO();

        Set<StudentDTO> students = new HashSet<>();
        students.add(student1);
        students.add(student2);

        Long classId = 1L;
        ResponseEntity responseEntity = new ResponseEntity<>(students, HttpStatus.OK);
        when(teamServiceImpl.getStudents(classId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/classes/{id}/students", classId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }


    @Test
    void updateClass() throws Exception {
        Long teamId = 1L;
        String payLoad = JsonUtils.readFileAsString(TEAM);

        when(teamServiceImpl.updateClass(eq(teamId), any(TeamRequestDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        MockHttpServletRequestBuilder builder = put("/v1/classes/{id}", teamId)
                .content(payLoad)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void addInternals() throws Exception {
        String payLoad = JsonUtils.readFileAsString("idsList/idlist.Json");
        IdsList internalsIds = new IdsList();
        Set<Long> ids = new HashSet<>();
        ids.add(1l);
        ids.add(2l);
        ids.add(3l);
        internalsIds.setIds(ids);

        when(teamServiceImpl.addInternalsByIds(anyLong(), any(IdsList.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/v1/classes/{id}/internals", 1L)
                        .content(payLoad)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addStudents() throws Exception {
        String payLoad = JsonUtils.readFileAsString("idsList/idlist.Json");
        IdsList studentsIds = new IdsList();
        Set<Long> ids = new HashSet<>();
        ids.add(1l);
        ids.add(2l);
        ids.add(3l);
        studentsIds.setIds(ids);

        when(teamServiceImpl.addStudentByIds(anyLong(), any(IdsList.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/v1/classes/{id}/students", 1L)
                        .content(payLoad)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void startClass() throws Exception {
        Long teamId = 1L;
        String payLoad = JsonUtils.readFileAsString(TEAM);

        when(teamServiceImpl.startClass(eq(teamId)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        MockHttpServletRequestBuilder builder = put("/v1/classes/{id}/start", teamId)
                .content(payLoad)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void finishClass() throws Exception {
        Long teamId = 1L;
        String payLoad = JsonUtils.readFileAsString(TEAM);

        when(teamServiceImpl.finishClass(eq(teamId)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        MockHttpServletRequestBuilder builder = put("/v1/classes/{id}/finish", teamId)
                .content(payLoad)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    void createTeamWithStudents() throws Exception {
        String payLoad = JsonUtils.readFileAsString(TEAM);
        TeamRequestDTO teamDTO = JsonUtils.getObjectFromFile(TEAM, TeamRequestDTO.class);
        when(teamServiceImpl.createClassWithStudentsAndInternals(any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/v1/classes").content(payLoad).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isCreated());

    }
}