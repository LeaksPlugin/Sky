package com.talesdev.core.network;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.talesdev.core.system.ReflectionUtils;
import com.talesdev.core.system.ReflectionUtils.RefClass;
import com.talesdev.core.system.ReflectionUtils.RefMethod;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;

/**
 * A class represent an custom payload packet
 *
 * @author MoKunz
 */
public class PluginMessagePacket {
    private Object nmsPacket;
    private String channelName;
    private byte[] data;

    public PluginMessagePacket(PacketContainer packetContainer) {
        if (isCustomPayloadPacket(packetContainer.getType())) {
            channelName = packetContainer.getStrings().read(0);
            nmsPacket = packetContainer.getHandle();
            // nms packet op.
            RefClass nmsPacketClass = ReflectionUtils.getRefClass(nmsPacket.getClass());
            RefMethod getByteBuffer = nmsPacketClass.getMethod("b");
            Object byteBuffer = getByteBuffer.of(nmsPacket).call();
            // byte buffer op.
            RefClass byteBufferClass = ReflectionUtils.getRefClass(byteBuffer.getClass());
            RefMethod read = byteBufferClass.getMethod("a");
            try {
                Object byteArrays = read.of(byteBuffer).call();
                data = (byte[]) byteArrays;
            } catch (Exception e) {
                data = new byte[0];
            }
        }
    }

    private boolean isCustomPayloadPacket(PacketType packetType) {
        return packetType.equals(PacketType.Play.Client.CUSTOM_PAYLOAD) || packetType.equals(PacketType.Play.Server.CUSTOM_PAYLOAD);
    }

    public String getChannel() {
        return channelName;
    }

    public Object getNmsPacket() {
        return nmsPacket;
    }

    public byte[] getByteArrays() {
        return data;
    }

    public String getHexString() {
        return Hex.encodeHexString(getByteArrays());
    }

    public String getString() {
        try {
            return new String(getByteArrays(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error while decoding bytes as utf-8 string!");
            e.printStackTrace();
        }
        return "";
    }
}
