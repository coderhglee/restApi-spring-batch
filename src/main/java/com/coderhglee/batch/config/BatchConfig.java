package com.coderhglee.batch.config;

import com.coderhglee.batch.domain.Video;
import com.coderhglee.batch.domain.VideoDto;
import com.coderhglee.batch.job.JsonProcessor;
import com.coderhglee.batch.job.JsonReader;
import com.coderhglee.batch.job.JsonWriter;
import com.coderhglee.batch.service.BatchService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.GsonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

@Configuration
//@EnableBatchProcessing
public class BatchConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchService batchService;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public Job youtubeJob() throws Exception {
        return jobBuilderFactory.get("youtubeJob")
                .incrementer(new RunIdIncrementer())
//                .listener(listener)
                .flow(saveStep())
                .end()
                .build();
    }

    @Bean
    public Step saveStep() throws Exception {
//        log.debug(channelId);
        return this.stepBuilderFactory.get("saveStep")
                .<VideoDto, Video>chunk(10)
                .reader(jsonReader(restTemplate))
                .processor(processor())
                .writer(writer())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    @StepScope
    public JsonItemReader<VideoDto> jsonItemReader() throws Exception {
        log.debug("-------------Into the reader-------");
        Gson gson = new Gson();
        // configure gson as required
        GsonJsonObjectReader<VideoDto> jsonObjectReader = new GsonJsonObjectReader<>(VideoDto.class);

        String channelId[] = {"UCdp4_yTBhQmB8E339Lafzow", "UCEE10-s88CeOCDzNMLhsblw", "UCWlV3Lz_55UaX4JsMj-z__Q"};
        String jsonArrayItems = batchService.getChannelItem(channelId);
        Resource resource = new ByteArrayResource(jsonArrayItems.getBytes());

        jsonObjectReader.setMapper(gson);

        JsonItemReaderBuilder<VideoDto> jsonItemReaderBuilder = new JsonItemReaderBuilder<>();
        jsonItemReaderBuilder.jsonObjectReader(jsonObjectReader);
        jsonItemReaderBuilder.resource(resource);
        jsonItemReaderBuilder.name("jsonItemReader");

        return jsonItemReaderBuilder.build();
//        return new JsonItemReaderBuilder<BasicDBObject>()
//                .jsonObjectReader(jsonObjectReader)
//                .resource(resource)
//                .name("jsonItemReader")
//                .build();
    }

    @Bean
    @StepScope
    public JsonReader jsonReader(RestTemplate restTemplate){
        return new JsonReader(restTemplate);
    }

    @Bean
    public JsonProcessor processor() {
        log.debug("-------------Into the processor-------");
        return new JsonProcessor();
    }

    @Bean
    public JsonWriter writer() {
        log.debug("-------------Into the writer-------");
        return new JsonWriter();
    }


}
