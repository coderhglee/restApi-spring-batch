package com.coderhglee.batch.job;

import com.coderhglee.batch.domain.VideoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JsonReader implements ItemReader<VideoDto> {

    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);
    private List<VideoDto> list;
    private int index;
    private RestTemplate restTemplate;

    public JsonReader(RestTemplate restTemplate) {
        this.index = 0;
        this.list = setList();
        this.restTemplate = restTemplate;

    }

    private List<VideoDto> setList() {

        ObjectMapper objectMapper = new ObjectMapper();

        List<VideoDto> videoList = null;
        try {

            String requestUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=%s&maxResults=%s&key=%s";
            ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

        } catch (Exception e) {

        }

        return videoList;
    }

    @Override
    public VideoDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
