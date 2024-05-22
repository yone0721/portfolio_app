package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	List<T> findAll();
	Optional<T> findById(int id);
	void insert(T entity);
	void update(T entity);
	void delete(T entity);
}
