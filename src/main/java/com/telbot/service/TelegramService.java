package com.telbot.service;

import com.telbot.entity.UserEntity;
import com.telbot.model.Command;
import com.telbot.model.TelegramRequest;
import com.telbot.model.TelegramResponse;
import com.telbot.repository.UserRepository;
import com.telbot.utils.DiseRoller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TelegramService {

    private UserRepository repository;

    private final String HELLO = "Welcome! Let's find you in my user list, so please send me the name of the account you registered as command {/find.username}";
    private final String REGISTERED = "Your status is active";
    private final String REGISTERED_UNACTIVE = "You are not active user. I can't shedule you";
    private final String NOTREGISTERED = "You are not registered. please register in panda";
    private final String ERROR = "Sorry something went wrong...";
    private final String NOTFOUND = "Sorry didn't find any registered user with name: ";
    private final String HELP = "try to use commands {/status}";

    public List<TelegramResponse> getResponse(TelegramRequest request) {
        getCommandAndMessageFromInputText(request);
            String responseMessage = doCommand(request);
            List<TelegramResponse> response = new ArrayList<>();
            response.add(fillResponse(request, responseMessage));
            return response;
    }

    private String doCommand(TelegramRequest request) {
        switch (request.getCommand()) {
            case START: {
                return HELLO;
            }
            case FIND: {
                return findOrRegisterTelegramUser(request);
            }
            case STATUS: {
                return checkStatus(request);
            }
            case HELP: {
                return this.HELP;
            }
            default: {
                return ERROR;
            }
        }
    }

    public String findOrRegisterTelegramUser(TelegramRequest request) {
        Optional<UserEntity> entity = repository.findUserEntityByRegUser(request.getMessage());
        if (entity.isPresent()) {
            if (entity.get().getChatId() == null) {
                UserEntity fillEntity = UserEntity.builder()
                        .id(entity.get().getId())
                        .regUser(entity.get().getRegUser())
                        .userName(request.getUserName())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .chatId(request.getChatId())
                        .isActive(true)
                        .build();
                repository.save(fillEntity);
            }
            return REGISTERED;
        } else {
            return NOTFOUND + request.getMessage();
        }
    }

    private String checkStatus(TelegramRequest request) {
        Optional<UserEntity> entity = repository.findUserEntityByUserName(request.getUserName());
        if (entity.isPresent()) {
            if (!entity.get().isActive()) {
                return REGISTERED_UNACTIVE;
            }
            return REGISTERED;
        } else {
            return NOTREGISTERED;
        }
    }

    public void getCommandAndMessageFromInputText(TelegramRequest inputRequest) {
        String tempStrCommand = inputRequest.getMessage();
        inputRequest.setCommand(Command.HELP);
        if (inputRequest.getMessage().contains(".")) {
            String[] tempMass = tempStrCommand.split("\\.");
            tempStrCommand = tempMass[0];
            inputRequest.setMessage(tempMass[1]);
        }
        try {
            inputRequest.setCommand(Command.valueOf(tempStrCommand.replace("/", "").toUpperCase()));
        } catch (IllegalArgumentException ignored) {
        } //TODO specify does it normal or not
    }

    private TelegramResponse fillResponse(TelegramRequest request, String message) {
        return TelegramResponse.builder()
                .chatId(request.getChatId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .message(message)
                .build();
    }

}
