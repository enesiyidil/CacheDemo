package com.example.cachedemo.controller;

import com.example.cachedemo.entity.User;
import com.example.cachedemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RedisTemplate<String, String> template;

    @PostMapping("/addrandomusers")
    public ResponseEntity<String> generateRandomUser(Integer count){
        return ResponseEntity.ok(userService.generateRandomUser(count));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/findbyid")
    public ResponseEntity<User> findById(Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
}
