package com.engxtask.accounting.service.serviceImpl;


import com.engxtask.accounting.model.SalaryReport;
import com.engxtask.accounting.repository.SalaryReportRepository;
import com.engxtask.accounting.service.SalaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.engxtask.accounting.Constants.CONVERT_SUM;

@Service
public class SalaryReportServiceImpl implements SalaryService {

    private final SalaryReportRepository salaryReportRepository;

    public SalaryReportServiceImpl(SalaryReportRepository salaryReportRepository) {
        this.salaryReportRepository = salaryReportRepository;
    }

    public List<SalaryReport> getSalaryReportByMonth(String month, Integer page) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, 10, Sort.by("salary"));
        List<SalaryReport> salaryReportByMonth = salaryReportRepository.getSalaryReportByMonth(month, firstPageWithTwoElements);
        salaryReportByMonth.forEach(s -> s.setSalary(s.getSalary() * CONVERT_SUM));
//        salaryReportByMonth.stream().map(s -> s.setSalary(s.getSalary() * CONVERT_SUM)).collect(Collectors.toList())
        return salaryReportByMonth;
    }

    @Override
    public Integer getTotalSalary() {
       return salaryReportRepository.findSumAllMonth();

    }
}
