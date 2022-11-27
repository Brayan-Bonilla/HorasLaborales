package com.example.handyMan.infrastructure.models.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ReportInDTO
{
    String idTechnical;
    String idService;
    Date startDate;
    Date endDate;
}
