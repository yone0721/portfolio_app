IF NOT EXISTS reservation_db CREATE DATABASE reservation_db;

IF NOT EXISTS gender_info CREATE TABLE gender_info(
    gender_id INTEGER NOT NULL DEFAULT 1,
    gender_name VARCHAR(10) NOT NULL UNIQUE,
)

IF NOT EXISTS user_info CREATE TABLE user_info(
    user_id INTEGER NOT NULL AUTO_INCREMENT,
    user_pass VARCHAR(500) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    post_code VARCHAR(10) NOT NULL,
    user_address VARCHAR(20) NOT NULL,
    birthday date NOT NULL,
    gender INTEGER NOT NULL,
    phone VARCHAR(15) NOT NULL,
    mail VARCHAR(1000) NOT NULL,
    registered_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY user_info(user_gender) REFERENCES gender_info(id)
)

IF NOT EXISTS store_info CREATE TABLE store_info(
    store_id INTEGER NOT NULL AUTO_INCREMENT,
    store_pass VARCHAR(500) NOT NULL,
    store_name VARCHAR(20) NOT NULL,
    post_code VARCHAR(10) NOT NULL,
    user_address VARCHAR(20) NOT NULL,
    establishment_at date NOT NULL,
    phone VARCHAR(15) NOT NULL,
    mail VARCHAR(1000) NOT NULL,
    registered_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (id)
)

IF NOT EXISTS reservation_table CREATE TABLE reservation_table(
    reservation_id INTEGER NOT NULL DEFAULT '1',
    client_id INTEGER NOT NULL,
    store_id INTEGER NOT NULL,
    created_at DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
    cancel_at DATETIME,
    completed_at DATETIME,
    PRIMARY KEY (reservation_id)
)
