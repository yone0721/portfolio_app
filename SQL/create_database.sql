CREATE DATABASE IF NOT EXISTS reservation_db
    CHARACTER SET utf8mb4;

-- サービスの種類 --
CREATE TABLE IF NOT EXISTS store_service_type (
    service_type_id INTEGER NOT NULL PRIMARY KEY,
    service_type_name VARCHAR(20) NOT NULL UNIQUE
);

-- 店舗情報のテーブル --
CREATE TABLE IF NOT EXISTS store_info_tb (
    store_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    store_name VARCHAR(100) NOT NULL,
    post_code VARCHAR(20) NOT NULL,
    city VARCHAR(15) NOT NULL,
    municipalities VARCHAR(100) NOT NULL,
    street_address VARCHAR(200) NOT NULL,
    building VARCHAR(200),
    mail VARCHAR(500) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    store_password VARCHAR(50) NOT NULL,
    opened_on VARCHAR(30) NOT NULL DEFAULT "10:00",
    closed_on VARCHAR(30) NOT NULL DEFAULT "21:00",
    created_at DATETIME NOT NULL,
    update_at DATETIME NOT NULL
);

-- サービスプラン --
CREATE TABLE IF NOT EXISTS service_plan(
    service_plan_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    store_id INTEGER NOT NULL, 
    plan_name VARCHAR(100) NOT NULL,
    service_describe VARCHAR(1000) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME,
    started_at DATE,
    finished_at DATE,
    FOREIGN KEY (store_id) REFERENCES store_info_tb (store_id) ON DELETE CASCADE
);

-- 顧客情報のテーブル --
CREATE TABLE IF NOT EXISTS user_info_tb (
    user_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(1000) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    user_name_furigana VARCHAR(40) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    user_password VARCHAR(500) NOT NULL,
    post_code VARCHAR(10) NOT NULL,
    city VARCHAR(10),
    municiplaities VARCHAR(20),
    user_address VARCHAR(50) NOT NULL,
    building VARCHAR(50),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL
);

-- 予約テーブル --
CREATE TABLE IF NOT EXISTS reservation_table (
    reservation_id VARCHAR(500) NOT NULL,
    user_id INTEGER NOT NULL,
    store_id INTEGER NOT NULL,
    reservation_date_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_canceled VARCHAR(10) NOT NULL DEFAULT "N",
    PRIMARY KEY (reservation_id,user_id,store_id),
    FOREIGN KEY (user_id) REFERENCES user_info_tb(user_id) ON DELETE CASCADE,
    FOREIGN KEY (store_id) REFERENCES store_info_tb(store_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS store_service_table(
    service_relation_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    store_id INTEGER NOT NULL,
    store_type_id INTEGER NOT NULL,
    FOREIGN KEY (store_id) REFERENCES store_info_tb(store_id) ON DELETE CASCADE,
    FOREIGN KEY (store_type_id) REFERENCES store_service_type (service_type_id) ON DELETE CASCADE


)
