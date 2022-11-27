package com.example.handyMan.application.ports.out;

import com.example.handyMan.application.domain.Report;

import java.util.List;

public interface WeekCalculatorRepository
{
    List<Report> findWeek (String idTechnical, int week);
}
