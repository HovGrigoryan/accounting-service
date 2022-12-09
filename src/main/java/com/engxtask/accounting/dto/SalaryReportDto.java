package com.engxtask.accounting.dto;

import com.engxtask.accounting.enums.Month;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryReportDto {


    private String employeeName;
    private Integer salary;
    private Month month;
}
