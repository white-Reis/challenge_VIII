package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentRequestDTO;
import Fabio.Reis.Scholarship.services.studentService.impl.StudentServiceImpl;
import Fabio.Reis.Scholarship.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class StudentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentServiceImpl studentServiceImpl;

    @Spy
    private ModelMapper modelMapper;

    private static final String STUDENT = "student/student.Json";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void createStudentSuccess() throws Exception {
        String payLoad = JsonUtils.readFileAsString(STUDENT);
        StudentRequestDTO studentDTO = JsonUtils.getObjectFromFile(STUDENT, StudentRequestDTO.class);

        when(studentServiceImpl.create(any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/students")
                .content(payLoad)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void deleteStudent() throws Exception {
        Long studentId = 1L;

        when(studentServiceImpl.delete(anyLong())).thenReturn(ResponseEntity.noContent().build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/v1/students/{id}", studentId);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void updateStudent() throws Exception {
        Long studentId = 1L;
        String payLoad = JsonUtils.readFileAsString(STUDENT);

        when(studentServiceImpl.update(eq(studentId), any(StudentRequestDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/api/v1/students/{id}", studentId)
                .content(payLoad)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllStudents() throws Exception {
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO(1L, "giovani", "Algenor", "giovani@example.com", "Data Science", 2));
        students.add(new StudentDTO(2L, "Jane ", "silva", "jane@example.com", "SI", 3));

        ResponseEntity<List<StudentDTO>> responseEntity = new ResponseEntity<>(students, HttpStatus.OK);
        when(studentServiceImpl.getAll()).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(students.size()));

        verify(studentServiceImpl).getAll();
    }

    @Test
    void getStudentById() throws Exception {
        Long studentId = 1L;
        StudentDTO student = new StudentDTO(studentId, "giovani", "Algenor", "giovani@example.com", "Data Science", 2);

        ResponseEntity<StudentDTO> responseEntity = new ResponseEntity<>(student, HttpStatus.OK);
        when(studentServiceImpl.getById(studentId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId));

        verify(studentServiceImpl).getById(studentId);
    }

}