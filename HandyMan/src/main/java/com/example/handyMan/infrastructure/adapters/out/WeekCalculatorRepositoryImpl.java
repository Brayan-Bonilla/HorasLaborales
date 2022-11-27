package com.example.handyMan.infrastructure.adapters.out;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.out.WeekCalculatorRepository;
import com.example.handyMan.infrastructure.mappers.entity.ReportEntityMapper;
import com.example.handyMan.infrastructure.configurations.JpaRepository.ReportJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekCalculatorRepositoryImpl implements WeekCalculatorRepository
{
    @Autowired
    private ReportJpaRepository reportJpaRepository;
    @Autowired
    private ReportEntityMapper reportEntityMapper;

    @Override
    public List<Report> findWeek(String idTechnical, int week)
    {
        return reportEntityMapper.Map(reportJpaRepository.findByIdTechnicalAndWeek(idTechnical,week));
    }
}
