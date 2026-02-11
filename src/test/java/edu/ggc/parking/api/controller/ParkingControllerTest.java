package edu.ggc.parking.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ggc.parking.api.dto.CountUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebMvcTest(ParkingController.class)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStatus_whenNoData_returnsDefaults() throws Exception {
        mockMvc.perform(get("/api/v1/lots/A/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lotId").value("A"))
                .andExpect(jsonPath("$.carCount").value(0));
    }

    @Test
    void postCount_returnsOk() throws Exception {
        CountUpdateRequest request = new CountUpdateRequest();
        request.setCarCount(15);
        request.setTimestamp(Instant.parse("2026-02-11T21:00:00Z"));

        mockMvc.perform(post("/api/v1/lots/A/counts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }


    @Test
    void postCount_thenGetStatus_returnsUpdatedData() throws Exception {
        CountUpdateRequest request = new CountUpdateRequest();
        request.setCarCount(42);
        request.setTimestamp(Instant.parse("2026-02-11T22:00:00Z"));

        mockMvc.perform(post("/api/v1/lots/Lot1/counts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/lots/Lot1/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lotId").value("Lot1"))
                .andExpect(jsonPath("$.carCount").value(42));
    }
}
