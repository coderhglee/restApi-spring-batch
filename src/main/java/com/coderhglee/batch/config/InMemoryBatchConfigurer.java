package com.coderhglee.batch.config;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

/**
 * 메타 데이터를 기록 안하고 Memory에 저장해서 SpringBatch를 사용하는 Configuration
 * @Author hglee
 */
public class InMemoryBatchConfigurer implements BatchConfigurer {

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    public JobRepository getJobRepository() {
        return jobRepository;
    }

    @Override
    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }

    @PostConstruct
    public void initialize() {
        if (this.transactionManager == null) {
            this.transactionManager = new ResourcelessTransactionManager();
        }
        try {
            MapJobRepositoryFactoryBean jobRepositoryFactoryBean =
                    new MapJobRepositoryFactoryBean(this.transactionManager);
            jobRepositoryFactoryBean.afterPropertiesSet();
            this.jobRepository = jobRepositoryFactoryBean.getObject();

            MapJobExplorerFactoryBean jobExplorerFactoryBean =
                    new MapJobExplorerFactoryBean(jobRepositoryFactoryBean);
            jobExplorerFactoryBean.afterPropertiesSet();
            this.jobExplorer = jobExplorerFactoryBean.getObject();

            SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
            jobLauncher.setJobRepository(jobRepository);
            jobLauncher.afterPropertiesSet();
            this.jobLauncher = jobLauncher;
        } catch (Exception e) {
            throw new BatchConfigurationException(e);
        }
    }
}

