import utility.ClientConsole;
import utility.CommandManager;
import utility.CommandSender;
import utility.ProductBuilder;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args){
        try{
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 12345;
            DatagramSocket socket = new DatagramSocket();

            CommandSender sender = new CommandSender(socket, serverAddress, serverPort);
            ProductBuilder builder = new ProductBuilder();
            CommandManager commandManager = new CommandManager(sender, builder);

            ClientConsole console = new ClientConsole(commandManager);

            System.out.println("Клиент запущен. Введите команду: ");

            console.run();

        }catch (Exception e){
            System.out.println("Ошибка запуска клиента: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
