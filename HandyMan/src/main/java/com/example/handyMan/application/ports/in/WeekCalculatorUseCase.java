package com.example.handyMan.application.ports.in;

import com.example.handyMan.infrastructure.models.DTO.WeekCalculatorDTO;

public interface WeekCalculatorUseCase
{
    WeekCalculatorDTO weekCalculator(String idTechnical, int week);
}
