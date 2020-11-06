package com.leaves.queue.api.controller;

import com.leaves.queue.topic.TopicManager;
import com.leaves.queue.topic.Topic;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {

    private final TopicManager topicManager;

    @PostMapping("/{topic}")
    public QueueApiResult receive(@PathVariable("topic") String topicName, @RequestBody String message) throws IOException {
        Topic topic = topicManager.findTopic(topicName);
        if (null == topic) {
            return new QueueApiResult(50001, "no topic named " + topicName);
        }
        topic.publish(message.getBytes(StandardCharsets.UTF_8));
        return new QueueApiResult(20000, "message push ok");
    }

    @Value
    public static class QueueApiResult {
        int code;
        String message;
    }


}
