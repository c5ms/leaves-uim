package com.leaves.queue.core;

import com.leaves.queue.configure.GuardConstants;
import com.leaves.queue.configure.GuardProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FilesystemCluster implements Cluster {

    private final GuardProperties properties;

    @Override
    public void setValue(String namespace, String value) {
        Path path = getPath(namespace);
        try {
            Files.write(path, value.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("set value error", e);
        }
    }

    @Override
    public void getValue(String namespace) {
        Path path = getPath(namespace);
        try {
            new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("get value error", e);
        }
    }

    public Path getPath(String path) {
        return Paths.get(properties.getDataDir(), GuardConstants.NS_CONFIG, path);
    }

}
