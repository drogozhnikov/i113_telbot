package com.telbot.service;

import com.telbot.dto.MessageDto;
import com.telbot.dto.UserDto;
import com.telbot.entity.UserEntity;
import com.telbot.exception.TelBotException;
import com.telbot.model.TelegramRequest;
import com.telbot.model.TelegramResponse;
import com.telbot.repository.UserRepository;
import com.telbot.service.telegram.TelegramBot;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TelegramService {

    private UserRepository repository;
    private TelegramBot bot;

    public void sendApiMessage(MessageDto messageDto) throws TelegramApiException {
        bot.sendMessage(buildResponse(messageDto.getMessage(), messageDto.getRegUser()));
    }

    public String getResponse(TelegramRequest request) {
        if(request.getMessage().equals("/start")){
            return "Welcome! I can't find you in my user list, so please enter the name of the account you registered:";
        }else{
            Optional<UserEntity> entity = repository.findUserEntityByRegUser(request.getMessage());
            if(entity.isPresent()){
                if(entity.get().getChatId()==null){
                    UserEntity fillEntity = UserEntity.builder()
                            .id(entity.get().getId())
                            .regUser(entity.get().getRegUser())
                            .userName(request.getUserName())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .chatId(request.getChatId())
                            .build();
                    repository.save(fillEntity);
                }
                return "Your status is active";
            }else{
                return "Sorry something went wrong";
            }
        }
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
        throw new TelBotException("User not Exist", HttpStatus.NOT_FOUND);
    }

    public void registerUser(UserDto userDto){
        UserEntity entity = UserEntity.builder()
                .regUser(userDto.getRegUser())
                .build();
        repository.save(entity);
    }

}
