package utility;

import requests.Request;
import responses.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CommandSender {
    private final DatagramSocket socket;
    private final InetAddress serverAdd;
    private final int serverPort;

    public CommandSender(DatagramSocket socket, InetAddress serverAdd, int serverPort) {
        this.socket = socket;
        this.serverAdd = serverAdd;
        this.serverPort = serverPort;
    }

    public void send(String commandName, Request request) throws IOException {
        CommandWrapper wrapper = new CommandWrapper(commandName, request);
        //Сериализация
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        out.writeObject(wrapper);
        out.flush();
        byte[] sendData = byteStream.toByteArray();

        //Отправка на сервер
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAdd, serverPort);
        socket.send(packet);
    }

    public Response receive() throws IOException, ClassNotFoundException {
        byte[] recieveData = new byte[4096];
        DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
        socket.receive(recievePacket);

        //Десериализация ответа
        ByteArrayInputStream byteInput = new ByteArrayInputStream(recievePacket.getData(), 0, recievePacket.getLength());
        ObjectInputStream in = new ObjectInputStream(byteInput);
        Response response = (Response) in.readObject();

        return response;
    }

    public void close() {
        socket.close();
    }
}
