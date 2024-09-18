package com.sadatscode.chatproject.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void messageProcess(ChatMessage chatMessage) {
        chatMessageService.addChatMessage(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getRecipientId()
                ,"/queue/messages",
                new ChatNotification(chatMessage.getId(),
                        chatMessage.getSenderId(),
                        chatMessage.getRecipientId(),
                        chatMessage.getContent()));
    }
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable("senderId") String senderId,@PathVariable("recipientId") String recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
