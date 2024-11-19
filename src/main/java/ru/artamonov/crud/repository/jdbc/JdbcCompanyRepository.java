package ru.artamonov.crud.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.artamonov.crud.db.DataSourceFactory;
import ru.artamonov.crud.model.Company;
import ru.artamonov.crud.repository.CompanyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCompanyRepository implements CompanyRepository {
	private final Logger logger = LoggerFactory.getLogger(JdbcCompanyRepository.class);
	private JdbcCompanyRepository() {}

	private static class SingletonHelper {
		private static final JdbcCompanyRepository INSTANCE = new JdbcCompanyRepository();
	}

	public static JdbcCompanyRepository getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public boolean save(Company company) {
		try {
			String sql = "INSERT INTO company (name, email) VALUES (?, ?)";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, company.getName());
			ps.setString(2, company.getEmail());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("SQL exception", e);
			return false;
		}
	}

	@Override
	public boolean update(Company company) {
		try {
			String sql = "UPDATE company SET name = ?, email = ? WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, company.getName());
			ps.setString(2, company.getEmail());
			ps.setInt(3, company.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("SQL exception", e);
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM company WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error("SQL exception", e);
			return false;
		}
	}

	@Override
	public Company get(int id) {
		try {
			String sql = "SELECT * FROM company WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				return company;
			}
		} catch (SQLException e) {
			logger.error("SQL exception", e);
			return null;
		}
		return null;
	}

	@Override
	public List<Company> getAll() {
		try {
			String sql = "SELECT * FROM company";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Company> companies = new ArrayList<>();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				companies.add(company);
			}
			return companies;
		} catch (SQLException e) {
			logger.error("SQL exception", e);
		}
		return null;
	}
}
