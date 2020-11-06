package com.leaves.queue.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("guard")
public class GuardProperties {
    private String dataDir;
}
