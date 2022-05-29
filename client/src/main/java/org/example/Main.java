package org.example;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try {
            SocketChannel sc = SocketChannel.open();
            sc.connect(new InetSocketAddress("localhost", 9191));

            sc.write(ByteBuffer.wrap("caonibabidbsaisabdi\n".getBytes(StandardCharsets.UTF_8)));


            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}