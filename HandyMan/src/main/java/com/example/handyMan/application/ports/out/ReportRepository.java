package com.example.handyMan.application.ports.out;

import com.example.handyMan.application.domain.Report;

public interface ReportRepository
{
    Report saveReport (Report report);
}
