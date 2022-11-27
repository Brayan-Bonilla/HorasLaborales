package com.example.handyMan.infrastructure.adapters.out;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.out.ReportRepository;
import com.example.handyMan.infrastructure.mappers.entity.ReportEntityMapper;
import com.example.handyMan.infrastructure.configurations.JpaRepository.ReportJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportRepositoryImpl implements ReportRepository
{
    @Autowired
    private ReportJpaRepository reportJpaRepository;
    @Autowired
    private ReportEntityMapper reportEntityMapper;

    @Override
    public Report saveReport(Report report)
    {
        return reportEntityMapper.toDomain(reportJpaRepository.save(reportEntityMapper.toEntity(report)));
    }
}
