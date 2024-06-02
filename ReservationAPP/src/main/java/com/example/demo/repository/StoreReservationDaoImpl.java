package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
import com.example.demo.exception.StoreInfoNotFoundException;

public class StoreReservationDaoImpl implements StoreReservationDao {
	private final JdbcTemplate jdbcTemplate;
	
	
	
	public StoreReservationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String,Object>> findReservationAll(StoreInfo storeInfo) {
		String sql = """
				SELECT 
					reservation.reservation_id,
					user.user_id,
					user.user_name,
					user.mail,
					user.phone,
					store.store_id,
					store.store_name,
					type.service_type_id,
					type.service_type_name,
					plan.service_plan_id,
					plan.plan_name,
					reservation.at_reservation_date,
					reservation.at_created					
				FROM reservation_table AS reservation
				INNER JOIN user_info_tb AS user
					ON reservation.user_id = user.user_id
				INNER JOIN store_info_tb AS store
					ON reservation.store_id = store.store_id
				INNER JOIN store_service_type AS type
					ON reservation.service_type_id = type.service_type_id
				INNER JOIN service_plan AS plan
					ON reservation.service_plan_id = plan.service_plan_id
				WHERE store.store_id = ? ORDER BY at_created DESC""";
		try {
			return jdbcTemplate.queryForList(sql,storeInfo.getStoreId());
		}catch(DataAccessException e) {
			e.getStackTrace();
			throw new StoreInfoNotFoundException("予約データが見つかりませんでした。");
		}
	}

	@Override
	public List<Map<String, Object>> findCanceledAll(StoreInfo storeInfo) {
		String sql = """
				SELECT 
					reservation.reservation_id,
					user.user_id,
					user.user_name,
					user.mail,
					user.phone,
					store.store_id,
					store.store_name,
					type.service_type_id,
					type.service_type_name,
					plan.service_plan_id,
					plan.plan_name,
					reservation.at_created,
					reservation.at_reservation_date
				FROM reservation_table AS reservation
				INNER JOIN user_info_tb AS user
					ON reservation.user_id = user.user_id
				INNER JOIN store_info_tb AS store
					ON reservation.store_id = store.store_id
				INNER JOIN store_service_type AS type
					ON reservation.service_type_id = type.service_type_id
				INNER JOIN service_plan AS plan
					ON reservation.service_plan_id = plan.service_plan_id
				ORDER BY at_created DESC
				WHERE store.store_id = ? AND is_canceled = 'Y' """;
		try {
			return jdbcTemplate.queryForList(sql,storeInfo.getStoreId());
		}catch(DataAccessException e) {
			e.getStackTrace();
			throw new StoreInfoNotFoundException("予約データが見つかりませんでした。");
		}
	}

	@Override
	public int insert(Reservation reservation) {
		String sql = """
				INSERT INTO reservation_table (
					user_id = ?,
					store_id = ?,
					service_type_id = ?,
					service_plan_id = ?
				) VALUES (?,?,?,?)""";
		
		try {
			return jdbcTemplate.update(sql,
					reservation.getUserId(),reservation.getStoreId(),
					reservation.getServiceTypeId(),reservation.getServicePlanId());
		}catch(FailedInsertSQLException e) {
			e.getStackTrace();
			throw new FailedInsertSQLException("予約に失敗しました。");
		}
	}
	@Override
	public int update(Reservation reservation) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int cancelReservation(Reservation reservation) {
		String sql = "UPDATE reservation_table SET is_canceled = ? WHERE reservation_id = ?";
		
		try {
			return jdbcTemplate.update(sql,'Y',reservation.getReservationId());
		}catch(FailedUpdateSQLException e) {
			e.getStackTrace();
			throw new FailedUpdateSQLException("予約キャンセルができませんでした。");
		}
	}

	@Override
	public int deleteCanceledReservation(Reservation reservation) {
		String sql = """
				DELETE FROM reservation_table
				WHERE reservation_id = ? is_canceled = 'Y'""";
		try {
			return jdbcTemplate.update(sql,reservation.getReservationId());			
		}catch(FailedDeleteSQLException e) {
			e.getStackTrace();
			throw new FailedDeleteSQLException("予約情報の削除に失敗しました。");
		}
	}
	
}
