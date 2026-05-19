package com.hypers.hm.debug;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AdbMessage {
    public int command;
    public int arg0;
    public int arg1;
    public int dataLength;
    public int checksum;
    public int magic;
    public byte[] data;

    public AdbMessage(int command, int arg0, int arg1, byte[] data) {
        this.command = command;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.data = data;
        this.dataLength = (data != null) ? data.length : 0;
        this.checksum = calculateChecksum(data);
        this.magic = command ^ 0xffffffff;
    }

    private int calculateChecksum(byte[] data) {
        int result = 0;
        if (data != null) {
            for (byte b : data) {
                result += (b & 0xff);
            }
        }
        return result;
    }
    
    public byte[] getHeaderBytes() {
        ByteBuffer buf = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(command);
        buf.putInt(arg0);
        buf.putInt(arg1);
        buf.putInt(dataLength);
        buf.putInt(checksum);
        buf.putInt(magic);
        return buf.array();
    }
}
