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
            //return validationWeek(report);
            return null;
        }
        return new Report();
    }

}
