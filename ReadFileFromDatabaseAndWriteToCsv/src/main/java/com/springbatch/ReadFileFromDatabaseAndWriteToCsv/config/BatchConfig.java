package com.springbatch.ReadFileFromDatabaseAndWriteToCsv.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.ReadFileFromDatabaseAndWriteToCsv.entity.Student;
import com.springbatch.ReadFileFromDatabaseAndWriteToCsv.mapper.UserRowMapper;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcCursorItemReader<Student> reader(){
		JdbcCursorItemReader<Student> reader=new JdbcCursorItemReader<Student>();
		reader.setDataSource(dataSource);
		reader.setSql("select id,name,dept,phonenumber from student");
		reader.setRowMapper(new UserRowMapper());
		return reader;
	}
	
	@Bean
	public FlatFileItemWriter<Student> csvWriter() {
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
	    writer.setResource(new FileSystemResource("outputData.csv"));

	    DelimitedLineAggregator<Student> lineAggregator = new DelimitedLineAggregator<>();
	    lineAggregator.setDelimiter(",");

	    BeanWrapperFieldExtractor<Student> fieldExtractor = new BeanWrapperFieldExtractor<>();
	    fieldExtractor.setNames(new String[]{"id", "name", "dept","phonenumber"}); 
	    lineAggregator.setFieldExtractor(fieldExtractor);
	    writer.setLineAggregator(lineAggregator);
	    writer.setHeaderCallback(writer1 -> writer1.write("ID,Name,Department,Phonenumber"));
	    return writer;
	}
	
	@Bean
	public Step exportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
	    return new StepBuilder("exportStep", jobRepository)
	            .<Student, Student>chunk(10, transactionManager)
	            .reader(reader()) 
	            .writer(csvWriter())
	            .build();
	}
	
	@Bean
	public Job exportJob(JobRepository jobRepository, Step exportStep) {
	    return new JobBuilder("exportJob", jobRepository)
	            .start(exportStep)
	            .build();
	}

}
