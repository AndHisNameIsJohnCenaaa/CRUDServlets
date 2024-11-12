package ru.artamonov.crud.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.artamonov.crud.db.DataSourceFactory;
import ru.artamonov.crud.model.Employee;
import ru.artamonov.crud.repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcEmployeeRepository implements EmployeeRepository {
	private final Logger logger = LoggerFactory.getLogger(JdbcEmployeeRepository.class);
	private JdbcEmployeeRepository() {}

	private static class SingletonHelper {
		private static final JdbcEmployeeRepository INSTANCE = new JdbcEmployeeRepository();
	}

	public static JdbcEmployeeRepository getInstance() {
		return JdbcEmployeeRepository.SingletonHelper.INSTANCE;
	}
	@Override
	public boolean save(Employee employee) {
		try {
			String sql = "INSERT INTO employee(name, telegram_id) VALUES (?,?)";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getTelegramId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean update(Employee employee) {
		try {
			String sql = "UPDATE employee SET name = ?, telegram_id = ? WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getTelegramId());
			ps.setInt(3, employee.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM employee WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return false;
	}

	@Override
	public Employee get(int id) {
		try {
			String sql = "SELECT * FROM employee WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setTelegramId(rs.getString("telegram_id"));
				return employee;
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Employee> getByTaskId(int taskId) {
		try {
			String sql = "SELECT em.id, em.name, em.telegram_id " +
					"FROM employee em JOIN employee_task et ON em.id = et.employee_id " +
					"WHERE et.task_id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			return getEmployees(rs);
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Employee> getAll() {
		try {
			String sql = "SELECT * FROM employee";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return getEmployees(rs);
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	private List<Employee> getEmployees(ResultSet rs) {
		try {
			List<Employee> employees = new ArrayList<>();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setTelegramId(rs.getString("telegram_id"));
				employees.add(employee);
			}
			return employees;
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return null;
	}
}
