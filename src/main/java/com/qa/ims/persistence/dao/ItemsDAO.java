package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Items;
import com.qa.ims.persistence.domain.orderItems;
import com.qa.ims.utils.DBUtils;

public class ItemsDAO implements Dao<Items>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public Items modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long itemsId = resultSet.getLong("items_id");
		double value = resultSet.getDouble("value");
		String itemName = resultSet.getString("item_name");
		return new Items(itemsId, value, itemName);
	}

	
	// Reads all items from the database
	
	@Override
	public List<Items> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items");) {
			List<Items> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(modelFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public Items readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY items_id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	// Creates an item in the database
	
	@Override
	public Items create(Items item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO items(value, item_name) VALUES (?, ?)");) {
			statement.setDouble(1, item.getValue());
			statement.setString(2, item.getItemName());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Items read(Long itemsId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE items_id = ?");) {
			statement.setLong(1, itemsId);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// Updates an item in the database

	@Override
	public Items update(Items item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE items SET value = ?, item_name= ? WHERE items_id = ?");) {
			statement.setDouble(1, item.getValue());
			statement.setString(2, item.getItemName());
			statement.setLong(3, item.getItemsId());
			statement.executeUpdate();
			return read(item.getItemsId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	// Deletes an item in the database
	@Override
	public int delete(long itemsId) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM items WHERE items_id= ?");) {
			statement.setLong(1, itemsId);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}


	@Override
	public orderItems modelFromResultSet(ResultSet resultSet, double price) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public orderItems create(long OrderItems) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public orderItems read(long orderItemsID) {
		// TODO Auto-generated method stub
		return null;
	}
}
