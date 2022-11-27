package com.example.handyMan.infrastructure.configurations.JpaRepository;

import com.example.handyMan.infrastructure.models.DAO.ReportDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportJpaRepository extends JpaRepository<ReportDao, Integer>
{
    List<ReportDao> findByIdTechnicalAndWeek(String idTechnical, Integer week);
}
