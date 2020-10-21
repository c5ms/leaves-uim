package com.leaves.server;


import com.leaves.util.concurrent.UimThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

// 200 线程   30秒达到7K/s
// 50  线程   18秒达到7K/s

@Slf4j
public class UimHttpServer {
    private volatile boolean running = false;

    private final UimThreadPool threadPool = new UimThreadPool(20, null, null);

    public void listen(int port, String charsetName) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!running) {
                Socket clientSocket = serverSocket.accept();
                Charset charset = Charset.forName(charsetName);
                UimRequestHandler processor = new UimRequestHandler(clientSocket, charset);
                threadPool.submit(processor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
