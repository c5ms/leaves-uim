package com.leaves;

import com.leaves.server.UimHttpServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        UimHttpServer uimHttpServer = new UimHttpServer();
        uimHttpServer.listen(9000, "gb2312");
    }
}
