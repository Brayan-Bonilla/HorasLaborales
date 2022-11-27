package com.example.handyMan.service;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.out.ReportRepository;
import com.example.handyMan.application.services.ReportService;
import com.example.handyMan.infrastructure.mappers.domain.ReportDomainMapper;
import com.example.handyMan.infrastructure.models.DAO.ReportDao;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReportServiceTest
{
    @MockBean
    ReportRepository reportRepository;
    @MockBean
    ReportDomainMapper reportDomainMapper;
    @Autowired
    ReportService reportService;

    @Test
    void saveReportTest() throws ParseException
    {
        String prueba = "2022-07-24 10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(prueba);
        Report reportDao = new Report();
        reportDao.setIdTechnical("prueba2");
        reportDao.setIdService("pruebas2");
        reportDao.setStartDate(date);
        reportDao.setEndDate(new Date());
        reportDao.setWeek(30);
        when(reportRepository.saveReport(any(Report.class))).thenReturn(reportDao);
        when(reportDomainMapper.toDomain(any(ReportInDTO.class))).thenReturn(reportDao);
        ReportInDTO reportInDTO = new ReportInDTO();
        reportInDTO.setIdTechnical("Pepito");
        reportInDTO.setIdService("Perez");
        reportInDTO.setStartDate(date);
        reportInDTO.setEndDate(new Date());
        Report report = reportService.saveReport(reportInDTO);
        assertNotNull(report);
        assertEquals("prueba2", report.getIdTechnical());
        assertEquals("pruebas2", report.getIdService());
        verify(reportRepository, times(2)).saveReport(any(Report.class));
        verify(reportDomainMapper).toDomain(any(ReportInDTO.class));
    }
}
