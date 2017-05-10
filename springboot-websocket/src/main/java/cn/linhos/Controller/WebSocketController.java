package cn.linhos.Controller;

import cn.linhos.config.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fprok on 2017/5/10.
 */
@Controller
public class WebSocketController {
    @Autowired
    private MyWebSocket myWebSocket;

    @RequestMapping("/test")
    public void sendMessage() throws IOException {
        new Thread(() -> {
            for(int i=0;i<10;i++) {
                Date date =new Date();
                try {
                    myWebSocket.onMessage(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime()));
                /*myWebSocket.sendMessage(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));*/
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("出现错误，发送失败！");
                }
            }
        }).start();

        //因为是多播，因此不能够直接发送，应该对每个进行发送，因此不能直接使用sendMessage
        myWebSocket.onMessage("finished!");
    }
}
