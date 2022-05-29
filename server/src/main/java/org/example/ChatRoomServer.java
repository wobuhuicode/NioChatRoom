package org.example;

import org.example.util.ChatSocketAttachment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;


public class ChatRoomServer {

    private void acceptHandler (ServerSocketChannel ssc, Selector selector) throws IOException {
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);

        SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);
        scKey.attach(new ChatSocketAttachment(1024));
    }

    private void readHandler (SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();

        ChatSocketAttachment attachment = (ChatSocketAttachment) key.attachment();

        ByteBuffer readBuffer = attachment.getReadBuffer();
        StringBuilder builder = attachment.getBuilder();

        readBuffer.clear();

        int numRead = sc.read(readBuffer);
        if (numRead <= 0) {
            sc.close();
            return;
        }

        readBuffer.flip();

        builder.append(StandardCharsets.UTF_8.decode(readBuffer));
        int idxOfDelimiter = builder.indexOf("\n");
        String msg = "";

        if (idxOfDelimiter > 0) {
            msg = builder.substring(0, idxOfDelimiter);
            builder.delete(0, idxOfDelimiter + 1);
        }

        System.out.println(msg);

    }

    private void serve() throws IOException {
        // 初始化 ssc 和 selector
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();

        ssc.bind(new InetSocketAddress(9191));
        ssc.configureBlocking(false);

        // 注册监听事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        for(;;) {
            int numSelectedKey = selector.select();
            if (numSelectedKey <= 0) break;
            Set<SelectionKey> keySet = selector.selectedKeys();

            Iterator<SelectionKey> iterator = keySet.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    acceptHandler(ssc, selector);
                } else if (key.isReadable()) {
                    readHandler(key);
                }
            }
        }

        selector.close();
        ssc.close();

    }




    public static void main(String[] args) throws IOException {
        ChatRoomServer server = new ChatRoomServer();

        try {
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello world!");
    }
}