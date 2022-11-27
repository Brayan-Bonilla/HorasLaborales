package com.example.handyMan.infrastructure.models.DAO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "service_report")
@Data
public class ReportDao implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String idTechnical;
    String idService;
    Date startDate;
    Date endDate;
    Integer week;
}
