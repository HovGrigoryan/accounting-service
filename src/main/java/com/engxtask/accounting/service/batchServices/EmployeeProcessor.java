package com.engxtask.accounting.service.batchServices;

import com.engxtask.accounting.dto.SalaryReportDto;
import com.engxtask.accounting.model.SalaryReport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeProcessor implements ItemProcessor<SalaryReportDto, SalaryReport> {

    private static final String DATE_ORDINAL_SUFFIX = "(?<=\\d)(st|nd|rd|th)";
    private static final String DATE_ORDINAL_SUFFIX_MATCHER = "(.*th.*|.*st.*|.*rd.*|.*nd.*)";

    private final String dmyFormat;
    private final String mdyFormat;

    public EmployeeProcessor(@Value("${dmy}") String dmy,
                             @Value("${mdy}") String mdy) {
        this.dmyFormat = dmy;
        this.mdyFormat = mdy;
    }

    @Override
    public SalaryReport process(@NonNull SalaryReportDto salaryReportDto) {
        SalaryReport salaryReport = new SalaryReport();
        salaryReport.setEmployeeName(salaryReportDto.getEmployeeName());
        salaryReport.setSalary(salaryReportDto.getSalary());
        salaryReport.setMonth(salaryReportDto.getMonth());
        return salaryReport;
    }

    private LocalDate parseDate(String date) {
        if (date.matches(DATE_ORDINAL_SUFFIX_MATCHER)) {
            String correctDate = date.replaceAll(DATE_ORDINAL_SUFFIX, "");
            return LocalDate.parse(correctDate, DateTimeFormatter.ofPattern(mdyFormat));
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(dmyFormat));
    }
}
