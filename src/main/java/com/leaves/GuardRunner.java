package com.leaves;

import com.leaves.queue.topic.TopicManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuardRunner implements CommandLineRunner {

    private final TopicManager topicManager;

    @Override
    public void run(String... args) {
        topicManager.startAllTopic();
    }
}
