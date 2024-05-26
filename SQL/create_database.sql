CREATE DATABASE IF NOT EXISTS reservation_db
    CHARACTER SET utf8mb4;

USE reservation_db;

/*
    店舗のサービス名のテーブル
    ・service_type_id       サービスの種類を管理する識別番号
    ・service_type_name     サービス種類の名称
*/
CREATE TABLE IF NOT EXISTS store_service_type (
    service_type_id INTEGER NOT NULL PRIMARY KEY,
    service_type_name VARCHAR(20) NOT NULL UNIQUE
);

/*
    店舗情報のテーブル
    ・store_id          店舗を管理するための識別番号　半角英数字
    ・store_name        店舗の名前
    ・post_code         郵便番号
    ・city              都道府県
    ・municipalities    市区町村
    ・street_address    市区町村以降の住所
    ・building          建物名・階層・部屋番号
    ・mail              メールアドレス
    ・phone             電話番号
    ・store_password    ログイン用の暗証番号
    ・opened_on         開店時間（00:00の形式で入力）
    ・closed_on         閉店時間（00:00の形式で入力）
    ・created_at        店舗情報の登録日時
    ・update_at         店舗情報の最終更新日時　店舗情報に更新があった場合のみ入力
*/
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
    is_opened VARCHAR(30) NOT NULL DEFAULT "10:00",
    is_closed VARCHAR(30) NOT NULL DEFAULT "21:00",
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);

/*
    サービスプラン　
    店舗が提供するサービスの内容をリストアップ
    ・service_plan_id       サービスプランを管理する識別番号
    ・store_id              店舗の識別番号　参照値はstore_info_tbのstore_id
    ・plan_name             プランの名称
    ・service_describe      サービスプランの説明文（サイトで確認するPR文）
    ・created_at            サービスプラン情報の登録日時
    ・update_at             サービスプラン情報の最終更新日時　情報に更新があった場合のみ入力
    ・started_at            プランの開始日
    ・finished_at           プランの終了日（期間限定などの場合にのみ設定）
*/
CREATE TABLE IF NOT EXISTS service_plan(
    service_plan_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    store_id INTEGER NOT NULL,
    service_type_id INTEGER NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    service_describe VARCHAR(1000) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    started_at DATE,
    finished_at DATE,
    FOREIGN KEY (`store_id`) REFERENCES store_info_tb (store_id) ON DELETE CASCADE,
    FOREIGN KEY (`service_type_id`) REFERENCES store_service_type (service_type_id) ON DELETE CASCADE
);

/*
    顧客情報のテーブル
    ・user_id               顧客情報を管理するための識別番号 半角英数字
    ・mail                  顧客のメールアドレス
    ・user_name             顧客の氏名
    ・user_name_furigana    氏名のフリガナ
    ・phone                 顧客の電話番号
    ・user_password         ログインするためのパスワード
    ・post_code             顧客住所の郵便番号
    ・city                  顧客住所の都道府県
    ・municipalities        顧客住所の市区町村
    ・user_address          市区町村以降の住所
    ・building              建物名・階層・部屋番号
    ・created_at            顧客情報の登録日時
    ・updated_at            顧客情報の最終更新日時
*/
CREATE TABLE IF NOT EXISTS user_info_tb (
    user_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    mail VARCHAR(1000) NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    user_name_furigana VARCHAR(40) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    user_password VARCHAR(500) NOT NULL,
    post_code VARCHAR(10) NOT NULL,
    city VARCHAR(10),
    municipalities VARCHAR(20),
    user_address VARCHAR(50) NOT NULL,
    building VARCHAR(50),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME
);

/*
    予約テーブル
    ・reservation_id          登録された予約を管理する識別番号
    ・user_id                 顧客情報の識別番号
    ・store_id                店舗情報の識別番号
    ・reservation_date_at     来店予定の日時
    ・created_at              予約登録をした日時
    ・is_canceled             キャンセルがあるかどうかのフラグ。デフォルトは"N"、キャンセルの場合は"Y"
*/
CREATE TABLE IF NOT EXISTS reservation_table (
    reservation_id INTEGER AUTO_INCREMENT NOT NULL,
    user_id INTEGER NOT NULL,
    store_id INTEGER NOT NULL,
    service_plan_id INTEGER NOT NULL,
    reservation_date_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_canceled VARCHAR(10) NOT NULL DEFAULT "N",
    PRIMARY KEY (reservation_id,user_id,store_id),
    FOREIGN KEY (`user_id`) REFERENCES user_info_tb(user_id) ON DELETE CASCADE,
    FOREIGN KEY (`store_id`) REFERENCES store_info_tb(store_id) ON DELETE CASCADE,
    FOREIGN KEY (`service_type_id`) REFERENCES store_service_type (service_type_id) ON DELETE CASCADE
);
