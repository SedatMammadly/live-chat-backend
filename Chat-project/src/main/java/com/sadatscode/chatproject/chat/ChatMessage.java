package com.sadatscode.chatproject.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@Data
public class ChatMessage {
    @Id
    private String id;
    private String chatId;
    private String recipientId;
    private String senderId;
    private String content;
    private Date timestamp;
}
