/**
 * @AUTHOR: Maynard
 * @DATE: 2023/07/19 17:24
 **/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            // 创建服务器端的Socket对象
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("服务器已启动，等待客户端连接...");

            // 等待客户端的连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端已连接：" + clientSocket.getInetAddress());

            // 创建输入流和输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 循环接收客户端发送的消息，并回复
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("收到客户端的消息：" + message);
                out.println("服务器收到消息：" + message);
            }

            // 关闭连接
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
