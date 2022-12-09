package com.engxtask.accounting.repository;

import com.engxtask.accounting.model.SalaryReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryReportRepository extends JpaRepository<SalaryReport, Long> {


    @Query(value = "Select * from account_service.salary_report sr where sr.month = :month", nativeQuery = true)
    List<SalaryReport> getSalaryReportByMonth(@Param("month") String month, Pageable pageable);

    @Query(value = "SELECT sum(salary) FROM account_service.salary_report;", nativeQuery = true)
    Integer findSumAllMonth();
}
