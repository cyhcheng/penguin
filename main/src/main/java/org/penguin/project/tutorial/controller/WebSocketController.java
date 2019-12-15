package org.penguin.project.tutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.constant.GlobalConstant;
import org.penguin.project.tutorial.model.ClientMessage;
import org.penguin.project.tutorial.model.ServerMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Slf4j
@Controller
public class WebSocketController {

    @MessageMapping(GlobalConstant.HELLO_MAPPING)
    @SendTo(GlobalConstant.TOPIC)
    public ServerMessage greeting(ClientMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new ServerMessage(LocalDateTime.now(),HtmlUtils.htmlEscape(message.getName()) + "，您好！我是后台机器人，有什么能帮到您的呢？");
    }
}
