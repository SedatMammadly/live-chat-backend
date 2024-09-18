package com.sadatscode.chatproject.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(String senderId,String recipientId,Boolean createNewRoomIfNotExists) {
    return chatRoomRepository.findBySenderIdAndRecipientId(recipientId,senderId)
             .map(ChatRoom::getChatId)
             .or(()->{
                 if(createNewRoomIfNotExists) {
                     var chatId = createChatId(recipientId,senderId);
                     return Optional.of(chatId);
                 }
                 return Optional.empty();
             });
    }

    private String createChatId(String recipientId, String senderId) {
        var chatId = String.format("%s_%s", recipientId,senderId);
        ChatRoom SenderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom RecipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();
        chatRoomRepository.save(SenderRecipient);
        chatRoomRepository.save(RecipientSender);
        return chatId;
    }
}
