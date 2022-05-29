package org.example.util;

import java.nio.ByteBuffer;

// 所有 socket 连接的附属类，读buffer和builder, 2k
public class ChatSocketAttachment {
    ByteBuffer readBuffer;
    StringBuilder builder;

    public ChatSocketAttachment(int capacity) {
        readBuffer = ByteBuffer.allocateDirect(capacity);
        builder = new StringBuilder(capacity);
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public StringBuilder getBuilder() {
        return builder;
    }
}
