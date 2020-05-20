package com.coderhglee.batch.job;

import com.coderhglee.batch.domain.VideoDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(
        locations = "classpath:application-test.yml")
class JsonReaderTests {

//    @Value("${spring.batch.job.enabled}")
//    private String enabled;
//
    @Value("${youtube.client-key}")
    private String clientKey;

    @Test
    void getRestApiList(){
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        List<VideoDto> videoDtoList = null;
        try {
            String requestUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=%s&maxResults=%s&key=%s";
            requestUrl = String.format(requestUrl,"UCdp4_yTBhQmB8E339Lafzow","10",clientKey);
            ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

            JsonNode news = root.get("items");
            ObjectReader objectReader = objectMapper.readerFor(new TypeReference<List<VideoDto>>() {
            });

            videoDtoList = objectReader.readValue(news);

        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
