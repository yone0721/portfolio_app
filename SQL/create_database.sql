CREATE DATABASE IF NOT EXISTS reservation_db;

CREATE TABLE IF NOT EXISTS gender_info (
    gender_id INTEGER NOT NULL PRIMARY KEY,
    gender_name VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_info (
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
    PRIMARY KEY (user_id),
    FOREIGN KEY (gender) REFERENCES gender_info(gender_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS store_info (
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
    PRIMARY KEY (store_id)
);

CREATE TABLE IF NOT EXISTS reservation_table (
    reservation_id VARCHAR(500) NOT NULL,
    user_id INTEGER NOT NULL,
    store_id INTEGER NOT NULL,
    created_at DATETIME NOT NULL,
    completed_at DATETIME,
    PRIMARY KEY (reservation_id,user_id,store_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_id),
    FOREIGN KEY (store_id) REFERENCES store_info(store_id)
);

CREATE TABLE IF NOT EXISTS cancel_table (
    id INTEGER NOT NULL AUTO_INCREMENT,
    reservation_id VARCHAR(500) NOT NULL,
    user_id INTEGER NOT NULL,
    store_id INTEGER NOT NULL,
    cancel_at DATETIME,
    cancel_reason VARCHAR(1000),
    PRIMARY KEY (id,reservation_id,user_id,store_id),
    FOREIGN KEY (reservation_id) REFERENCES reservation_table(reservation_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_id),
    FOREIGN KEY (store_id) REFERENCES store_info(store_id)
);
