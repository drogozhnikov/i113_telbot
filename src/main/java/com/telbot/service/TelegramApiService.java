package com.telbot.service;

import com.telbot.dto.MessageDto;
import com.telbot.dto.UserDto;
import com.telbot.entity.UserEntity;
import com.telbot.exception.TelBotException;
import com.telbot.model.TelegramResponse;
import com.telbot.repository.UserRepository;
import com.telbot.service.converter.UserConverter;
import com.telbot.service.telegram.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TelegramApiService {

    private UserConverter converter;
    private UserRepository repository;
    private TelegramBot bot;

    public void sendApiMessage(MessageDto messageDto) throws TelegramApiException {
        bot.sendMessage(buildResponse(messageDto.getMessage(), messageDto.getRegUser()));
    }

    private TelegramResponse buildResponse(String message, String regUser) {
        Optional<UserEntity> entity = repository.findUserEntityByRegUser(regUser);
        if (entity.isPresent()) {
            if(entity.get().isActive()){
                return TelegramResponse.builder()
                        .message(message)
                        .chatId(entity.get().getChatId())
                        .userName(entity.get().getUserName())
                        .firstName(entity.get().getFirstName())
                        .lastName(entity.get().getLastName())
                        .build();
            }
            throw new TelBotException("User is not active", HttpStatus.NOT_FOUND);
        }
        throw new TelBotException("User have no chat id", HttpStatus.NOT_FOUND);
    }

    //used by API. Create entity without chat id.
    public UserDto registerUser(UserDto userDto){
        if(userDto.getRegUser()!= null){
            Optional<UserEntity> user = repository.findUserEntityByRegUser(userDto.getRegUser());
            if(!user.isPresent()){
                UserEntity entity = converter.convertToEntity(userDto);
                return converter.convertToDto(repository.save(entity));
            }
            return converter.convertToDto(user.get());
        }
        return new UserDto();
    }

    public void setNotification(UserDto userDto, boolean value){
        Optional<UserEntity> entity = repository.findUserEntityByRegUser(userDto.getRegUser());
        if(entity.isPresent()){
            entity.get().setActive(value);
            repository.save(entity.get());
        }else{
            throw new TelBotException("User not exist", HttpStatus.NOT_FOUND);
        }
    }

    public String getNotificationStatus(UserDto userDto){
        Optional<UserEntity> entity = repository.findUserEntityByRegUser(userDto.getRegUser());
        String response = "User not Registered yet";
        if(entity.isPresent()){
            response = "User: " + userDto.getRegUser() + " Registered";
            if(entity.get().getChatId()!=null){
                response+= ", Active";
                if(entity.get().isActive()){
                    response+=", notification is Active";
                }else{
                    response+=", notification is Diactived";
                }
            }else{
                response+= ", not confirmed";
            }
            return response;
        }
        return response;
    }

}
