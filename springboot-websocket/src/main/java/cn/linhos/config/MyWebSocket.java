package cn.linhos.config;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by fprok on 2017/5/9.
 * 注册一个websocket 事件
 */
@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {
    private static CopyOnWriteArraySet<MyWebSocket> webSockets = new CopyOnWriteArraySet<MyWebSocket>();
    public Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        try {
            sendMessage("11111111111111");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);  //从set中删除
        //在线数减1
        System.out.println("有一连接关闭！");
    }

    /*
    * 将消息发送到客户端
    * */
    public void sendMessage(String message) throws IOException {
        System.out.println("sssssssssssssssssss"+message);
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /*
    * 当客户端发送消息过来，@OnMessage 接受消息
    * */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        if (message == null || message.length()<=0)
            message="输入为空了";
            for (MyWebSocket item : webSockets) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    continue;
                }
            }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public static void sendInfo(String message) throws IOException {
        if(message==null)
            message="为空了！";
        for (MyWebSocket item : webSockets) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }


}
