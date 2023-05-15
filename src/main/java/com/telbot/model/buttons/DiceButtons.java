package com.telbot.model.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class DiceButtons implements TelegramButtons{
    @Override
    public void setKeys(SendMessage input) {
        input.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new
                ReplyKeyboardMarkup();
        input.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardTopRow = new KeyboardRow();
        keyboardTopRow.add("/D.20");

        KeyboardRow keyboardCenterRow = new KeyboardRow();
        keyboardCenterRow.add("/D.4");
        keyboardCenterRow.add("/D.6");
        keyboardCenterRow.add("/D.8");

        KeyboardRow keyboardBottomRow = new KeyboardRow();
        keyboardBottomRow.add("/D.10");
        keyboardBottomRow.add("/D.12");
        keyboardBottomRow.add("/D.100");

        keyboard.add(keyboardTopRow);
        keyboard.add(keyboardCenterRow);
        keyboard.add(keyboardBottomRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
