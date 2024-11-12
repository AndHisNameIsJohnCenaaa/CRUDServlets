package ru.artamonov.crud.db;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;

import javax.sql.DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class DataSourceFactory {
	private final DataSource dataSource;
	private static final Logger log = getLogger(DataSourceFactory.class);

	DataSourceFactory() {
		PGSimpleDataSource simpleDataSource = new PGSimpleDataSource();
		URI rootpath = null;
		try {
			rootpath = Objects.requireNonNull(Thread.currentThread()
							.getContextClassLoader()
							.getResource("db/postgres.properties"))
					.toURI();
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}

		try (InputStream input = Files.newInputStream(Paths.get(rootpath))) {
			Properties properties = new Properties();
			properties.load(input);
			simpleDataSource.setUrl(properties.getProperty("database.url"));
			simpleDataSource.setUser(properties.getProperty("database.username"));
			simpleDataSource.setPassword(properties.getProperty("database.password"));

		} catch (FileNotFoundException e) {
			log.error(" file postgres.properties not found", e);
		} catch (IOException e) {
			log.error(" reading postgres.properties failed", e);
		}
		dataSource = simpleDataSource;
	}

	public static Connection getConnection() throws SQLException {
		return SingletonHelper.INSTANCE.dataSource.getConnection();
	}

	public static DataSource getDataSource() {
		return SingletonHelper.INSTANCE.dataSource;
	}

	private static class SingletonHelper {
		private static final DataSourceFactory INSTANCE = new DataSourceFactory();
	}


}
