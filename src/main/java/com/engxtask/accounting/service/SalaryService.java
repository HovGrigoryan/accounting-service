package com.engxtask.accounting.service;

import com.engxtask.accounting.model.SalaryReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryService {

    List<SalaryReport> getSalaryReportByMonth(String month, Integer page);

    Integer getTotalSalary();
}
