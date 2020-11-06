package com.leaves.queue.topic;

import com.leaves.queue.configure.GuardConstants;
import com.leaves.queue.configure.GuardProperties;
import com.leaves.queue.core.Serializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicManager {
    private final GuardProperties properties;
    private final Serializer serializer;

    private final ConcurrentHashMap<String, Topic> topics = new ConcurrentHashMap<>();

    public Topic findTopic(String name) {
        return topics.get(name);
    }

    public void startAllTopic() {
        try {
            Path topicPath = Paths.get(properties.getDataDir(), GuardConstants.NS_TOPIC);
            if (Files.notExists(topicPath)) {
                Files.createFile(topicPath);
            }
            Files.list(topicPath).forEach(path -> {
                try {
                    if (Files.isDirectory(path)) {
                        startTopic(path.toFile().getName());
                    }
                } catch (Exception e) {
                    log.error("start topic error", e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("start all topic error", e);
        }
    }

    public void startTopic(String name) {
        Path topicPath = Paths.get(properties.getDataDir(), GuardConstants.NS_TOPIC, name);
        if (Files.notExists(topicPath)) {
            throw new RuntimeException("no this topic config");
        }
        try {
            Path configPath = topicPath.resolve("config");
            if (Files.notExists(configPath)) {
                Files.createFile(configPath);
            }
            String config = new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8);
            TopicConfig configData = serializer.deserialize(config.getBytes(), TopicConfig.class);

            Path topicDataPath = Paths.get(properties.getDataDir(), GuardConstants.NS_TOPIC_DATA);
            Topic topic = Topic.builder()
                    .dataDir(topicDataPath.toString())
                    .queueName(name)
                    .build();
            topic.start();
            topics.put(name, topic);
        } catch (IOException e) {
            throw new RuntimeException("start topic error", e);
        }
    }


}
