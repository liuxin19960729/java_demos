package com.liuxin.java.nio.channel.pipe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class PipeCus1 {
    public static void main(String[] args) throws IOException {
        ReadableByteChannel readCh = Channels.newChannel(System.in);
        WritableByteChannel writeCh = Channels.newChannel(System.out);
        Pipe open = Pipe.open();
        Pipe.SinkChannel sink = open.sink();
        Pipe.SourceChannel source = open.source();
//        Charset.availableCharsets()
//        Channels.newWriter()
    }

//    private static ReadableByteChannel startWorker(int reps) throws IOException {
//        Pipe open = Pipe.open();
//
//    }

    private static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;

        public Worker(WritableByteChannel channel, int reps) {
            this.reps = reps;
            this.channel = channel;
        }

        @Override
        public void run() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        }
    }
}


