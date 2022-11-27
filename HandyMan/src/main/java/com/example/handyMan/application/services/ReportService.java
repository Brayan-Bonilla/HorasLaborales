package com.example.handyMan.application.services;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.in.ReportUseCase;
import com.example.handyMan.application.ports.out.ReportRepository;
import com.example.handyMan.infrastructure.mappers.domain.ReportDomainMapper;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;
import com.example.handyMan.infrastructure.validations.ReportValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class ReportService implements ReportUseCase
{
    @Autowired
    private ReportDomainMapper reportDomainMapper;
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report saveReport(ReportInDTO reportInDTO)
    {
        if (reportInDTO.getStartDate().before(reportInDTO.getEndDate()))
        {
            Report report = reportDomainMapper.toDomain(reportInDTO);
            ReportValidation.validation(report);
            return null;
        }
        return new Report();
    }

    /*
        Se encarga de devolver el numero de la semana del año
     */
    private int weekYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /*
        Se encarga de validar el rango de fechas que se va a almacenar
     */
    private Report validationWeek(Report report)
    {
        int week1 = weekYear(report.getStartDate());
        int week2 = weekYear(report.getEndDate());
        Date dateStart = report.getStartDate();
        Date dateEnd = report.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        if (week1 != week2)
        {
            calendar.setTime(dateStart);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            report.setStartDate(dateStart);
            if (dayOfWeek == 1)
            {
                report.setEndDate(conditionalsDate(calendar, 0, 23, 59, 59));
            } else
            {
                report.setEndDate(conditionalsDate(calendar, (8 - dayOfWeek), 23, 59, 59));
            }
            report.setWeek(week1);
            reportRepository.saveReport(report);
            week1++;
            while (week1 != week2)
            {
                report.setStartDate(conditionalsDate(calendar, 1, 0, 0, 0));
                report.setEndDate(conditionalsDate(calendar, 6, 23, 59, 59));
                report.setWeek(week1);
                reportRepository.saveReport(report);
                week1++;
            }
            report.setStartDate(conditionalsDate(calendar, 1, 0, 0, 0));
            report.setEndDate(dateEnd);
        }
        report.setWeek(week1);
        report = reportRepository.saveReport(report);
        return report;
    }

    /*
       Se encarga de establecer un horario especifico
    */
    private Date conditionalsDate(Calendar calendar, int day, int hour, int minute, int second)
    {
        calendar.add(Calendar.DAY_OF_YEAR, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }
}
