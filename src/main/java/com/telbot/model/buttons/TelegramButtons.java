package com.telbot.model.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramButtons {

    public void setKeys(SendMessage input);
}
