import commands.Command;
import requests.Request;
import responses.ErrorResponse;
import responses.Response;
import utility.CollectionManager;
import utility.CommandManager;
import utility.CommandWrapper;
import utility.FileManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    private static final int port = 12345;

    public static void main(String[] args){
        try{
            FileManager fileManager = new FileManager();
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(collectionManager);

            // Хук на завершение работы
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Сервер завершает работу. Сохраняем коллекцию...");
                collectionManager.save();
            }));

            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Сервер запущен...");


            while(true){
                try {
              //Прием пакета
                byte[] recieveData = new byte[4096];
                DatagramPacket receivePacket = new DatagramPacket(recieveData, recieveData.length);
                socket.receive(receivePacket);

                //Десериализация команды
                ByteArrayInputStream byteInput = new ByteArrayInputStream(receivePacket.getData(), 0 , receivePacket.getLength());
                ObjectInputStream in = new ObjectInputStream(byteInput);
                CommandWrapper wrapper = (CommandWrapper) in.readObject();

                //Выполнение команды
                String name = wrapper.getCommandName();
                Request request = wrapper.getRequest();
                Command command = commandManager.getCommand(name);
                Response response = command != null
                        ? command.execute(request) : new ErrorResponse("Команда не найдена");

                //Отправка ответа
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                out.writeObject(response);
                out.flush();

                byte[] sendData = outputStream.toByteArray();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(packet);

                System.out.println("Ответ отправлен");

                }catch (Exception inner){
                    System.out.print("Ошибка обработки запроса: ");
                    inner.printStackTrace();
                }
            }

        }catch (Exception e){
            System.out.println("Ошибка запуска сервера: ");
            e.printStackTrace();
        }
    }

}
