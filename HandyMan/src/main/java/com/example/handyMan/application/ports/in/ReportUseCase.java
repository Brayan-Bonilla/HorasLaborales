package com.example.handyMan.application.ports.in;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;


public interface ReportUseCase
{
    Report saveReport(ReportInDTO reportInDTO);
}
