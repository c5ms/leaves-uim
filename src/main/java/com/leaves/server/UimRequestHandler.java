package com.leaves.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UimRequestHandler implements Runnable {
    private final Socket clientSocket;
    private final Charset charset;

    public UimRequestHandler(Socket clientSocket, Charset charset) {
        this.clientSocket = clientSocket;
        this.charset = charset;
    }

    @Override
    public void run() {
        try (
                InputStream is = clientSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
        ) {

            // 读取 http 头
            StringBuilder headerBuilder = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty()) {
                headerBuilder.append(line).append('\n');
                line = reader.readLine();
            }

            String resp = "HTTP/1.1 200 OK\n" +
                    "Date: Mon, 25 May 2020 06:30:21 GMT\n" +
                    "Content-Type: text/json; charset=UTF-8\n";


            try (OutputStream outputStream = clientSocket.getOutputStream()) {
                outputStream.write(resp.getBytes(StandardCharsets.UTF_8));
                outputStream.write('\n');
                String json="{\"hostName\":\"a very sample http server\"}";
                outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            }

            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("error while handling a http request", e);
        }
    }
}