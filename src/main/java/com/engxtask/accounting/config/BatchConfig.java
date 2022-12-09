package com.engxtask.accounting.config;

import com.engxtask.accounting.dto.SalaryReportDto;
import com.engxtask.accounting.model.SalaryReport;
import com.engxtask.accounting.service.batchServices.ArchiveResourceItemReader;
import com.engxtask.accounting.service.batchServices.EmployeeProcessor;
import com.engxtask.accounting.service.batchServices.EmployeeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Comparator;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    @Value("${zip-file-path}")
    private Resource resource;

    private final EmployeeWriter writer;
    private final EmployeeProcessor processor;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesAndStoreToDB")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("final step")
                .<SalaryReportDto, SalaryReport>chunk(100)
                .reader(multiResourceItemReader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public MultiResourceItemReader<SalaryReportDto> multiResourceItemReader() {
        ArchiveResourceItemReader<SalaryReportDto> resourceItemReader = new ArchiveResourceItemReader<>();
        resourceItemReader.setResource(resource);
        resourceItemReader.setDelegate(reader());
        resourceItemReader.setComparator(Comparator.comparing(Resource::getDescription));
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<SalaryReportDto> reader() {
        FlatFileItemReader<SalaryReportDto> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<>() {{
//            setFieldSetMapper(new MyFieldSetMapper());
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("employeeName", "salary", "month");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(SalaryReportDto.class);
            }});
        }});
        return reader;
    }
}
