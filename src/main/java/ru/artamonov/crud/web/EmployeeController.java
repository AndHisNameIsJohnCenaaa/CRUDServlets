package ru.artamonov.crud.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.artamonov.crud.model.Company;
import ru.artamonov.crud.model.Employee;
import ru.artamonov.crud.repository.EmployeeRepository;
import ru.artamonov.crud.repository.jdbc.JdbcEmployeeRepository;
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

@WebServlet("/employees/*")
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeRepository repository;
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Override
	public void init() throws ServletException {
		repository = JdbcEmployeeRepository.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ServletUtil.setEncodingAndContentType(resp);
		String pathInfo = req.getPathInfo();
		if (pathInfo == null) {
			List<Employee> employees = repository.getAll();
			logger.info(employees.toString());
			ServletUtil.respond(employees, resp);
		}
		else {
			int id = Integer.parseInt(pathInfo.substring(1));
			Employee employee = repository.get(id);
			logger.info(employee.toString());
			ServletUtil.respond(employee, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			Employee employee = ServletUtil.expect(Employee.class, req);
			ServletUtil.respond(repository.save(employee), resp);
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
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null) {
				throw new BadRequestException("No employee id was found");
			}else {
				Employee employee = ServletUtil.expect(Employee.class, req);
				employee.setId(Integer.parseInt(pathInfo.substring(1)));
				if(!repository.update(employee)) {
					throw new NotFoundException("Employee not found");
				}
			}
			ServletUtil.respond(HttpServletResponse.SC_NO_CONTENT, resp);

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
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtil.setEncodingAndContentType(resp);
		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null) {
				throw new BadRequestException("No employee id was found");
			}else {
				int id = Integer.parseInt(pathInfo.substring(1));
				if (!repository.delete(id)) {
					throw new NotFoundException("Employee not found");
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
