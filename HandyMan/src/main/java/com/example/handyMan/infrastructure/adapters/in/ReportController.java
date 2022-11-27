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

    @PostMapping("/saveReport")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Report> saveReport(@RequestBody ReportInDTO report)
    {
        Report report1 = reportUseCase.saveReport(report);
        return (report1.getId() != null && report1.getId() > 0) ? new ResponseEntity<>(report1, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/calculate/idTechnical/{idTechnical}/week/{week}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<WeekCalculatorDTO> weekCalculator(@PathVariable String idTechnical, @PathVariable int week)
    {
        WeekCalculatorDTO weekCalculatorDTO = weekCalculatorUseCase.weekCalculator(idTechnical, week);
        return new ResponseEntity<>(weekCalculatorDTO, HttpStatus.OK);
    }
}
