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
 *　1: 日曜日, 2: 月曜日, ..., 7: 土曜日
 */

INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(13,6);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(13,7);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(14,1);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(14,7);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(15,4);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(15,5);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(15,6);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(16,1);
INSERT INTO store_regular_holidays(
    store_id,day_of_week)VALUES(16,2);

/*
*　定休日と店舗情報の取得
*/

SELECT
    store.store_id,
    store.store_name,
    store.zip_code,
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
GROUP BY holidays.store_id


/*
*   予約データのサンプル
*/

INSERT INTO reservation_table (
    user_id,
    store_id,
    at_reservation_date
) VALUES (
    1,
    14,
    now()
);
INSERT INTO reservation_table (
    user_id,
    store_id,
    at_reservation_date
) VALUES (
    1,
    14,
    now()
);
INSERT INTO reservation_table (
    user_id,
    store_id,
    at_reservation_date
) VALUES (
    1,
    15,
    now()
);


/*
*   予約上限数の計算用SQL
*/

SELECT
    store.store_name,
    COUNT(res.store_id) AS 予約数
FROM reservation_table AS res
LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE res.at_reservation_date LIKE '2024-06-20%'
GROUP BY store.store_name;

SELECT
    store.store_name,
    res.at_reservation_date
FROM reservation_table AS res
LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE res.at_reservation_date LIKE '2024-06-20%';

/*
*　指定した店舗の残り予約数も取得するクエリ
*/

SELECT
    store.store_name,
    store.store_reservation_limit - (
    SELECT
        COUNT(reservation_id)
    FROM reservation_table AS res
    WHERE res.store_id = 15
    )
FROM store_info_tb AS store
WHERE store.store_id = 15;

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
    store.store_reservation_limit - (
    SELECT
        COUNT(reservation_id)
    FROM reservation_table AS res
    WHERE res.store_id = store.store_id
    ) AS store_reservation_limit,
    store.is_opened,
    store.is_closed,
    GROUP_CONCAT(holidays.day_of_week)
FROM store_info_tb AS store
INNER JOIN store_regular_holidays AS holidays
ON holidays.store_id = store.store_id
GROUP BY holidays.store_id\G

ALTER TABLE reservation_table
ADD num_of_people INTEGER NOT NULL DEFAULT 1
AFTER at_reservation_date;


SELECT
    store.store_reservation_limit -(
        SELECT
            COUNT(reservation_id)
        FROM reservation_table AS res
        WHERE res.store_id = store.store_id
        AND at_reservation_date LIKE '2024-06-11%'
    ) AS store_reservation_limit
FROM store_info_tb AS store
WHERE store.store_id = 14


SELECT
    COUNT(reservation_id)
FROM reservation_table
WHERE store_id = 14
AND at_reservation_date LIKE '2024-06-12%'

SELECT
    user.user_id,
    user.user_name,
    store.store_id,
    store.store_name,
    res.at_reservation_date,
    res.num_of_people,
    res.at_created
FROM reservation_table AS res
LEFT JOIN user_info_tb AS user
ON user.user_id = res.user_id
LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE res.at_created LIKE '2024-06-13 15%'\G

SELECT
    res.reservation_id,
    res.user_id,
    res.store_id,
    store.store_name,
    res.at_reservation_date,
    res.num_of_people,
    res.at_created,
    res.is_deleted
FROM reservation_table AS res
LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE
    res.user_id = 1
AND res.store_id = 14
AND res.is_deleted = 0
ORDER BY res.at_created DESC
LIMIT 1;


-- シーク法
SELECT
    res.reservation_id,
    res.at_reservation_date,
    res.store_id,
    store.store_name,
    store.city,
    store.municipalities,
    store.street_address,
    store.building,
    store.mail,
    store.phone,
    res.num_of_people
FROM reservation_table AS res

LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE
    res.store_id = 14
AND
    (cast('2024-06-20') AS date > res.at_reservation_date)
ORDER BY res.at_reservation_date DESC
LIMIT 10

-- オフセット法
SELECT
    res.reservation_id,
    res.at_reservation_date,
    res.store_id,
    store.store_name,
    store.city,
    store.municipalities,
    store.street_address,
    store.building,
    store.mail,
    store.phone,
    res.num_of_people
FROM reservation_table AS res
LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE
    res.user_id = 1
ORDER BY res.at_reservation_date DESC
LIMIT 10
OFFSET 0

SELECT
    res.reservation_id,
    res.user_id,
    res.store_id,
    store.store_name,
    store.city,
    store.municipalities,
    store.street_address,
    store.building,
    store.mail,
    store.phone,
    res.at_reservation_date,
    res.num_of_people
FROM reservation_table AS res
LEFT JOIN store_info_tb AS store
ON store.store_id = res.store_id
WHERE res.user_id = 1
AND res.is_deleted = 0
AND res.reservation_id < 18
ORDER BY res.at_reservation_date DESC
LIMIT 10
