package com.sadatscode.chatproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void save(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }
    public void disconnect(User user) {
        User storedUser = repository.findById(user.getNickName()).orElse(null);
        if(storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
        }
        repository.save(user);
    }

    public List<User>FindConnectUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }
}
