package com.example.handyMan.integration;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ReportControllerWebTestClientTest
{
    private ObjectMapper objectMapper;
    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp()
    {
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveReportTest() throws ParseException
    {
        String prueba = "2022-04-06 5:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(prueba);
        ReportInDTO reportInDTO = new ReportInDTO();
        reportInDTO.setIdTechnical("Juan");
        reportInDTO.setIdService("Valdez");
        reportInDTO.setStartDate(date);
        prueba = "2022-04-08 17:00:00";
        date = sdf.parse(prueba);
        reportInDTO.setEndDate(date);
        client.post().uri("/api/saveReport")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(reportInDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Report.class)
                .consumeWith(response ->
                        {
                            Report report = response.getResponseBody();
                            assertNotNull(report);
                            assertEquals("Juan", report.getIdTechnical());
                            assertEquals("Valdez", report.getIdService());
                            assertEquals(14, report.getWeek());
                        });

    }
}
