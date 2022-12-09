package com.engxtask.accounting.controller;


import com.engxtask.accounting.model.SalaryReport;
import com.engxtask.accounting.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final SalaryService salaryService;

    public EmployeeRestController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/month/{month}/{page}")
    public ResponseEntity<List<SalaryReport>> employees(@PathVariable String month, @PathVariable Integer page) {
        if (month != null) month.toUpperCase();
        return ResponseEntity.of(Optional.of(salaryService.getSalaryReportByMonth(month, page)));
    }

    @GetMapping("/totalSalary")
    public Integer totalSalary() {
        return salaryService.getTotalSalary();
    }
}
