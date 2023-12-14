package com.example.cachedemo.service;

import com.example.cachedemo.entity.User;
import com.example.cachedemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RedisTemplate<String, User> template;

    @Caching(
            evict = {@CacheEvict(value = "findall", allEntries = true, beforeInvocation = true)},
            cacheable = {@Cacheable(value = "findall")}
    )
    public List<User> generateRandomUser(Integer count){
        for (int i = 0; i < count; i++){
            userRepository.save(User.builder()
                            .name("Test" + i)
                            .email("test" + i + "@example.com")
                    .build());
        }
        return userRepository.findAll();
    }

    public User generateRandomUser(){
        User user = userRepository.save(User.builder()
                .name("Test")
                .email("test@example.com")
                .build());
        template.opsForList().rightPush("userList", user);
        return user;
    }

//    @Cacheable(value = "findall")
    public List<User> findAll() {
        Long userListSize = template.opsForList().size("userList");
        return template.opsForList().range("userList", 0, userListSize - 1);
    }

    @Cacheable(value = "findbyid", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public String deleteUserById(Long id){
        User user = userRepository.findById(id).get();
        template.opsForList().remove("userList", 0, user);
        return "Silindi";
    }

    public User update(Long id, User updatedUser) {
        User user = userRepository.findById(id).get();
        Long userIndex = template.opsForList().indexOf("userList", user);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        template.opsForList().set("userList", userIndex, user);
        return userRepository.save(user);
    }
}
