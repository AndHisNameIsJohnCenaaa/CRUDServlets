package ru.artamonov.crud.repository.jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.artamonov.crud.db.DataSourceFactory;
import ru.artamonov.crud.MyBatisScriptUtility;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;


public abstract class AbstractRepositoryTest {

	private static final String initDBPath = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
					.getResource("db/initDB.sql"))
			.getFile()).toPath().toString();
	private static final String populateDBPath = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
					.getResource("db/populateDB.sql"))
			.getFile()).toPath().toString();

	public AbstractRepositoryTest() {
	}

	@BeforeAll
	public static void setUpClass() throws Exception {
		MyBatisScriptUtility.runScript(initDBPath, DataSourceFactory.getConnection());
	}

	@BeforeEach
	public void setUp() throws Exception {
		MyBatisScriptUtility.runScript(populateDBPath, DataSourceFactory.getConnection());
	}
}
