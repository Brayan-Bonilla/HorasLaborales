package com.example.handyMan.infrastructure.mappers.domain;

import com.example.handyMan.application.domain.Report;
import com.example.handyMan.infrastructure.models.DTO.ReportInDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportDomainMapper
{
    Report toDomain(ReportInDTO reportInDTO);
}
