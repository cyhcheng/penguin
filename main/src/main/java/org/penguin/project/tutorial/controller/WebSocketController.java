package org.penguin.project.tutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.constant.GlobalConstant;
import org.penguin.project.tutorial.model.request.WebSocketClientMessage;
import org.penguin.project.tutorial.model.response.WebSocketServerMessage;
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
    public WebSocketServerMessage greeting(WebSocketClientMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new WebSocketServerMessage(LocalDateTime.now(), HtmlUtils.htmlEscape(message.getName()) + "，您好！我是后台机器人，有什么能帮到您的呢？");
    }
}
