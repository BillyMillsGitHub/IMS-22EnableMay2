package com.qa.ims.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.qa.ims.persistence.domain.orderItems;

public interface Dao<T> {

	List<T> readAll();
	
	T read(Long id);

	T create(T t);

	T update(T t);

	int delete(long id);

	T modelFromResultSet(ResultSet resultSet) throws SQLException;

	orderItems modelFromResultSet(ResultSet resultSet, double price) throws SQLException;


	orderItems create(long OrderItems);

	orderItems read(long orderItemsID);

}