package Fabio.Reis.Scholarship.controllers;

import Fabio.Reis.Scholarship.model.squadEntity.squadDTO.SquadDTO;
import Fabio.Reis.Scholarship.model.studentEntity.studentDTO.StudentDTO;
import Fabio.Reis.Scholarship.services.squadService.impl.SquadService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class SquadControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private SquadController squadController;

    @Mock
    private SquadService squadService;

    @Spy
    private ModelMapper modelMapper;

    private static final String SQUAD = "squad/squad.Json";

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(squadController).build();
    }

    @Test
    void getSquads() throws Exception {
        List<SquadDTO> squads = new ArrayList<>();
        Set<StudentDTO> students = new HashSet<>();
        squads.add(new SquadDTO(1l,"Alpha Squad",students));
        squads.add(new SquadDTO(2l,"Beta Squad",students));

        ResponseEntity<List<SquadDTO>> responseEntity = new ResponseEntity<>(squads, HttpStatus.OK);
            when(squadService.getSquads()).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/squads")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Alpha Squad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Beta Squad"));
    }

    @Test
    void getSquadById() throws Exception {
        Long squadId = 1L;
        Set<StudentDTO> students = new HashSet<>();
        SquadDTO squad = new SquadDTO(1l,"Alpha Squad",students);
        ResponseEntity<SquadDTO> responseEntity = new ResponseEntity<>(squad, HttpStatus.OK);
        when(squadService.findById(squadId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/squads/{id}", squadId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alpha Squad"));
    }
}
