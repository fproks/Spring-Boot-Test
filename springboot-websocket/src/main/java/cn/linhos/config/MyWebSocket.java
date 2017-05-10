package cn.linhos.config;

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
    public void onOpen(Session session){
        this.session =session;
        webSockets.add(this);
        try{
            sendMessage("11111111111111");
        }catch (IOException e){
            System.out.println("IO异常");
        }
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);  //从set中删除
        //在线数减1
        System.out.println("有一连接关闭！" );
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for (MyWebSocket item : webSockets) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSockets) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }



}
