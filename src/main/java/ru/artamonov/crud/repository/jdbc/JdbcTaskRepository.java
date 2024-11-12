package ru.artamonov.crud.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.artamonov.crud.db.DataSourceFactory;
import ru.artamonov.crud.model.Task;
import ru.artamonov.crud.repository.TaskRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JdbcTaskRepository implements TaskRepository {
	private final Logger logger = LoggerFactory.getLogger(JdbcTaskRepository.class);
	private JdbcTaskRepository() {}

	private static class SingletonHelper {
		private static final JdbcTaskRepository INSTANCE = new JdbcTaskRepository();
	}

	public static JdbcTaskRepository getInstance() {
		return JdbcTaskRepository.SingletonHelper.INSTANCE;
	}

	@Override
	public boolean save(Task task) {
		try {
			String sql = "INSERT INTO task (deadline, description) VALUES (?,?)";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, Date.valueOf(task.getDeadline()));
			ps.setString(2, task.getDescription());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean assignTaskToCompany(int taskId, int companyId) {
		try {
			String sql = "UPDATE task SET company_id = ? WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, companyId);
			ps.setInt(2, taskId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean assignTaskToEmployee(int taskId, int employeeId) {
		try {
			String sql = "INSERT INTO employee_task (task_id, employee_id) VALUES (?,?)";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, taskId);
			ps.setInt(2, employeeId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM task WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public Task get(int id) {
		try {
			String sql = "SELECT * FROM task WHERE id = ?";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Task task = null;
			while (rs.next()) {
				task = new Task();
				task.setId(rs.getInt("id"));
				task.setDeadline(LocalDate.parse(rs.getString("deadline"),
						DateTimeFormatter.ISO_DATE));
				task.setDescription(rs.getString("description"));
			}
			return task;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public List<Task> getAll() {
		try {
			String sql = "SELECT * FROM task";
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return getTasks(rs);
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	private List<Task> getTasks(ResultSet rs) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		while (rs.next()) {
			Task task = new Task();
			task.setId(rs.getInt("id"));
			task.setDeadline(LocalDate.parse(rs.getString("deadline"),
					DateTimeFormatter.ISO_DATE));
			task.setDescription(rs.getString("description"));
			tasks.add(task);
		}
		return tasks;
	}

	@Override
	public List<Task> getByEmployeeId(int employeeId) {
		String sql = "SELECT t.id, t.deadline, t.description " +
				"FROM task t JOIN employee_task et ON t.id = et.task_id " +
				"WHERE employee_id = ?";
		return getTasksByForeignId(employeeId, sql);

	}

	@Override
	public List<Task> getByCompanyId(int companyId) {
		String sql = "SELECT * FROM task WHERE company_id = ?";
		return getTasksByForeignId(companyId, sql);
	}

	private List<Task> getTasksByForeignId(int foreignId, String sql) {
		try {
			Connection conn = DataSourceFactory.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, foreignId);
			ResultSet rs = ps.executeQuery();
			return getTasks(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
