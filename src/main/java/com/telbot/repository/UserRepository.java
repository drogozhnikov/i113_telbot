package com.telbot.repository;

import com.telbot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserEntityByUserName(String userName);
    Optional<UserEntity> findUserEntityByRegUser(String regUser);

    void deleteById(int id);
}
