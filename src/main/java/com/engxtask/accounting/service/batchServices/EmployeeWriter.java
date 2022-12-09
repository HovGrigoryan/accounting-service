package com.engxtask.accounting.service.batchServices;

import com.engxtask.accounting.model.SalaryReport;
import com.engxtask.accounting.repository.SalaryReportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeWriter  implements ItemWriter<SalaryReport> {

    private final SalaryReportRepository repository;
    private short counter;

    public EmployeeWriter(SalaryReportRepository repository) {
        this.repository = repository;
    }

    //There are two ways to store data, first in json file, second in db
    @Override
    public void write(@NonNull List<? extends SalaryReport> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees" + (++counter) + ".json"))) {
            objectMapper.writeValue(writer, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.saveAll(list);
    }
}
