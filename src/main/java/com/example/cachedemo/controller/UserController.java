package com.example.cachedemo.controller;

import com.example.cachedemo.entity.User;
import com.example.cachedemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/addrandomusers")
    public ResponseEntity<List<User>> generateRandomUser(Integer count){
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

    @GetMapping("/tekkayit")
    public ResponseEntity<User> save(){
        return ResponseEntity.ok(userService.generateRandomUser());
    }

    @DeleteMapping("/destroy")
    public ResponseEntity<String> deleteUserById(Long id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(Long id, User updatedUser){
        return ResponseEntity.ok(userService.update(id, updatedUser));
    }

}
