package com.telbot.service.telegram;

import com.telbot.model.TelegramRequest;
import com.telbot.model.TelegramResponse;
import com.telbot.service.TelegramService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${botname}")
    private String BOT_NAME;
    @Value("${bottoken}")
    private String BOT_TOKEN;

    private TelegramService service;

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onRegister() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            TelegramRequest request = fillUnit(update.getMessage());
            service.getResponse(request);//TODO fix why service == null
        }
    }

    public void sendMessage(TelegramResponse response) throws TelegramApiException {
        SendMessage outMess = new SendMessage();
//        setKeys(outMess); //TODO Configure how understand wich user uses some Api
        outMess.setChatId(response.getChatId());
        outMess.setText(response.getMessage());
        execute(outMess);
    }


    private TelegramRequest fillUnit(Message inMess) {
        return TelegramRequest.builder()
                .firstName(inMess.getFrom().getFirstName())
                .lastName(inMess.getFrom().getLastName())
                .message(inMess.getText())
                .chatId(inMess.getChatId().toString())
                .userName(inMess.getFrom().getUserName())
                .build();
    }

}
