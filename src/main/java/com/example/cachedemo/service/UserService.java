package com.example.cachedemo.service;

import com.example.cachedemo.entity.User;
import com.example.cachedemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String generateRandomUser(Integer count){
        for (int i = 0; i < count; i++){
            userRepository.save(User.builder()
                            .name("Test" + i)
                            .email("test" + i + "@example.com")
                    .build());
        }
        return "OK";
    }

    @Cacheable(value = "findall")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "findbyid", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
