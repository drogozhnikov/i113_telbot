package com.telbot.controller;

import com.telbot.dto.MessageDto;
import com.telbot.dto.UserDto;
import com.telbot.service.TelegramApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RestController
@RequestMapping("/api/i113/telegrambot/")
@AllArgsConstructor
public class TelegramController {

    private TelegramApiService service;

    @PostMapping("/")
    public void sendMessage(@RequestBody MessageDto message) throws TelegramApiException {
        service.sendApiMessage(message);
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto user) {
        return service.registerUser(user);
    }

}
