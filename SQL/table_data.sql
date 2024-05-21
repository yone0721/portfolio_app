INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (1,"飲食");
INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (2,"カット");
INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (3,"マッサージ");
INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (4,"その他");


/* 
    テストデータ　店舗情報
*/
INSERT INTO store_info_tb (
    store_id,
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    building,
    mail,
    phone,
    store_password,
    opened_on,
    closed_on,
    created_at
) VALUES (
    'B0001',
    '美容院その1',
    '000-0001',
    '東京都',
    '港区',
    '新橋のような場所1-1-1',
    'ビルディング5F 101',
    'mail1@info.com',
    '0120444444',
    'password1234',
    '08:00',
    '17:00',
    now()
);

INSERT INTO store_info_tb (
    store_id,
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    mail,
    phone,
    store_password,
    opened_on,
    closed_on,
    created_at
) VALUES (
    'F0001',
    '飲食店その1',
    '000-0011',
    '東京都',
    '千代田区',
    '丸の内のような場所3-3-3',
    'mail2@info.com',
    '0120828828',
    'aaaa1111',
    '10:00',
    '21:00',
    now()
);

INSERT INTO store_info_tb (
    store_id,
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    building,
    mail,
    phone,
    store_password,
    opened_on,
    closed_on,
    created_at
) VALUES (
    'O0001',
    '万屋',
    '110-0101',
    '東京都',
    '新宿区',
    '歌舞伎町のような場所1200',
    '万屋ぎんちゃん',
    'gintama@info.com',
    '012099991111',
    'gintoki0000',
    '07:00',
    '16:00',
    now()
);

/*
    店舗のサービスプラン
*/
INSERT INTO service_plan(
    store_id,
    plan_name,
    service_describe
) VALUES (
    'B0001',
    'カット　1,000円',
    'カットのみのお手軽プラン！'
);

INSERT INTO service_plan(
    store_id,
    plan_name,
    service_describe,
) VALUES (
    'B0001',
    'カット＋カラー 5,000円',
    'カット後に金髪に仕上げます。'
);

INSERT INTO service_plan(
    store_id,
    plan_name,
    service_describe
) VALUES (
    'F0001',
    '食べ放題 2,980円',
    '2時間メニュー食べ放題 サラダバー・ドリンクバー付き'
);

INSERT INTO service_plan(
    store_id,
    plan_name,
    service_describe
) VALUES (
    'O0001',
    'なんでも相談 0円',
    '厄介ごと受け付けます。'
);

/* 
    テスト用データ　顧客情報
*/

INSERT INTO user_info_tb(
    user_id,
    mail,
    user_name,
    user_name_furigana,
    phone,
    user_password,
    post_code,
    city,
    municipalities,
    user_address,
    building
) VALUES (
    'user0001',
    'user@email.com',
    '山田太郎',
    'ヤマダタロウ',
    '0800000000',
    'pass0000',
    '111-1111',
    '東京都',
    '中野区',
    '東中野のような場所555',
    'スカイハイツ2222'
);

