package com.coderhglee.batch.service;

import com.coderhglee.batch.util.BatchUtil;
import com.google.gson.*;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    public String getChannelItem(String channelId[]) throws Exception {
        int maxResults = 50;
        String publishedAfter = "2019-03-11T00:00:00Z";
        String publishedBefore = "2019-03-11T23:59:59Z";
        String clientKey = "";


        JsonArray channelArray = new JsonArray();
        for (String ch : channelId) {
            String searchJson = BatchUtil.readUrl("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + ch + "&maxResults=" + maxResults + "&publishedAfter=" + publishedAfter + "&publishedBefore=" + publishedBefore + "&key=" + clientKey);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(searchJson);
            JsonArray items = element.getAsJsonObject().get("items").getAsJsonArray();

            JsonArray newItems = new JsonArray();
            for (JsonElement item : items) {
                JsonObject itemObject = new JsonObject();
                String videoId = item.getAsJsonObject().get("id").getAsJsonObject().get("videoId").toString().replaceAll("\"", "");
                String videoJson = BatchUtil.readUrl("https://www.googleapis.com/youtube/v3/videos?part=statistics&id=" + videoId + "&key=" + clientKey);

                JsonElement vElement = parser.parse(videoJson);
                JsonArray vItems = vElement.getAsJsonObject().get("items").getAsJsonArray();

                itemObject.add("snippet", item);
                itemObject.add("statistics", vItems.get(0).getAsJsonObject().get("statistics"));
                newItems.add(itemObject);
            }
            JsonObject newObject = new JsonObject();
            newObject.addProperty("_id", ch);
            newObject.add("items", newItems);


            channelArray.add(newObject);
        }


        return channelArray.toString();
    }
}

