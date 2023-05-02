package com.telbot.service;

import com.telbot.model.Command;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }




}
