package org.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchExample {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("batch-job.xml");

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean("personJob", Job.class);

        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Job Status : " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
