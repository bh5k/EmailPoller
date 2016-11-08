package com.stg.emailpoller.repository;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.stg.emailpoller.ReadConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Data Source Factory.
 *
 * Created by dqromney on 11/7/16.
 */
public class DataSourceFactory {
	private static ReadConfig readConfig;
	private static Properties configProps;

	public static DataSource getMySQLDataSource() {
		readConfig = new ReadConfig("config.properties");
		try {
			configProps = readConfig.getPropValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MysqlDataSource mysqlDS = null;
		mysqlDS = new MysqlDataSource();
		mysqlDS.setURL(configProps.getProperty("db.url"));
		mysqlDS.setUser(configProps.getProperty("db.username"));
		mysqlDS.setPassword(configProps.getProperty("db.password"));
		return mysqlDS;
	}
}
