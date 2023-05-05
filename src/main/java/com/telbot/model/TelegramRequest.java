package com.telbot.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramRequest {

    @NonNull
    private String chatId;
    private String userName;
    private String message;
    private String firstName;
    private String lastName;
    private Command command = Command.HELP;

}
