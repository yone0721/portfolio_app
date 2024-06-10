package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StoreInfo;
import com.example.demo.entity.UserInfo;

@Repository
public class ReservationDaoImpl implements ReservationDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<StoreInfo> findAllStores() {
		String sql = """
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
			GROUP BY holidays.store_id
			LIMIT 10;""";
		
		List<Map<String,Object>> getStoresList = jdbcTemplate.queryForList(sql);
		return null;
	}

	@Override
	public UserInfo findUserByMail(String mail) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	
	
	
}
