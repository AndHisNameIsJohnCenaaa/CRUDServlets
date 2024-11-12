package ru.artamonov.crud.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.artamonov.crud.model.Employee;
import ru.artamonov.crud.model.Task;
import ru.artamonov.crud.repository.TaskRepository;
import ru.artamonov.crud.repository.jdbc.JdbcTaskRepository;
import ru.artamonov.crud.util.ServletUtil;
import ru.artamonov.crud.util.exception.BadRequestException;
import ru.artamonov.crud.util.exception.ErrorResource;
import ru.artamonov.crud.util.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/tasks/*")
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	private TaskRepository repository;

	@Override
	public void init() throws ServletException {
		repository = JdbcTaskRepository.getInstance();
	}

	private int getIdBy(HttpServletRequest request, String parameterName) {
		String paramId = Objects.requireNonNull(request.getParameter(parameterName));
		return Integer.parseInt(paramId);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			List<Task> tasks;
			try {
				int id = getIdBy(req, "companyId");
				tasks = repository.getByCompanyId(id);
			} catch (NullPointerException e) {
				try {
					int id = getIdBy(req, "employeeId");
					tasks = repository.getByEmployeeId(id);
				} catch (NullPointerException e2) {
					tasks = repository.getAll();
				}
			}
			if (tasks == null) {
				throw new NotFoundException("Tasks not found");
			}
			logger.info(tasks.toString());
			ServletUtil.respond(tasks, resp);
		} catch (NotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			ErrorResource error = new ErrorResource("Not found", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
			ServletUtil.respond(error, resp);
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			Task task = ServletUtil.expect(Task.class, req);
			ServletUtil.respond(repository.save(task), resp);
		} catch (BadRequestException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ErrorResource error = new ErrorResource("Bad Request", e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
			ServletUtil.respond(error, resp);
		} catch (NotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			ErrorResource error = new ErrorResource("Not found", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
			ServletUtil.respond(error, resp);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo.contains("/company/")) {
				String[] parts = pathInfo.split("/");
				int companyId = Integer.parseInt(parts[parts.length - 1]);
				int taskId = Integer.parseInt(parts[1]);
				repository.assignTaskToCompany(taskId, companyId);
			} else if (pathInfo.contains("/employee/")) {
				String[] parts = pathInfo.split("/");
				int employeeId = Integer.parseInt(parts[parts.length - 1]);
				int taskId = Integer.parseInt(parts[1]);
				repository.assignTaskToEmployee(taskId, employeeId);
			}

		} catch (NullPointerException | NumberFormatException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ErrorResource error = new ErrorResource("Bad Request", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
			ServletUtil.respond(error, resp);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null) {
				throw new BadRequestException("No task id was found");
			} else {
				int id = Integer.parseInt(pathInfo.substring(1));
				if (!repository.delete(id)) {
					throw new NotFoundException("Task not found");
				}
			}
		} catch (BadRequestException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ErrorResource error = new ErrorResource("Bad Request", e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
			ServletUtil.respond(error, resp);
		} catch (NotFoundException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			ErrorResource error = new ErrorResource("Not found", e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
			ServletUtil.respond(error, resp);
		}
	}
}
