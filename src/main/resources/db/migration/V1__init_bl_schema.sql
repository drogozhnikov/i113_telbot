create schema if not exists telbot;

SET search_path TO telbot;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    chat_id    VARCHAR(100) not null unique,
    user_name  VARCHAR(100) not null unique,
    reg_user   VARCHAR(100),
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);





