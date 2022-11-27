package com.example.handyMan.infrastructure.mappers.entity;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.infrastructure.models.DAO.ReportDao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportEntityMapper
{
    ReportDao toEntity(Report report);

    Report toDomain(ReportDao reportDao);

    List<Report> Map(List<ReportDao> reportEntities);
}
