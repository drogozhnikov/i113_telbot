package com.telbot.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramResponse {

    @NonNull
    private String chatId;
    private String userName;
    private String message;
    private String firstName;
    private String lastName;

}
