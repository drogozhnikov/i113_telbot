package com.telbot.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    private final String regexAdd = "(/+.{3,}\\W{1}.{3,100}\\W{1}.{3,100}\\W{1}\\d{4}[-]\\d{2}[-]\\d{2})";
    private final String regexUpdate = "(/+.{3,}\\W{1}\\d{1,3}\\W{1}.{3,100}\\W{1}.{3,100}\\W{1}\\d{4}[-]\\d{2}[-]\\d{2})";
    private final String regexDelete = "(/+.{3,}\\W{1}\\d{1,3})";

    private final String COMMAND_ADD = "telegram.request.command.add";
    private final String COMMAND_GET = "telegram.request.command.get";
    private final String COMMAND_UPDATE = "telegram.request.command.update";
    private final String COMMAND_DELETE = "telegram.request.command.delete";
    private final String COMMAND_LIST = "telegram.request.command.list";
    private final String COMMAND_HELP = "telegram.request.command.help";

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }


}
