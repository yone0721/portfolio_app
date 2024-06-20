CREATE DATABASE IF NOT EXISTS reservation_db
    CHARACTER SET utf8mb4;

USE reservation_db;


CREATE TABLE IF NOT EXISTS store_info_tb (
    store_id INTEGER NOT NULL AUTO_INCREMENT,
    store_name VARCHAR(100) NOT NULL,
    post_code VARCHAR(20) NOT NULL,
    city VARCHAR(15) NOT NULL,
    municipalities VARCHAR(100) NOT NULL,
    street_address VARCHAR(200) NOT NULL,
    building VARCHAR(200),
    mail VARCHAR(500) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL UNIQUE,
    store_password VARCHAR(50) NOT NULL,
    store_reservation_limit INTEGER NULL,
    is_opened VARCHAR(30) NOT NULL DEFAULT "10:00",
    is_closed VARCHAR(30) NOT NULL DEFAULT "21:00",
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    is_deleted BOOLEAN DEFAULT 0,
    PRIMARY KEY (store_id),
    FULLTEXT (store_name,
                city,
                municipalities,
                street_address,
                building)
    WITH PARSER ngram
);

CREATE TABLE IF NOT EXISTS user_info_tb (
    user_id INTEGER AUTO_INCREMENT NOT NULL,
    mail VARCHAR(500) NOT NULL UNIQUE,
    user_name VARCHAR(20) NOT NULL,
    user_name_furigana VARCHAR(40) NOT NULL,
    phone VARCHAR(15) NOT NULL UNIQUE,
    user_password VARCHAR(500) NOT NULL,
    post_code VARCHAR(10) NOT NULL,
    city VARCHAR(10),
    municipalities VARCHAR(20),
    user_address VARCHAR(50) NOT NULL,
    building VARCHAR(50),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    is_deleted INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS reservation_table (
    reservation_id INTEGER AUTO_INCREMENT NOT NULL,
    user_id INTEGER NOT NULL,
    store_id INTEGER NOT NULL,
    reserved_at DATETIME NOT NULL,
    num_of_people INTEGER NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (reservation_id,user_id,store_id),
    FOREIGN KEY (`user_id`) REFERENCES user_info_tb(user_id) ON DELETE CASCADE,
    FOREIGN KEY (`store_id`) REFERENCES store_info_tb(store_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS store_regular_holidays(
    regular_holiday_id INTEGER AUTO_INCREMENT NOT NULL,
    store_id INTEGER NOT NULL,
    day_of_week INTEGER NOT NULL,
    is_deleted BOOLEAN default 0,
    PRIMARY KEY (regular_holiday_id),
    FOREIGN KEY (store_id) REFERENCES store_info_tb(store_id)
);
