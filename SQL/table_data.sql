/*
　　店舗情報テーブル
    テストデータ
*/
INSERT INTO store_info_tb (
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    mail,
    phone,
    store_password,
    store_reservation_limit,
    is_opened,
    is_closed,
    created_at
) VALUES (
    'QVハウス',
    '1234567',
    '東京都',
    '港区',
    '新橋1-1-1',
    'qv@example.com',
    '0120444444',
    'vZTc2ib8y05o1qMfm1qsC1ca4mbYImIOkB736+OhHU8=',
    15,
    '08:00',
    '17:00',
    now()
);
--pass1234

INSERT INTO store_info_tb (
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    mail,
    phone,
    store_password,
    store_reservation_limit,
    is_opened,
    is_closed,
    created_at
) VALUES (
    '牛貴族',
    '9876543',
    '東京都',
    '千代田区',
    '丸の内',
    'ushiki@example.com',
    '0120123456',
    'dUBo+TygkD4dt/CtPsWmFhecc49GKVndI4C24nQ2gNs=',
    15,
    '10:00',
    '21:00',
    now()
);
-- aaaa1111

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
    'リングサロン',
    '1070062',
    '東京都',
    '港区',
    '南青山3丁目1番36号',
    '青山丸竹ビル',
    'ring-salon@example.com',
    '00011112222',
    'abcd1234',
    '10:00',
    '19:00',
    now()
);
--abcd1234

INSERT INTO store_info_tb (
    store_name,
    post_code,
    city,
    municipalities,
    street_address,
    mail,
    phone,
    store_password,
    store_reservation_limit,
    is_opened,
    is_closed,
    created_at
) VALUES (
    '銅の皿',
    '1111111',
    '東京都',
    '渋谷区',
    '原宿',
    'dousara@example.com',
    '0120334334',
    '6PqCOnbzqvcGj9IGi6gdH8s7aAvYVCdt3ELWE5dUJAs=',
    15,
    '10:00',
    '22:00',
    now()
);
--pass5678

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
 *　定休日テーブル
 *　0: 日曜日, 1: 月曜日, ..., 6: 土曜日
 */

INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(13,0);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(13,6);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(14,1);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(14,4);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(15,3);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(15,4);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(15,5);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(16,0);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(16,1);

/*
*　定休日と店舗情報の取得
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
    store.store_reservation_limit,
    store.is_opened,
    store.is_closed,
    GROUP_CONCAT(holidays.day_of_week)
FROM store_info_tb AS store
INNER JOIN store_regular_holidays AS holidays
ON holidays.store_id = store.store_id
GROUP BY holidays.store_id;
