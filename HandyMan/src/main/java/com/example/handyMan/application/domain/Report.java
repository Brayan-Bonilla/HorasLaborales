package com.example.handyMan.application.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Report
{
    Integer id;
    String idTechnical;
    String idService;
    Date startDate;
    Date endDate;
    Integer week;
}
