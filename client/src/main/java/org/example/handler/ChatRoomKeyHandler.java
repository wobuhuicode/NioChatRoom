package org.example.handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ChatRoomKeyHandler {
    static void readHandler(SelectionKey key) {
        try {
            SocketChannel sc = (SocketChannel) key.channel();


            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
