package com.example.handyMan.controller;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.in.ReportUseCase;
import com.example.handyMan.application.ports.in.WeekCalculatorUseCase;
import com.example.handyMan.infrastructure.adapters.in.ReportController;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
public class ReportControllerTest
{
    @MockBean
    private ReportUseCase reportUseCase;

    @MockBean
    private WeekCalculatorUseCase weekCalculatorUseCase;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveReportTest() throws Exception
    {
        String prueba = "2022-07-24 10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(prueba);
        Report reportDao = new Report();
        reportDao.setId(100);
        reportDao.setIdTechnical("Pepito");
        reportDao.setIdService("Perez");
        reportDao.setStartDate(date);
        reportDao.setEndDate(new Date());
        reportDao.setWeek(30);
        ReportInDTO reportInDTO = new ReportInDTO();
        reportInDTO.setIdTechnical("Pepito");
        reportInDTO.setIdService("Perez");
        reportInDTO.setStartDate(date);
        reportInDTO.setEndDate(new Date());
        when(reportUseCase.saveReport(any(ReportInDTO.class))).thenReturn(reportDao);

        mockMvc.perform(post("/api/saveReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportInDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idTechnical").value("Pepito"))
                .andExpect(jsonPath("$.idService").value("Perez"))
                .andExpect(jsonPath("$.week").value(30));

    }
}
