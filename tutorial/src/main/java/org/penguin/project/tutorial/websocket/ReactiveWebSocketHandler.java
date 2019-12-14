package org.penguin.project.tutorial.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.penguin.project.tutorial.model.ServerMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

//    private Flux<String> eventFlux = Flux.generate(sink -> {
//        ServerMessage event = new ServerMessage(now(), randomUUID().toString());
//        try {
//            sink.next(objectMapper.writeValueAsString(event));
//        } catch (JsonProcessingException e) {
//            sink.error(e);
//        }
//    });
//
//    private Flux<String> intervalFlux = Flux.interval(Duration.ofMillis(5000L)).zipWith(eventFlux, (time, event) -> event);

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
//        return webSocketSession.send(intervalFlux.map(webSocketSession::textMessage)).and(webSocketSession.receive().map(WebSocketMessage::getPayloadAsText).log());
        return webSocketSession.send(webSocketSession.receive().map(msg -> "Received On Server :: " + new ServerMessage(now(), msg.getPayloadAsText() + "(" + randomUUID() + ")")).map(webSocketSession::textMessage));
    }
}
