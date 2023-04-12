package com.telbot.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    @NonNull
    private String userName;
    private String message;

}
