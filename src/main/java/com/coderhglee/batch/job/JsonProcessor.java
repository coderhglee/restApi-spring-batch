package com.coderhglee.batch.job;

import com.coderhglee.batch.domain.Video;
import com.coderhglee.batch.domain.VideoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class JsonProcessor implements ItemProcessor<VideoDto, Video> {

    private static final Logger log = LoggerFactory.getLogger(JsonProcessor.class);

    @Override
    public Video process(VideoDto item) throws Exception {
//        item.append("collectionTime",System.currentTimeMillis());
        log.info(item.toString());
        Video video = new Video();
        return video;
    }
}
