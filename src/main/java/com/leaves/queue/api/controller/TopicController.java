package com.leaves.queue.api.controller;

import com.leaves.queue.topic.TopicManager;
import com.leaves.queue.topic.Topic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicManager topicManager;

    @PostMapping("/{topic}")
    public PublishResponse publish(@PathVariable("topic") String topicName, @RequestBody String message) throws IOException, InterruptedException {
        Topic topic = topicManager.findTopic(topicName);
        if (null == topic) {
            return new PublishResponse(50001, -1, "no topic named " + topicName);
        }
        long index = topic.publish(message.getBytes(StandardCharsets.UTF_8));
        if (-1 == index) {
            if (!topic.isRunning()) {
                return new PublishResponse(50002, index, "topic is stopped");
            } else {
                return new PublishResponse(50000, index, "message push failure,but we don't know the reason");
            }
        }
        return new PublishResponse(20000, index, "message push ok");
    }


    @GetMapping("/{topic}")
    public SubscribeResponse subscribe(@PathVariable("topic") String topicName, @RequestParam("subscriber") String subscriber) throws IOException, InterruptedException {
        Topic topic = topicManager.findTopic(topicName);
        byte[] data = topic.take(subscriber, 10, TimeUnit.SECONDS);
        if (null == data) {
            return new SubscribeResponse(20000, null);
        }
        return new SubscribeResponse(20000, new String(data, StandardCharsets.UTF_8));
    }


    @Getter
    @RequiredArgsConstructor
    public static class PublishResponse {
        private final int code;
        private final long index;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SubscribeResponse {
        private final int code;
        private final String message;
    }


}
