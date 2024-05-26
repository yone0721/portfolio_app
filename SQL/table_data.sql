INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (1,"飲食");
INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (2,"カット");
INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (3,"マッサージ");
INSERT INTO store_service_type (service_type_id,service_type_name) VALUES (4,"その他");


/*
    テストデータ　店舗情報
*/
INSERT INTO store_info_tb (
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    building,
    mail,
    phone,
    store_password,
    is_opened,
    is_closed,
    created_at
) VALUES (
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
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    mail,
    phone,
    store_password,
    is_opened,
    is_closed,
    created_at
) VALUES (
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
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    building,
    mail,
    phone,
    store_password,
    is_opened,
    is_closed,
    created_at
) VALUES (
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
    service_type_id,
    plan_name,
    service_describe
) VALUES (
    1,
    2,
    'カット　1,000円',
    'カットのみのお手軽プラン！'
);

INSERT INTO service_plan(
    store_id,
    service_type_id,
    plan_name,
    service_describe
) VALUES (
    1,
    2,
    'カット・カラー 5,000円',
    'カット後に金髪に仕上げます。'
);

INSERT INTO service_plan(
    store_id,
    service_type_id,
    plan_name,
    service_describe
) VALUES (
    2,
    1,
    '食べ放題 2,980円',
    '2時間メニュー食べ放題 サラダバー・ドリンクバー付き'
);

INSERT INTO service_plan(
    store_id,
    service_type_id,
    plan_name,
    service_describe
) VALUES (
    3,
    4,
    'なんでも相談 0円',
    '厄介ごと受け付けます。'
);

/*
    テスト用データ　顧客情報
*/

INSERT INTO user_info_tb(
    mail,
    user_name,
    user_name_furigana,
    phone,
    user_password,
    post_code,
    city,
    municipalities,
    user_address,
    building,
    updated_at
) VALUES (
    'user@email.com',
    '山田太郎',
    'ヤマダタロウ',
    '0800000000',
    'pass0000',
    '111-1111',
    '東京都',
    '中野区',
    '東中野のような場所555',
    'スカイハイツ2222',
    now()
);

INSERT INTO user_info_tb(
    mail,
    user_name,
    user_name_furigana,
    phone,
    user_password,
    post_code,
    city,
    municipalities,
    user_address,
    updated_at
) VALUES (
    'user2@email.com',
    '田中太郎',
    'タナカタロウ',
    '08011111111',
    'pass1111',
    '111-2222',
    '東京都',
    '八王子市',
    '八王子',
    now()
);

/*
    テスト用データ
*/
INSERT INTO reservation_table (
    user_id,
    store_id,
    service_plan_id,
    reservation_date_at
) VALUES (
    1,3,4,'2024/05/30 12:00:00'
);


/*
    予約テーブルの情報取得

*/
SELECT
    reservation.reservation_id,
    user.user_id,
    user.user_name,
    store.store_id,
    store.store_name,
    plan.plan_name,
    reservation.reservation_date_at
FROM user_info_tb AS user
INNER JOIN reservation_table AS reservation
        ON user.user_id = reservation.user_id
INNER JOIN store_info_tb AS store
        ON store.store_id = reservation.store_id
INNER JOIN service_plan AS plan
        ON plan.service_plan_id = reservation.service_plan_id
WHERE reservation.is_canceled = "N";

/*
    店舗情報取得
*/
SELECT
    store.store_id,
    store.store_name,
    store.post_code,
    store.city,
    store.municipalities,
    store.street_address,
    store.building,
    store.mail,
    store.phone,
    store.is_opened,
    store.is_closed,
    store.created_at,
    store.updated_at,
    store_type.service_type_name,
    plan.plan_name
FROM store_info_tb AS store
INNER JOIN service_plan AS plan
    ON store.store_id = plan.store_id
INNER JOIN store_service_type AS store_type
    ON plan.service_type_id = store_type.service_type_id
WHERE store.store_id\G
