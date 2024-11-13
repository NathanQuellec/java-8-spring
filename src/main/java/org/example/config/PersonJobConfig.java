package org.example.config;

import org.example.Person;
import org.example.PersonItemProcessor;
import org.example.PersonItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class PersonJobConfig {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public PersonJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public FlatFileItemReader<Person> personReader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input.csv"));

        // Configure the line mapper
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

        // Set up the tokenizer for parsing lines
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"firstName", "lastName"});

        // Set up the field set mapper to map fields to the Person class
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);

        return reader;

    }

    @Bean
    public PersonItemProcessor personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public PersonItemWriter personItemWriter() {
        return new PersonItemWriter();
    }

    @Bean
    public Step personStep(FlatFileItemReader<Person> personReader, PersonItemProcessor personItemProcessor, PersonItemWriter personItemWriter) {
        return stepBuilderFactory.get("personStep")
                .<Person, Person>chunk(1)
                .reader(personReader)
                .processor(personItemProcessor)
                .writer(personItemWriter)
                .build();
    }

    @Bean
    public Job personJob(Step personStep) {
        return jobBuilderFactory.get("personJob")
                .start(personStep)
                .build();
    }
}
