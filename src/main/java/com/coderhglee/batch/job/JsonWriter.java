package com.coderhglee.batch.job;

import com.coderhglee.batch.domain.Video;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class JsonWriter implements ItemWriter<Video> {

    @Override
    public void write(List<? extends Video> list) throws Exception {

    }
}
