package com.telbot.service;

import com.telbot.dto.MessageDto;
import com.telbot.dto.UserDto;
import com.telbot.entity.UserEntity;
import com.telbot.exception.TelBotException;
import com.telbot.model.TelegramResponse;
import com.telbot.repository.UserRepository;
import com.telbot.service.telegram.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TelegramApiService {

    private UserRepository repository;
    private TelegramBot bot;

    public void sendApiMessage(MessageDto messageDto) throws TelegramApiException {
        bot.sendMessage(buildResponse(messageDto.getMessage(), messageDto.getRegUser()));
    }

    private TelegramResponse buildResponse(String message, String userName) {
        Optional<UserEntity> entity = repository.findUserEntityByUserName(userName);
        if (entity.isPresent()) {
            return TelegramResponse.builder()
                    .message(message)
                    .chatId(entity.get().getChatId())
                    .userName(entity.get().getUserName())
                    .firstName(entity.get().getFirstName())
                    .lastName(entity.get().getLastName())
                    .build();
        }
        throw new TelBotException("User have no chat id", HttpStatus.NOT_FOUND);
    }

    //used by API. Create entity without chat id.
    public void registerUser(UserDto userDto){
        UserEntity entity = UserEntity.builder()
                .regUser(userDto.getRegUser())
                .build();
        repository.save(entity);
    }
}
