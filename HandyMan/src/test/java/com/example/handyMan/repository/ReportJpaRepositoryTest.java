package com.example.handyMan.repository;

import com.example.handyMan.infrastructure.configurations.JpaRepository.ReportJpaRepository;
import com.example.handyMan.infrastructure.models.DAO.ReportDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReportJpaRepositoryTest
{
    @Autowired
    ReportJpaRepository reportJpaRepository;

    @Test
    void testSaveReport() throws ParseException
    {
        String prueba = "2022-07-24 10:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(prueba);
        ReportDao reportDao = new ReportDao();
        reportDao.setIdTechnical("prueba6");
        reportDao.setIdService("pruebas");
        reportDao.setStartDate(date);
        reportDao.setEndDate(new Date());
        reportDao.setWeek(30);
        ReportDao saveReport = reportJpaRepository.save(reportDao);
        assertNotNull(saveReport);
        assertTrue(saveReport.getId() != null && saveReport.getId() > 0);
        assertEquals("prueba6",saveReport.getIdTechnical());
        assertEquals("pruebas",saveReport.getIdService());
    }

    @Test
    void testFinIdTechnical()
    {
        List<ReportDao> reportDaos = reportJpaRepository.findByIdTechnicalAndWeek("prueba1", 20);
        assertFalse(reportDaos.isEmpty());
        assertEquals(3 , reportDaos.size());
    }
}
