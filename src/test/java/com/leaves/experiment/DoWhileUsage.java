package com.leaves.experiment;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DoWhileUsage {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("./pom.xml"));
        ByteBuffer byteBuffer = ByteBuffer.allocate(inputStream.available());
        int ch = inputStream.read();
        while (-1 != ch) {
            byteBuffer.put((byte) ch);
            ch = inputStream.read();
        }
        System.out.println(new String(byteBuffer.array()));
    }

}
