package com.leaves.server;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class UimRequestHandler   implements Runnable {
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

            StringBuilder headerBuilder = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty()) {
                headerBuilder.append(line).append('\n');
                line = reader.readLine();
            }

            String resp = "HTTP/1.1 200 OK\n" +
                    "Date: Mon, 25 May 2020 06:30:21 GMT\n" +
                    "Content-Type: text/json; charset=UTF-8\n";

            OutputStream outputStream = clientSocket.getOutputStream();
            IOUtils.write(resp, outputStream, "UTF-8");
            outputStream.write('\n');
            IOUtils.write("{\"hostName\":\"a very sample http server\"}", outputStream, "UTF-8");
            outputStream.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}