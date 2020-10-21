package com.leaves;

import com.leaves.server.UimHttpServer;

public class Application {
    public static void main(String[] args) {
        UimHttpServer uimHttpServer = new UimHttpServer();
        uimHttpServer.listen(9000, "gb2312");
    }
}
