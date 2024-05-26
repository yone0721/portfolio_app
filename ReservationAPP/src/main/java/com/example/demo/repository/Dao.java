package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	List<T> findAll();
	Optional<T> findById(int id);
	int insert(T entity);
	int update(T entity);
	int delete(T entity);
}
