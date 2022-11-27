package com.example.handyMan.infrastructure.validations;

import com.example.handyMan.application.domain.Report;
import org.apache.commons.lang3.Validate;

public class ReportValidation
{
    public static void validation(Report report)
    {
        validateIdTechnical(report.getIdTechnical());
        validateIdService(report.getIdService());
    }

    private static  void validateIdTechnical(String value)
    {
        Validate.notNull(value, "Technical id can not be null");
        Validate.notBlank(value, "Technical id can not be empty");
    }

    private static void validateIdService(String value)
    {
        Validate.notNull(value, "Service id can not be null");
        Validate.notBlank(value, "Service id can not be empty");
    }
}
