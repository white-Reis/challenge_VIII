package Fabio.Reis.Scholarship.controllers;



import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalDTO;
import Fabio.Reis.Scholarship.model.internalEntity.internaDTO.InternalRequestDTO;
import Fabio.Reis.Scholarship.services.Internal.impl.InternalServiceImpl;
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
class InternalControllerTest {


    private MockMvc mockMvc;

    @InjectMocks
    private InternalController internalController;

    @Mock
    private InternalServiceImpl internalServiceImpl;

    @Spy
    private ModelMapper modelMapper;
    private static final String INTERNAL = "internal/internal.Json";


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(internalController).build();
    }

    @Test
    void createInternalSucess() throws Exception {
        String payLoad = JsonUtils.readFileAsString(INTERNAL);
        InternalRequestDTO internalDTO = JsonUtils.getObjectFromFile(INTERNAL, InternalRequestDTO.class);
       when(internalServiceImpl.create(any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/internals").content(payLoad).contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isCreated());

    }


    @Test
    void deleteInternal() throws Exception {
        Long internalId = 1L;

        when(internalServiceImpl.delete(anyLong())).thenReturn(ResponseEntity.noContent().build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/v1/internals/{id}", internalId);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void updateInternal() throws Exception {
        Long internalId = 1L;
        String payLoad = JsonUtils.readFileAsString(INTERNAL);

        when(internalServiceImpl.update(eq(internalId), any(InternalRequestDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/api/v1/internals/{id}", internalId)
                .content(payLoad)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getAllInternals() throws Exception {
        List<InternalDTO> internals = new ArrayList<>();
        internals.add(new InternalDTO(1L, "giovani", "Algenor", "giovani@example.com", "ADM", "Coordinator"));
        internals.add(new InternalDTO(2L, "Jane ", "silva", "jane@example.com", "ADM", "Coordinator"));

        ResponseEntity<List<InternalDTO>> responseEntity = new ResponseEntity<>(internals, HttpStatus.OK);
        when(internalServiceImpl.getAll()).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/internals")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(internals.size()));

        verify(internalServiceImpl).getAll();
    }

    @Test
    void getStudentById() throws Exception {
        Long internalId = 1L;
        InternalDTO internal = new InternalDTO(internalId, "giovani", "Algenor", "giovani@example.com", "ADM", "Coordinator");

        ResponseEntity<InternalDTO> responseEntity = new ResponseEntity<>(internal, HttpStatus.OK);
        when(internalServiceImpl.getById(internalId)).thenReturn(responseEntity);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/internals/{id}", internalId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(internalId));

        verify(internalServiceImpl).getById(internalId);
    }
}