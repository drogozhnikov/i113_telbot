package com.telbot.service.converter;

import com.telbot.dto.UserDto;
import com.telbot.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConverter {

    public UserDto convertToDto(UserEntity inputEntity) {
        return UserDto.builder()
                .regUser(inputEntity.getRegUser())
                .build();
    }

    public UserEntity convertToEntity(UserDto inputDTO){
        return UserEntity.builder()
                .regUser(inputDTO.getRegUser())
                .build();
    }

}
