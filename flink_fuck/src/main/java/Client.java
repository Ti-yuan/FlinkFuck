/**
 * @AUTHOR: Maynard
 * @DATE: 2023/07/19 17:23
 **/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            // 创建客户端的Socket对象，并指定服务器的IP地址和端口号
            Socket clientSocket = new Socket("localhost", 8080);

            // 创建输入流和输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 发送消息给服务器
            out.println("Hello, Server!");

            // 接收服务器的回复消息
            String response = in.readLine();
            for (int i = 1; i < 10; i++) {
                System.out.println("Loading:"+i);
                Thread.sleep(1000);
            }
            System.out.println("服务器回复：" + response);

            // 关闭连接
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

