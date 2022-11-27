package com.example.handyMan.infrastructure.adapters.in;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.application.ports.in.ReportUseCase;
import com.example.handyMan.application.ports.in.WeekCalculatorUseCase;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;
import com.example.handyMan.infrastructure.models.DTO.WeekCalculatorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReportController
{
    @Autowired
    private ReportUseCase reportUseCase;
    @Autowired
    private WeekCalculatorUseCase weekCalculatorUseCase;


}
