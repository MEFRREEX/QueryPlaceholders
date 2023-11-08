package com.mefrreex.queryplaceholders.query;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.function.Consumer;

import lombok.NonNull;

public class BedrockQuery {

    private static final byte UNCONNECTED_PING = 0x01;
    private static final byte[] UNCONNECTED_MESSAGE_SEQUENCE = {0x00, (byte) 0xff, (byte) 0xff, 0x00, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, 0x12, 0x34, 0x56, 0x78};

    private static final Random random = new Random();
    private static long dialerId = random.nextLong();

    private final int timeout;

    public BedrockQuery() {
        this(2000);
    }

    public BedrockQuery(int timeout) {
        this.timeout = timeout;
    }

    public BedrockQueryResponse create(@NonNull String address, int port) {
        return createQuery(address, port);
    }

    public void create(@NonNull String address, int port, @NonNull Consumer<BedrockQueryResponse> response) {
        new Thread(() -> {
            response.accept(create(address, port));
        }).start();
    }

    private BedrockQueryResponse createQuery(String serverAddress, int serverPort) {
        try {
            InetAddress address = InetAddress.getByName(serverAddress);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeByte(UNCONNECTED_PING);
            dataOutputStream.writeLong(System.currentTimeMillis() / 1000);
            dataOutputStream.write(UNCONNECTED_MESSAGE_SEQUENCE);
            dataOutputStream.writeLong(dialerId++);

            byte[] requestData = outputStream.toByteArray();
            byte[] responseData = new byte[1024 * 1024 * 4];

            try (DatagramSocket socket = new DatagramSocket()) {
                DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address, serverPort);
                socket.send(requestPacket);

                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
                socket.setSoTimeout(timeout);
                socket.receive(responsePacket);

                String responseString = new String(responsePacket.getData(), 0, responsePacket.getLength());
                String[] splittedData = responseString.split(";", 2)[1].split(";");
                
                int protocol = Integer.parseInt(splittedData[1]);
                int playerCount = Integer.parseInt(splittedData[3]);
                int maxPlayers = Integer.parseInt(splittedData[4]);

                return new BedrockQueryResponse(true, splittedData[0], protocol, splittedData[2], playerCount, maxPlayers, splittedData[6], splittedData[7]);
            }
        } catch (Exception e) {
            return BedrockQueryResponse.empty();
        }
    }
}