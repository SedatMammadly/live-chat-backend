package com.sadatscode.chatproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    @MessageMapping("/addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload User user) {
        service.save(user);
        return user;
    }
    @MessageMapping("/disconnect")
    @SendTo("/user/topic")
    public User disconnect(@Payload User user) {
       service.disconnect(user);
        return user;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectUsers() {
        return ResponseEntity.ok(service.FindConnectUsers());
    }
}
