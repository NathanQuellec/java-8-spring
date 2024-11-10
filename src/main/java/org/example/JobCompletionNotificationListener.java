package org.example;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job is about to start");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job finished with status: {}", jobExecution.getStatus());
    }
}
